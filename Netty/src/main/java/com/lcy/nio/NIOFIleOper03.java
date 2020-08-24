package com.lcy.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFIleOper03 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("Netty/docs/file01.txt");
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("Netty/docs/file02.txt");
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {
            int read = fileInputStreamChannel.read(byteBuffer);
            if (read != -1) {
                byteBuffer.flip();
                fileOutputStreamChannel.write(byteBuffer);
                byteBuffer.clear();
            } else {
                break;
            }
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
