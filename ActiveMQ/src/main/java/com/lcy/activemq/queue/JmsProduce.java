package com.lcy.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce {
    public static final String ACTIVE_URL = "tcp://192.168.0.108:61616";
    //    public static final String ACTIVE_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        //1 创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVE_URL);
        //2 通过连接工厂，获得连接connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3 创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4 创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //6 通过使用messageProducer生产3条消息发送到MQ的消息队列
        for (int i = 1; i <= 3; i++) {
            //7 创建消息
            TextMessage textMessage = session.createTextMessage("msg---" + i);//理解为一个字符串
            messageProducer.send(textMessage);
        }
        //关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("******消息发送到MQ完成*******");
    }
}
