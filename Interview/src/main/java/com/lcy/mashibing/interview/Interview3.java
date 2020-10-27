package com.lcy.mashibing.interview;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Interview3<T> {
    final private LinkedList<T> lists = new LinkedList<T>();
    final private int MAX = 10;
    private int count = 0;

    public static void main(String[] args) {
        Interview3<String> interview3 = new Interview3<String>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) System.out.println(interview3.get());
            }, "thead" + i).start();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) interview3.put(Thread.currentThread().getName() + " " + j);
            }, "thead" + i).start();
        }
    }

    public synchronized void put(T t) {
        while (lists.size() == MAX) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lists.add(t);
        ++count;
        this.notifyAll();
    }

    public synchronized T get() {
        T t = null;
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.removeFirst();
        --count;
        this.notifyAll();
        return t;
    }

}
