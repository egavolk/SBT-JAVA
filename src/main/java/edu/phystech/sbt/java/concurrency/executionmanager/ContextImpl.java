package edu.phystech.sbt.java.concurrency.executionmanager;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ContextImpl implements Context {
    private final AtomicInteger completed = new AtomicInteger(0);
    private final AtomicInteger failed = new AtomicInteger(0);
    private final AtomicInteger waiting;
    private final AtomicBoolean interrupted = new AtomicBoolean(false);
    private final int cnt;
    private final Runnable callback;

    ContextImpl(Runnable callback, int cnt) {
        this.callback = callback;
        this.waiting = new AtomicInteger(cnt);
        this.cnt = cnt;
    }

    @Override
    public int getCompletedTaskCount() {
        return completed.get();
    }

    @Override
    public int getFailedTaskCount() {
        return failed.get();
    }

    @Override
    public int getInterruptedTaskCount() {
        return waiting.get();
    }

    @Override
    public void interrupt() {
        interrupted.set(true);
    }

    @Override
    public boolean isFinished() {
        return completed.get() + failed.get() == cnt;
    }

    boolean isInterrupted() {
        return interrupted.get();
    }

    void completeTask() {
        completed.incrementAndGet();
    }

    void failTask() {
        failed.incrementAndGet();
    }

    void startTask() {
        waiting.decrementAndGet();
    }
}
