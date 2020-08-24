package com.lcy.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个channel组，管理所有的channel
    //GlobalEventExecutor.INSTANCE是一个全局事件执行器，是一个单例
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    //handlerAdded表示一旦建立连接，第一个被执行
    //将当前channel加入到channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //writeAndFlush会将所有的channel遍历并发送
        channelGroup.writeAndFlush("【客户端】" + channel.remoteAddress() + " 加入聊天\n");
        channelGroup.add(channel);
    }

    //断开连接，将离线信息推送给所有用户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //writeAndFlush会将所有的channel遍历并发送
        channelGroup.writeAndFlush("【客户端】" + channel.remoteAddress() + " 离开了\n");
        //一旦触发handlerRemoved，会自动从channelGroup中移除
    }

    //表示channel处于活动状态，提示xxx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了～");
    }

    //表示channel处于不活跃状态，提示xxx离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了～");
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        //遍历channelGroup，根据不同的情况，回送不同的消息
        channelGroup.forEach(ch -> {
            if (channel != ch) { //不是当前的channel
                ch.writeAndFlush("【客户】" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + channel.remoteAddress() + " 发送消息" + msg + "\n");
            } else {
                ch.writeAndFlush("【自己】发送了消息" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常时关闭通道
        ctx.close();
    }
}
