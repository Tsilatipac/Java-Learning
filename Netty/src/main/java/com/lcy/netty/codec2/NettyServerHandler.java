package com.lcy.netty.codec2;

import com.lcy.netty.codec.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 自定义Handler需要继承netty规定好对HandlerAdapter，此时才能称为Handler
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    /**
     * 读取数据
     *
     * @param ctx 上下文对象，含有管道pipeline
     * @param msg 客户端发送对数据 默认是object
     * @throws Exception 抛出的异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == MyDataInfo.MyMessage.DataType.StudentType) {
            MyDataInfo.Student student = msg.getStudent();
            System.out.println("学生id="+student.getId()+" 学生名字"+student.getName());
        } else if (dataType == MyDataInfo.MyMessage.DataType.WorkerType) {
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println("工人的名字="+worker.getName()+" 年龄"+worker.getAge());
        } else {
            System.out.println("传输的类型不正确");
        }
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
