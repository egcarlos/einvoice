/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.recurrent;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

/**
 *
 * @author Carlos
 * @param <T>
 */
public abstract class AbstractRecurrentTask<T> {

    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    private AtomicBoolean flag;

    protected Callable<List<T>> findTasks;

    protected Function<T, Boolean> tryLock;

    protected Function<T, String> getId;

    protected Consumer<T> consumer;

    @PostConstruct
    public void init() {
        logger.info(() -> "Task " + this.getClass().getSimpleName() + ": created.");
        flag = new AtomicBoolean(false);
    }

    protected void timeout() {
        if (!flag.compareAndSet(false, true)) {
            logger.fine(() -> "Task " + this.getClass().getSimpleName() + ": in use, skipping.");
            return;
        }
        try {
            List<T> tasks = findTasks.call();
            if (tasks.isEmpty()) {
                logger.fine(() -> "Task " + this.getClass().getSimpleName() + ": no tasks found.");
                return;
            }
            logger.fine(() -> "Task " + this.getClass().getSimpleName() + ": " + tasks.size() + " tasks found.");
            tasks.forEach(t -> {
                if (tryLock.apply(t)) {
                    logger.info(() -> "Task " + this.getClass().getSimpleName() + ": " + getId.apply(t) + " dispatching.");
                    consumer.accept(t);
                }
            });
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex, () -> "Task " + this.getClass().getSimpleName() + ": Error while executing task. " + ex.getMessage());
        } finally {
            flag.set(false);
        }
    }

}
