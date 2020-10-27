package com.lcy.mashibing.interview;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * <p>
 * 使用wait和notify/notifyAll来实现
 * <p>
 * 使用Lock和Condition来实现
 * 对比两种方式，Condition的方式可以更加精确的指定哪些线程被唤醒
 */
public class Interview3_2<T> {
    private final LinkedList<T> list = new LinkedList<>();
    private final int MAX = 10;
    private final Lock lock = new ReentrantLock();
    private final Condition consumer = lock.newCondition();
    private final Condition producer = lock.newCondition();
    private int count = 0;

    public static void main(String[] args) {
        Interview3_2<String> thisThread = new Interview3_2<>();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    System.out.println(thisThread.get());
                    ;
                }
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    thisThread.put(new Random().nextInt() + "");
                }
            }).start();
        }
    }

    public void put(T t) {
        try {
            lock.lock();
            while (count == MAX) {
                producer.await();
            }
            list.add(t);
            ++count;
            consumer.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();

        }
    }

    public T get() {
        T t = null;
        try {
            lock.lock();
            while (count == 0) {
                consumer.await();
            }
            t = list.removeFirst();
            --count;
            producer.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }

}
