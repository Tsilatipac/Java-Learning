package com.lcy.mashibing.jvm;

public class Sync2 {
    public static String name;
    public static double balance;

    public static synchronized void setBalance(String name, double balance) {
        Sync2.name = name;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (Sync2.class) {
            Sync2.balance = balance;
        }
    }

    public static synchronized void getBalance() {
        System.out.println("name: "+name);
        System.out.println("balance: "+balance);
    }

    public static void main(String[] args) {
        new Thread(()->{setBalance("Lee", 50000);}).start();
        new Thread(Sync2::getBalance).start();
    }
}
