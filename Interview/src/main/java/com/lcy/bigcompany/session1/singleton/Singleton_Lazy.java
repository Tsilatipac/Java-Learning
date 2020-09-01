package com.lcy.bigcompany.session1.singleton;

public class Singleton_Lazy {
    private static volatile Singleton_Lazy INSTANCE;

    private Singleton_Lazy() {

    }

    public static Singleton_Lazy getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (Singleton_Lazy.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton_Lazy();
                }
            }
        }

        return INSTANCE;
    }
}
