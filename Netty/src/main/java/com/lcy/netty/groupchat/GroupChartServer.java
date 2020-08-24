package com.lcy.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChartServer {
    private final int PORT;

    public GroupChartServer(int PORT) {
        this.PORT = PORT;
    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChartServer(8888).run();
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //向pipeline中加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //向pipeline中加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入自己的业务处理Handler
                            pipeline.addLast(new GroupChatServerHandler());
                        }
                    });
            System.out.println("netty服务器启动");
            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
            //监听关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
