package com.lcy.basic;

import org.junit.Test;

import java.lang.ref.SoftReference;
import java.util.Random;

public class BasicClassTest {
    @Test
    public void testReference() {
        String string = "Hello everyone";
        SoftReference<String> softReference = new SoftReference<>(string);
        String string2 = softReference.get();
        System.out.println(string2);
    }
    @Test
    public void testRandom() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
//            System.out.print(random.nextInt(21)+"\t");
            System.out.print(i+"\t");
        }
    }
}
