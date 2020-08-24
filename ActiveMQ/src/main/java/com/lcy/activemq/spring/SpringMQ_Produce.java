package com.lcy.activemq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class SpringMQ_Produce {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Produce produce = (SpringMQ_Produce) ctx.getBean("springMQ_Produce");
        produce.jmsTemplate.send((session) -> session.createTextMessage("spring 和active的整合case"));
        System.out.println("send task over...");
    }
}
