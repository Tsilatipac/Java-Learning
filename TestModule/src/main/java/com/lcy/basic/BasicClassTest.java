package com.lcy.basic;

import org.junit.Test;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
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
    public void testRuntime() throws IllegalAccessException, InstantiationException {
        Class<? extends Runtime> aClass = Runtime.getRuntime().getClass();
        Constructor<?>[] constructors = aClass.getConstructors();
        System.out.println(constructors);
    }

    @Test
    public void testRandom() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
//            System.out.print(random.nextInt(21)+"\t");
            System.out.print(i+"\t");
        }
    }

    @Test
    public void testForeach() {
        List<String> list = new ArrayList<>(); list.add("1");
        list.add("2");
        for (String item : list) {
            if ("2".equals(item)) {
                list.remove(item); }
        }
    }
}
