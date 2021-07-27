package com.lcy.heima.proxy.staticdemo;

public class StaticDemoTest {
    public static void main(String[] args) {
        Customer customer = new Customer();
        String result = customer.order("鱼香肉丝");
        System.out.println(result);
    }
}
