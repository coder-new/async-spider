package com.farmer.async.spider.controller;

import com.alibaba.fastjson.JSON;
import com.farmer.async.spider.message.Constants;
import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.message.persistence.IMessagePersistence;
import com.farmer.async.spider.service.BloggerService;
import com.farmer.async.spider.service.message.BloggerListMessage;
import com.farmer.async.spider.service.message.BloggerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/25
 */
@Component
public class BloggerMessageHandler {

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private IMessagePersistence iMessagePersistence;

    @JmsListener(destination = Constants.BLOGGER_QUEUE_NAME)
    public void receive(String messageStr) {

        System.out.println("blogger message!" + messageStr);

        BaseMessage baseMessage = JSON.parseObject(messageStr,BaseMessage.class);
        String messageType = baseMessage.getMessageType();

        if (messageType.equals(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_BLOGGER)) {
            bloggerService.handle(JSON.parseObject(messageStr,BloggerMessage.class));
        } else if (messageType.equals(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_BLOGGER_LIST)) {
            bloggerService.handleList(JSON.parseObject(messageStr, BloggerListMessage.class));
        }

        iMessagePersistence.delete(baseMessage.getMessageId());
    }
}
