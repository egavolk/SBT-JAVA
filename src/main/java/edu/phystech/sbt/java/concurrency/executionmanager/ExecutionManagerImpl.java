package edu.phystech.sbt.java.concurrency.executionmanager;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutionManagerImpl implements ExecutionManager {
    private final ArrayList<ContextImpl> contexts = new ArrayList<>();
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    @Override
    public ContextImpl execute(Runnable callback, Runnable... tasks) {
        ContextImpl context = new ContextImpl(callback, tasks.length);
        for (Runnable task : tasks) {
            executor.execute(new RunnableWrapper(task, context));
        }
        return context;
    }
}
