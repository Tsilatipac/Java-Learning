package com.lcy.mashibing.interview;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DeprecatedInterview3 {
    static Semaphore readSemaphore = new Semaphore(10);
    static Semaphore writeSemaphore = new Semaphore(2);
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    List<Integer> list = new LinkedList<>();

    public static void main(String[] args) {
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();

        DeprecatedInterview3 sync3 = new DeprecatedInterview3();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    writeSemaphore.acquire();
                    writeLock.lock();
                    int i1 = new Random().nextInt();
                    sync3.put(i1);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "write: "+i1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    writeLock.unlock();
                    writeSemaphore.release();
                }
            }).start();
        }
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                try {
                    readSemaphore.acquire();
                    readLock.lock();
                    int i1 = sync3.get(0);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "read: "+i1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readLock.unlock();
                    readSemaphore.release();
                }
            }).start();
        }
    }

    public void put(Integer integer) {
        list.add(integer);
    }

    public int get(int index) {
        return list.get(index);
    }

    public int getCount() {
        return list.size();
    }
}
