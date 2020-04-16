package edu.phystech.sbt.java.concurrency.executionmanager;

public class RunnableWrapper implements Runnable {
    private final Runnable task;
    private final ContextImpl context;

    public RunnableWrapper(Runnable task, ContextImpl context) {
        this.task = task;
        this.context = context;
    }

    @Override
    public void run() {
        if (context.isInterrupted()) {
            return;
        }
        context.startTask();
        try {
            task.run();
            context.completeTask();
        }
        catch (Exception e) {
            context.failTask();
        }
    }
}
