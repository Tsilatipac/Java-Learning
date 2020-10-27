package com.lcy.mashibing.interview;

import java.util.concurrent.locks.LockSupport;

public class Interview2 {
    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        t1 = new Thread(() -> {

            for (char i = 'A'; i <= 'Z'; i++) {
                System.out.print(i);
                LockSupport.unpark(t2);
                LockSupport.park();
            }

        });
        t2 = new Thread(() -> {
            for (int i = 1; i < 27; i++) {
                LockSupport.park();
                System.out.print(i);
                LockSupport.unpark(t1);
            }
        });
        t1.start();
        t2.start();
    }
}
