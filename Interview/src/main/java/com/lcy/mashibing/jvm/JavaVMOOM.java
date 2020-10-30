package com.lcy.mashibing.jvm;

import java.util.TreeSet;

public class JavaVMOOM {
    public static void main(String[] args) {
        int i = 1;
        while (true) {
            System.out.println(i++);
            new Thread(()->{
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                while (true) {
//                }
            }).start();
        }
    }
}
