package com.lcy.boot.activemq.boot_mq_produce;

import com.lcy.boot.activemq.BootMqProduceApplication;
import com.lcy.boot.activemq.produce.Queue_Produce;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.jms.Queue;

@SpringBootTest(classes = BootMqProduceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
class BootMqProduceApplicationTests {

    @Resource
    private Queue_Produce queue_produce;

    @Test
    public void contextLoads() throws Exception{
        queue_produce.produceMsg();
    }

}
