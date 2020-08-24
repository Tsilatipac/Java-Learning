package com.lcy.nio.practice;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class NIOClientPractice {

    public static final int PORT = 8888;
    public static final String HOST = "127.0.0.1";
    private static Selector selector;
    private static SocketChannel socketChannel;

    public NIOClientPractice() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NIOClientPractice client = new NIOClientPractice();
        new Thread(client::receiveMsg).start();
        client.sendMsg();

    }

    public  void receiveMsg() {
        while (true) {
            try {
                int select = selector.select(2000);
                if (select > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isReadable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            channel.read(buffer);
                            System.out.println(new String(buffer.array()).trim());
                        }
                    }
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMsg() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
            try {
                socketChannel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
