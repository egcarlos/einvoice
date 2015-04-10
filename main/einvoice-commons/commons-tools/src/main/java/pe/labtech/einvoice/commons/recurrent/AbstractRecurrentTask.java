/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.recurrent;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

/**
 *
 *
 * @author carlos.echeverria
 * @param <T> Type of the task to be procesed
 */
public abstract class AbstractRecurrentTask<T> implements RecurrentTask {

    /**
     * Logger instance to be used in subclasses.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    private AtomicBoolean working;

    private AtomicBoolean enabled;

    /**
     * Lambda expression to recover the list of tasks.
     */
    protected Callable<List<T>> findTasks;

    /**
     * Lambda expression to lock a task as "in process". Must return a boolean.
     */
    protected Function<T, Boolean> tryLock;

    /**
     * Lambda expression to create an identifier for the task.
     */
    protected Function<T, String> getId;

    /**
     * Lambda expression with the processing of the task.
     */
    protected Consumer<T> consumer;

    /**
     * Lambda expression for handling the individual error on each task.
     */
    protected BiConsumer<T, ? super Throwable> onTaskError;

    @PostConstruct
    public void init() {
        logger.info(() -> "Task " + this.getClass().getSimpleName() + ": created.");
        this.working = new AtomicBoolean(false);
        this.enabled = new AtomicBoolean(true);
        this.getId = t -> t != null ? t.toString() : "invaid";
    }

    protected void timeout() {
        if (!enabled.get()) {
            return;
        }
        try {
            //Obteniendo lock en el bean para procesar
            if (!working.compareAndSet(false, true)) {
                logger.fine(() -> "Task " + this.getClass().getSimpleName() + ": in use, skipping.");
                return;
            }

            //Obteniendo la lista de tareas
            if (findTasks == null) {
                logger.warning(() -> "Unable to recover task list, findTask lambda is null.");
                return;
            }
            List<T> tasks;
            try {
                tasks = findTasks.call();
            } catch (Exception ex) {
                logger.log(Level.WARNING, ex, () -> "Unable to recover tasks. " + ex.getMessage());
                return;
            }
            if (tasks.isEmpty()) {
                logger.fine(() -> "Task " + this.getClass().getSimpleName() + ": no tasks found.");
                return;
            }
            logger.fine(() -> tm(tasks.size() + " tasks found."));

            //procesando cada tarea
            tasks.forEach(t -> {
                String id = getId.apply(t);
                try {
                    //se bloquea la tarea y si es exitoso se procesa
                    if (tryLock.apply(t)) {
                        logger.info(() -> tm(id + " dispatching."));
                        consumer.accept(t);
                        logger.info(() -> tm(id + " procesed."));
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, ex, () -> tm(id + " error while executing task. " + ex.getMessage()));
                    if (this.onTaskError != null) {
                        this.onTaskError.accept(t, ex);
                    }
                }
            });

        } finally {
            working.set(false);
        }
    }

    protected String tm(String message) {
        return MessageFormat.format("Task {0}: {1}", this.getClass().getSimpleName(), message);
    }

    @Override
    public boolean isEnabled() {
        return this.enabled.get();
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    @Override
    public boolean isWorking() {
        return this.working.get();
    }

}
