package com.lcy;

import sun.misc.Launcher;

import java.net.URL;
import java.util.concurrent.Executors;

public class ClassLoaderTest {
    public static void main(String[] args) {
        System.out.println("*******启动类加载器*******");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL.toExternalForm());
        }
        System.out.println("*******扩展类加载器*******");
        String extDirs = System.getProperty("java.ext.dirs");
        for (String path : extDirs.split(";")) {
            System.out.println(path);
        }
        B.hello();
    }
    static void  hello() {
        System.out.println("asdf");
    }

    static class B extends ClassLoader {
        static void hello() {
            System.out.println("9999999");
        }
    }
}
