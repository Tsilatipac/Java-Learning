package com.lcy.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义Handler需要继承netty规定好对HandlerAdapter，此时才能称为Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据
     *
     * @param ctx 上下文对象，含有管道pipeline
     * @param msg 客户端发送对数据 默认是object
     * @throws Exception 抛出的异常
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        //msg转成一个byteBuf（Netty提供的，不是NIO的ByteBuffer）
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址：" + ctx.channel().remoteAddress());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //数据写入管道并刷新
        //一般需要进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端", CharsetUtil.UTF_8));
    }

    //处理异常，一般需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
