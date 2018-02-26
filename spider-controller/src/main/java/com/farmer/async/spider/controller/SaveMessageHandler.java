package com.farmer.async.spider.controller;

import com.alibaba.fastjson.JSON;
import com.farmer.async.spider.message.Constants;
import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.message.persistence.IMessagePersistence;
import com.farmer.async.spider.save.message.BloggerRelationSaveMessage;
import com.farmer.async.spider.save.service.BloggerRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/4
 */
@Component
public class SaveMessageHandler {

    @Autowired
    private IMessagePersistence iMessagePersistence;

    @Autowired
    private BloggerRelationService bloggerRelationService;

    @JmsListener(destination = Constants.SAVE_QUEUE_NAME)
    public void receive(String messageStr) {

        System.out.println("save message!" + messageStr);

        BaseMessage baseMessage = JSON.parseObject(messageStr,BaseMessage.class);
        String messageType = baseMessage.getMessageType();
        if (messageType.equals(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_SAVE)) {
            bloggerRelationService.handle(JSON.parseObject(messageStr,BloggerRelationSaveMessage.class));
        }

        iMessagePersistence.delete(baseMessage.getMessageId());
    }
}
