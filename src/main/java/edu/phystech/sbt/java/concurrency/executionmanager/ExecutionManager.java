package edu.phystech.sbt.java.concurrency.executionmanager;

public interface ExecutionManager {

    Context execute(Runnable callback, Runnable... tasks);

}