package com.lcy.boot.activemq.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.UUID;

@Component
public class TopicProduce {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    Topic topic;

    @Scheduled(fixedDelay = 3000)
    public void produceTopic() {
        jmsMessagingTemplate.convertAndSend(topic, "主题消息：" + UUID.randomUUID().toString().substring(0, 6));
    }

}
