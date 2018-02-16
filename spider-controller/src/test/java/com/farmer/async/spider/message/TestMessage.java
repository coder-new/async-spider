package com.farmer.async.spider.message;

import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.message.definition.BaseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/10/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMessage {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestMessage.class);

    @Autowired
    private ActiveMqMessageSend activeMqMessageSend;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test() {

        BaseMessage baseMessage1 = new BaseMessage();
        baseMessage1.setRequestId(UUID.randomUUID().toString());
        baseMessage1.setMessageId("123");
        baseMessage1.setMessageType("1");
        activeMqMessageSend.send(baseMessage1);

        BaseMessage baseMessage2 = new BaseMessage();
        baseMessage2.setRequestId(UUID.randomUUID().toString());
        baseMessage2.setMessageId("123");
        baseMessage2.setMessageType("2");
        activeMqMessageSend.send(baseMessage2);

        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSend() {

        BaseMessage baseMessage1 = new BaseMessage();
        baseMessage1.setRequestId("1");
        activeMqMessageSend.sendMessage(baseMessage1,Constants.DOWNLOAD_QUEUE_NAME);

        BaseMessage baseMessage2 = new BaseMessage();
        baseMessage2.setRequestId("2");
        activeMqMessageSend.sendMessage(baseMessage2,Constants.PARSER_QUEUE_NAME);

        BaseMessage baseMessage3 = new BaseMessage();
        baseMessage3.setRequestId("3");
        activeMqMessageSend.sendMessage(baseMessage3,Constants.SAVE_QUEUE_NAME);
    }

}
