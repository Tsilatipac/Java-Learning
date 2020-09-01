package com.lcy.bigcompany.session1.singleton;

public class Singleton_Hungry {
    public static final Singleton_Hungry instance = new Singleton_Hungry();

    private Singleton_Hungry() {

    }

    public static Singleton_Hungry getInstance() {
        return instance;
    }
}
