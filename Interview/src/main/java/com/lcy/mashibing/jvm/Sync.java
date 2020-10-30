package com.lcy.mashibing.jvm;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Sync {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger();
        i.incrementAndGet();

        StringBuffer stringBuffer = new StringBuffer();

        StringBuilder stringBuilder = new StringBuilder();
        Object o = new Object();
        int[] ints = new int[8];

        ArrayList arrayList = new ArrayList();
        ArrayList arrayList1 = (ArrayList) arrayList.clone();

        String string = "Hello";

        System.out.println(ClassLayout.parseInstance(ints).toPrintable());

//        Instant start = Instant.now();
//        int sum = 0;
//        for (int i = 0; i < 10000_0000; i++) {
//            sum += 100;
//        }
//        System.out.println(Duration.between(start, Instant.now()).toMillis());

        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

//        ThreadLocal<String> tl = new ThreadLocal<>();
//        tl.set("Happy new year");

//        int integer = 5;
//        System.out.println(ClassLayout.parseInstance(integer).toPrintable());

    }
}
