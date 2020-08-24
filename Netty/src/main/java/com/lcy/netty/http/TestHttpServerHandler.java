package com.lcy.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestHttpServerHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        //netty提供的处理http的编解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //添加一个自定义的Handler
        pipeline.addLast("MyTestServerInitializer", new TestServerInitializer());
    }
}
