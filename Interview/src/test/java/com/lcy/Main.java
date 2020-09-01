package com.lcy;

import com.lcy.bigcompany.session1.singleton.Singleton_Enum;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Singleton_Enum.INSTANCE.getInstance();
    }
}