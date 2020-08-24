package com.lcy.nio;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class BasicBuffer {
    public static void main(String[] args) throws Exception {

        //使用ServerSocketChannel和SocketChannel网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8888);

        //绑定端口到Socket并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建Buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;

        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long l = socketChannel.read(byteBuffers);
                byteRead += l; //累计读取的字节数
                System.out.println("byteRead = " + byteRead);
                Arrays.stream(byteBuffers).map(buffer -> "position = " +
                        buffer.position() + ", limit = " + buffer.limit()).forEach(System.out::println);
            }
            Arrays.asList(byteBuffers).forEach(Buffer::flip);
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }
            Arrays.asList(byteBuffers).forEach(Buffer::clear);
            System.out.println("byteRead = " + byteRead + " byteWrite = " + byteWrite);
        }

    }

    public void intBuffer() {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
            System.out.println("done");
        }
    }

    public void mappedByteBuffer() throws Exception {
        /*
         * 参数1：使用读写模式
         * 参数2：0：可以直接修改的起始位置
         * 参数3：5：映射到内存的大小，
         */
        RandomAccessFile randomAccessFile = new RandomAccessFile("Netty/docs/file01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'A');
        mappedByteBuffer.put(3, (byte) '9');
        randomAccessFile.close();
    }
}
