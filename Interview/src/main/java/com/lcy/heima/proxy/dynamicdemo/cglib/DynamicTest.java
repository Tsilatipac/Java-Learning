package com.lcy.heima.proxy.dynamicdemo.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class DynamicTest {
    public static void main(String[] args) {
        Customer customer = new Customer();
        //使用CGLIB创建代理对象
        Customer deliveryClerk = (Customer) Enhancer.create(customer.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if ("order".equals(method.getName())) {
                    Object result = method.invoke(customer, objects);
                    System.out.println("添加的内容1");
                    return result + ",添加的返回值内容";
                }
                return null;
            }
        });
        String result = deliveryClerk.order("鱼香肉丝");
        System.out.println(result);
    }
}
