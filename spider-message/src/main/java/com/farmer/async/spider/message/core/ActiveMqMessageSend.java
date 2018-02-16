package com.farmer.async.spider.message.core;

import com.alibaba.fastjson.JSON;
import com.farmer.async.spider.message.Constants;
import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.definition.BaseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : ${Date}
 */
@Component
public class ActiveMqMessageSend {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue messageQueue;

    @Autowired
    private Queue downloadQueue;

    @Autowired
    private Queue parserQueue;

    @Autowired
    private Queue saveQueue;


    public void send(BaseMessage baseMessage) {

        String messageStr = JSON.toJSONString(baseMessage);

        jmsTemplate.convertAndSend(messageQueue,messageStr);
    }

    public void sendMessage(BaseMessage baseMessage,String destination) {

        String messageStr;
        switch (destination) {

            case Constants.SAVE_QUEUE_NAME:
                messageStr = JSON.toJSONString(baseMessage);
                jmsTemplate.convertAndSend(saveQueue,messageStr);
                break;
            case Constants.DOWNLOAD_QUEUE_NAME:
                messageStr = JSON.toJSONString(baseMessage);
                jmsTemplate.convertAndSend(downloadQueue,messageStr);
                break;
            case Constants.PARSER_QUEUE_NAME:
                messageStr = JSON.toJSONString(baseMessage);
                jmsTemplate.convertAndSend(parserQueue,messageStr);
                break;
            default:
                System.out.println("");
        }
    }
}
