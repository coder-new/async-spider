package com.farmer.async.spider.controller.api;

import com.farmer.async.spider.controller.cnblogs.HomePageSpiderEndPoint;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.message.definition.BaseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/10
 */
@RestController
public class SpiderEndPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderEndPoint.class);

    @Autowired
    private ActiveMqMessageSend activeMqMessageSend;

    @Autowired
    private HomePageSpiderEndPoint homePageSpiderEndPoint;

    @RequestMapping(value = "/spider",
            method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void start() {

        LOGGER.info("start time:" + String.valueOf(System.currentTimeMillis()));

        testSendMessage();

        //testHomePage();
    }

    private void testHomePage() {

        //homePageSpiderEndPoint.start();
    }

    private void testSendMessage() {

        for (int i=0;i<1;i++) {
            BaseMessage baseMessage = new BaseMessage();
            baseMessage.setMessageId(String.valueOf(i));
            baseMessage.setMessageType("1");
            baseMessage.setRequestId(UUID.randomUUID().toString());

            activeMqMessageSend.send(baseMessage);
        }
    }
}
