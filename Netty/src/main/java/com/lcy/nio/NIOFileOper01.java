package com.lcy.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileOper01 {
    public static void main(String[] args) throws Exception {
        String str = "Hello, Mr. Lee";

        File file = new File("Netty/docs/file01.txt");
        boolean mkdirs = file.getParentFile().mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        // 通过FileOutputStream获取FileChannel
        // FileChannel真实类型是FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
