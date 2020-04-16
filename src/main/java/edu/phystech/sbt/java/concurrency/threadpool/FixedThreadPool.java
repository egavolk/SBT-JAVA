package edu.phystech.sbt.java.concurrency.threadpool;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class FixedThreadPool implements ThreadPool {
    private final ArrayList<Thread> workers = new ArrayList<>();
    private final ArrayDeque<Runnable> tasks = new ArrayDeque<>();

    private class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable task;
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        }
                        catch (InterruptedException ignore) {
                        }
                    }
                    task = tasks.getFirst();
                }
                task.run();
            }
        }
    }

    FixedThreadPool(int size) {
        for (int i = 0; i < size; i++) {
            workers.add(new Worker());
        }
    }

    @Override
    public void start() {
        for (Thread worker : workers) {
            worker.start();
        }
    }

    @Override
    public void execute(Runnable task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }
}
