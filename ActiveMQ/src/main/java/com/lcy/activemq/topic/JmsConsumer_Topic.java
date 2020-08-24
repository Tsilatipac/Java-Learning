package com.lcy.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Topic {
    public static final String ACTIVE_URL = "tcp://192.168.0.108:61616";
    public static final String TOPIC_NAME = "topic-bruce";

    public static void main(String[] args) throws JMSException, IOException {
        //1 创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVE_URL);
        //2 通过连接工厂，获得连接connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3 创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4 创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        //5 创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        //通过监听方式来消费消息
        messageConsumer.setMessageListener((message ->{
            if (null != message && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("消费者接受到消息" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }));
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
