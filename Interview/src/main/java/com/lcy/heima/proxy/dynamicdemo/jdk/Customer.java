package com.lcy.heima.proxy.dynamicdemo.jdk;

public class Customer implements OrderInterface{
    @Override
    public String order(String foodName) {
        return "已经下单了" + foodName;
    }

    @Override
    public void test1() {

    }

    @Override
    public void test2() {

    }

}
