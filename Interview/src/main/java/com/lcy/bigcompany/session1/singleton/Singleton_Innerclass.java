package com.lcy.bigcompany.session1.singleton;

public class Singleton_Innerclass {
    public static final Singleton_Innerclass INSTANCE = new Singleton_Innerclass();

    private Singleton_Innerclass() {

    }

    public static Singleton_Innerclass getINSTANCE() {
        return INSTANCE;
    }


}
