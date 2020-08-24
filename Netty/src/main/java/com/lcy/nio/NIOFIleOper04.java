package com.lcy.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFIleOper04 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("Netty/docs/file01.txt");
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("Netty/docs/file02.txt");
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        fileOutputStreamChannel.transferFrom(fileInputStreamChannel, 0, fileInputStreamChannel.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
