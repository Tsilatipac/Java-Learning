package com.lcy.nio.practice;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NIOServerPractice {

    private static final int PORT = 8888;
    private static Selector selector;
    private static ServerSocketChannel serverSocketChannel;

    public NIOServerPractice() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NIOServerPractice server = new NIOServerPractice();
        try {
            while (true) {
                int select = selector.select(2000);
                if (select > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel sc = serverSocketChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + " 上线");
                        }
                        if (key.isReadable()) {
                            SocketChannel channel = null;
                            try {
                                channel = (SocketChannel) key.channel();
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                int count = channel.read(buffer);
                                if (count > 0) {
                                    String msg = channel.getRemoteAddress() + ": " + new String(buffer.array());
                                    System.out.println(msg);
                                    server.sendMessageToOther(selector, msg, channel);
                                }
                            } catch (IOException e) {
                                try {
                                    System.out.println(channel.getRemoteAddress() + " 离线了...");
                                    key.channel();
                                    channel.close();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        }
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待...");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToOther(Selector selector, String msg, SocketChannel self) throws IOException {
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel dest = (SocketChannel) targetChannel;
                dest.write(ByteBuffer.wrap(msg.getBytes()));
            }
        }

    }
}
