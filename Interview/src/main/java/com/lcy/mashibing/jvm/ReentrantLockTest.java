package com.lcy.mashibing.jvm;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockTest {
    Lock lock = new ReentrantLock(true);
    int value;

    public static void main(String[] args) {

        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
//        new Thread(reentrantLockTest::lockFiveSecond).start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        new Thread(reentrantLockTest::synchronizedTest).start();
//        new Thread(reentrantLockTest::reentrantLockMethod).start();
//        new Thread(reentrantLockTest::lockInterruptiblyMethod).start();
//        new Thread(reentrantLockTest::countdownLatchMethod).start();
//        new Thread(reentrantLockTest::cyclicBarrierMethod).start();
        new Thread(reentrantLockTest::SemaphoreMethod).start();

    }

    static void sleep1000() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void lockFiveSecond() {
        try {
            lock.lock();
            for (int i = 0; i < 3; i++) {
                Thread.sleep(1000);
                System.out.println(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void synchronizedTest() {
        try {
            lock.lock();
            System.out.println("method2");
        } finally {
            lock.unlock();
        }
    }

    void reentrantLockMethod() {
        boolean locked = false;
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("tryLock -> " + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) lock.unlock();
        }
    }

    void lockInterruptiblyMethod() {
        try {
            lock.lockInterruptibly();
            System.out.println("method start");
            Thread.sleep(1000);
            System.out.println("method end");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    void countdownLatchMethod() {
        Thread[] threads = new Thread[100];
        CountDownLatch countDownLatch = new CountDownLatch(threads.length);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                int result = 0;
                for (int j = 0; j < 10000; j++) result += j;
                countDownLatch.countDown();
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end latch");
    }

    void cyclicBarrierMethod() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> System.out.println("满人，发车"));

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    void phaserMethod() {
        Phaser phaser = new Phaser();
    }

    void readWriteLockMethod() {
        ReentrantLock reentrantLock = new ReentrantLock();
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();

//        Runnable readR = () -> read(reentrantLock);
        Runnable readR = () -> read(readLock);
//        Runnable writeR = () -> write(reentrantLock, new Random().nextInt());
        Runnable writeR = () -> write(writeLock, new Random().nextInt());

        for (int i = 0; i < 18; i++) new Thread(readR).start();
        for (int i = 0; i < 2; i++) new Thread(writeR).start();
    }

    void read(Lock lock) {
        try {
            lock.lock();
            sleep1000();
            System.out.println("read over");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void write(Lock lock, int v) {
        try {
            lock.lock();
            sleep1000();
            value = v;
            System.out.println("write over");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void SemaphoreMethod() {
//        Semaphore s = new Semaphore(2);
        Semaphore s = new Semaphore(2, true);

        new Thread(() -> {
            try {
                s.acquire();
                System.out.println("T1 running...");
                sleep1000();
                System.out.println("T1 running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s.release();
            }
        }).start();

        new Thread(() -> {
            try {
                s.acquire();
                System.out.println("T2 running...");
                sleep1000();
                System.out.println("T2 running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s.release();
            }
        }).start();
    }

    void exchangerMethod() {
        Exchanger<Integer> exchanger = new Exchanger<>();
        int x  = 10;
        try {
            x = exchanger.exchange(x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
