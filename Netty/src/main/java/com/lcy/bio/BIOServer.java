package com.lcy.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");

        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }

    }

    //编写Handler和客户通讯
    public static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

