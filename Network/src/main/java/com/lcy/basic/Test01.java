package com.lcy.basic;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Arrays;

public class Test01 {
    public static void main(String[] args) throws Exception{
        InetAddress ia1 = InetAddress.getByName("192.168.1.1");
        InetAddress ia2 = InetAddress.getByName("localhost");
        InetAddress ia3 = InetAddress.getByName("www.baidu.com");
        InetAddress ia4 = InetAddress.getByName("MacBook-Pro-16.local");
        System.out.println(ia1);
        System.out.println(ia2);
        System.out.println(ia3);
        System.out.println(ia4);
        System.out.println(Arrays.toString(ia3.getAddress()));
        System.out.println("--------------------------------");
        InetSocketAddress unresolved = InetSocketAddress.createUnresolved("www.baiud.com", 80);
        System.out.println(unresolved);
        System.out.println(unresolved.getHostName());
        System.out.println(unresolved.getHostString());
        System.out.println(unresolved.getPort());
        InetAddress address = unresolved.getAddress();
    }
}
