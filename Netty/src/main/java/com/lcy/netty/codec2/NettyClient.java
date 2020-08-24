package com.lcy.netty.codec2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class NettyClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup) //设置线程组
                    .channel(NioSocketChannel.class) //设置客户端通道实现类（反射）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("encoder",new ProtobufEncoder());
                            ch.pipeline().addLast(new NettyClientHandler()); //加入自己的处理器
                        }
                    });
            System.out.println("客户端OK");
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            channelFuture.channel().closeFuture().sync();
            //对关闭通道进行监听
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
