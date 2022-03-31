package com.lcy.basic;

import java.util.ArrayList;

public class Test1 {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <=6; j++) {
                for (int k = 1; k <= 6; k++) {
                    for (int l = 1; l <=6; l++) {
                        strings.add("" + i + j + k + l);
                    }
                }
            }
        }
        int count=0;
        for (String string : strings) {
            if (string.contains("6")) {
                count++;
            }
        }
        System.out.println(strings.size());
        System.out.println(count);
    }
}
