package com.lcy.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_Topic_Persist {
    public static final String ACTIVEMQ_URL = "tcp://192.168.0.108:61616";
    public static final String TOPIC_NAME = "Topic-persist";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer messageProducer = session.createProducer(topic);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        connection.start();

        for (int i = 1; i <= 3; i++) {
            //7 创建消息
            TextMessage textMessage = session.createTextMessage("topic-persist-msg--" + i);//理解为一个字符串
            messageProducer.send(textMessage);
        }

        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("******消息发送到MQ完成*******");
    }
}
