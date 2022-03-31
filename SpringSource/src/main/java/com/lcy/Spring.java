package com.lcy;

import java.util.Map;
import java.util.Properties;

public class Spring {

    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        System.out.println("-------------------environment---------------------");
        env.forEach((k, v) -> System.out.println(k + ":" + v));

        Properties properties = System.getProperties();
        System.out.println("-------------------properties---------------------");
        properties.forEach((k, v) -> System.out.println(k + ":" + v));

        System.out.println("-------------------runtime---------------------");
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.availableProcessors());
        System.out.println(runtime.freeMemory());
        System.out.println(runtime.totalMemory());
    }
}
