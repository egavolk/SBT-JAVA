package edu.phystech.sbt.java.concurrency.task;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public class Task<T> {
    private Callable<? extends T> callable;
    private volatile T result;
    private AtomicBoolean startedCall = new AtomicBoolean(false);
    private AtomicBoolean finishedCall = new AtomicBoolean(false);
    private volatile RuntimeException exception;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() {
        if (finishedCall.get()) {
            if (exception == null) {
                return result;
            } else {
                throw exception;
            }
        }


        boolean needCall = startedCall.getAndSet(true);
        if (needCall) {
            synchronized (this) {
                try {
                    result = callable.call();
                    finishedCall.set(true);
                    this.notifyAll();
                    return result;
                } catch (Exception e) {
                    exception = new RuntimeException(e);
                    finishedCall.set(true);
                    this.notifyAll();
                    throw exception;
                }
            }
        }

        synchronized (this) {
            while (!finishedCall.get()) {
                try {
                    this.wait();
                } catch (InterruptedException ignore) {
                }
            }
            if (exception == null) {
                return result;
            } else {
                throw exception;
            }
        }
    }
}
