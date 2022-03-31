package com.lcy.basic;

import java.util.concurrent.locks.LockSupport;

public class ThreadState {
    private static final Object o = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread( () -> {
            LockSupport.park();
            try {
                Thread.sleep(10);
            } catch (Exception ignore) {
            }
            synchronized (o) {
            }
        });
        // NEW
        System.out.println(t1.getState());
        t1.start();
        // RUNNABLE
        System.out.println(t1.getState());
        t1.join(1);
        // WAITING
        System.out.println(t1.getState());
        LockSupport.unpark(t1);
        t1.join(1);
        // TIMED_WAITING
        System.out.println(t1.getState());
        synchronized (o) {
            Thread.sleep(10);
            // BLOCKED
            System.out.println(t1.getState());
        }
        t1.join(1);
        // TERMINATED
        System.out.println(t1.getState());
    }
}
