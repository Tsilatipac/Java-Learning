package com.lcy.basic;

import org.junit.Test;

import java.lang.ref.SoftReference;

public class BasicClassTest {
    @Test
    public void testReference() {
        String string = "Hello everyone";
        SoftReference<String> softReference = new SoftReference<>(string);
        String string2 = softReference.get();
        System.out.println(string2);
    }

}
