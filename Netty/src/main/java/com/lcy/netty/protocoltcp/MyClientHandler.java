package com.lcy.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 5; i++) {
            String msg = "今天天气不错";
            byte[] content = msg.getBytes(CharsetUtil.UTF_8);
            int length = msg.getBytes(CharsetUtil.UTF_8).length;
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setContent(content);
            messageProtocol.setLen(length);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("客户端接收到的消息如下");
        System.out.println("长度=" + len);
        System.out.println("内容=" + new String(content, CharsetUtil.UTF_8));
        System.out.println("客户端收到的消息数量=" + (++this.count));
    }
}
