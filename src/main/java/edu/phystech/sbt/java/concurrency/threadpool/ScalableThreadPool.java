package edu.phystech.sbt.java.concurrency.threadpool;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ScalableThreadPool implements ThreadPool {
    private final ArrayList<Thread> workers = new ArrayList<>();
    private final ArrayDeque<Runnable> tasks = new ArrayDeque<>();
    private final int minWorkerCnt;
    private final int maxWorkerCnt;
    private AtomicInteger freeWorkersCnt;

    private class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable task;
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        if (workers.size() > minWorkerCnt) {
                            freeWorkersCnt.decrementAndGet();
                            workers.remove(this);
                            return;
                        }

                        try {
                            tasks.wait();
                        }
                        catch (InterruptedException ignore) {
                        }
                    }
                    freeWorkersCnt.decrementAndGet();
                    task = tasks.getFirst();
                }
                task.run();
                freeWorkersCnt.incrementAndGet();
            }
        }
    }

    ScalableThreadPool(int minWorkerCnt, int maxWorkerCnt) {
        this.minWorkerCnt = minWorkerCnt;
        this.maxWorkerCnt = maxWorkerCnt;
        this.freeWorkersCnt = new AtomicInteger(minWorkerCnt);
        for (int i = 0; i < minWorkerCnt; i++) {
            workers.add(new Worker());
        }
    }

    @Override
    public void start() {
        for (Thread thread : workers) {
            thread.start();
        }
    }

    @Override
    public void execute(Runnable task) {
        synchronized (tasks) {
            if (freeWorkersCnt.get() == 0 && workers.size() < maxWorkerCnt) {
                Worker worker = new Worker();
                workers.add(worker);
                worker.start();
            }
            tasks.add(task);
            tasks.notify();
        }
    }
}
