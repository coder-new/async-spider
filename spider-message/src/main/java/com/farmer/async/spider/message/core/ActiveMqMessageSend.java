package com.farmer.async.spider.message.core;

import com.alibaba.fastjson.JSON;
import com.farmer.async.spider.message.Constants;
import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.message.persistence.IMessagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

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

    @Autowired
    private Queue bloggerQueue;

    @Autowired
    private IMessagePersistence iMessagePersistence;

    @Autowired
    private MessageCountManager messageCountManager;

    public void send(BaseMessage baseMessage) {

        String messageStr = JSON.toJSONString(baseMessage);

        jmsTemplate.convertAndSend(messageQueue,messageStr);
    }

    public void sendMessageWithPersistence(BaseMessage baseMessage,String destination) {

        String messageStr = JSON.toJSONString(baseMessage);
        iMessagePersistence.persistence(baseMessage.getMessageId(),destination,messageStr);
        sendMessage(messageStr,destination);
    }

    public void sendMessageWithoutPersistence(String messageStr,String destination) {

        sendMessage(messageStr,destination);
    }

    private void sendMessage(String messageStr,String destination) {

        switch (destination) {

            case Constants.SAVE_QUEUE_NAME:
                jmsTemplate.convertAndSend(saveQueue,messageStr);
                break;
            case Constants.DOWNLOAD_QUEUE_NAME:
                jmsTemplate.convertAndSend(downloadQueue,messageStr);
                break;
            case Constants.PARSER_QUEUE_NAME:
                jmsTemplate.convertAndSend(parserQueue,messageStr);
                break;
            case Constants.BLOGGER_QUEUE_NAME:
                jmsTemplate.convertAndSend(bloggerQueue,messageStr);
                break;
            default:
                System.out.println("");
        }

        messageCountManager.increment();

    }
}
