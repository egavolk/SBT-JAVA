package edu.phystech.sbt.java.concurrency.threadpool;

public interface ThreadPool {
    void start();

    void execute(Runnable task);
}
