package com.lcy.heima.proxy.dynamicdemo.jdk;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicTest {
    public static void main(String[] args) {
        Customer customer = new Customer();

        //JDK的API，动态的生成一个代理对象
        OrderInterface deliveryClerk = (OrderInterface) Proxy.newProxyInstance(
                customer.getClass().getClassLoader(),
                customer.getClass().getInterfaces(),
                (proxy, method, args1) -> {
                    Object result = method.invoke(customer, args1);
                    if ("order".equals(method.getName())) {
                        System.out.println("代理对象添加的方法");
                        return result + "代理对象添加的返回值";
                    } else {
                        return method.invoke(customer,args1);
                    }
                });

        //调用代理对象，执行对应方法
        String result = deliveryClerk.order("红烧鸡块");
        System.out.println(result);
        deliveryClerk.test1();
    }
}
