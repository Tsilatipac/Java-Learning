package com.lcy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 得到一个Selector
        Selector selector = Selector.open();
        // 绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把ServerSocketChannel注册到Selector关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            //等待一秒，如果没有事件发生就，返回
            if (selector.select(1000) == 0) { //没有事件发生
                System.out.println("服务器等待了一秒，无连接");
                continue;
            }

            // 获取到相关的selectionKey集合
            // 如果大于0，表示已经获取到关注的事件
            // selector.selectedKeys()返回关注事件的集合
            // 通过SelectionKeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // 将socketChannel注册到selector，关注事件为OP_READ，同时给socketChannel关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端 " + new String(buffer.array()));
                }

                //手动从集合中移除当前的selectionKey，防止重复操作
                keyIterator.remove();
            }
        }
    }
}
