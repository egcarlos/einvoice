/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.recurrent;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import pe.labtech.einvoice.commons.jndi.JNDI;

/**
 * Abstract template for recurrent tasks that will handle the
 *
 * @author @{@link mailto:info@labtech.pe}
 * @param <T> Type of the task to be procesed
 */
public abstract class AbstractRecurrentTask<T> implements RecurrentTask {

    //BUGFIX for ASE 15
    private static final ConcurrentHashMap<String, Boolean> lock = new ConcurrentHashMap<>();

    /**
     * Logger instance to be used in subclasses.
     */
    protected final Logger logger = Logger.getLogger(getTaskId());

    /**
     * Holder for the working flag. Read only.
     */
    private AtomicBoolean working;

    /**
     * Holder for the enabled flag. Read/Write.
     */
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

    /**
     * Async executor service.
     */
    protected ExecutorService service;

    @PostConstruct
    public void init() {
        logger.info(() -> "Task " + this.getClass().getSimpleName() + ": created.");
        this.working = new AtomicBoolean(false);
        this.enabled = new AtomicBoolean(true);
        lock.putIfAbsent(getTaskId(), Boolean.FALSE);
        this.getId = t -> t != null ? t.toString() : "invaid";
        this.service = JNDI.getInstance().lookup("java:global/einvoice/executor/" + this.getClass().getSimpleName());
    }

    protected void timeout() {
        if (!enabled.get()) {
            return;
        }
        //Obteniendo lock en el bean para procesar
        if (tryLock()) {
            logger.fine(() -> "Task " + this.getClass().getSimpleName() + ": in use, skipping.");
            return;
        }
        try {
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
                if (tryLock.apply(t)) {
                    if (this.service == null) {
                        dispatch(id, t);
                    } else {
                        service.submit(() -> dispatch(id, t));
                    }
                }
            });

        } finally {
            unlock();
        }
    }

    private void dispatch(String id, T t) {
        try {
            logger.info(() -> tm(id + " dispatching."));
            //se bloquea la tarea y si es exitoso se procesa
            consumer.accept(t);
            logger.info(() -> tm(id + " procesed."));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex, () -> tm(id + " error while executing task. " + ex.getMessage()));
            if (this.onTaskError != null) {
                this.onTaskError.accept(t, ex);
            }
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

    private String getTaskId() {
        return this.getClass().getName();
    }

    public boolean tryLock() {
        return lock.replace(getTaskId(),
                Boolean.FALSE,
                Boolean.TRUE
        );
    }

    public void unlock() {
        lock.replace(getTaskId(),
                Boolean.FALSE
        );
    }

}
