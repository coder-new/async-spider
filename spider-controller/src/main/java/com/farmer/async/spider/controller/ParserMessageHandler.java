package com.farmer.async.spider.controller;

import com.alibaba.fastjson.JSON;
import com.farmer.async.spider.message.Constants;
import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.message.persistence.IMessagePersistence;
import com.farmer.async.spider.parser.manager.BloggerRelationPageParserService;
import com.farmer.async.spider.parser.message.BloggerRelationPageParserMessage;
import com.farmer.async.spider.request.message.BloggerRelationDownloadMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/4
 */
@Component
public class ParserMessageHandler {

    @Autowired
    private IMessagePersistence iMessagePersistence;

    @Autowired
    private BloggerRelationPageParserService bloggerRelationPageParserService;

    @JmsListener(destination = Constants.PARSER_QUEUE_NAME)
    public void receive(String messageStr) {

        System.out.println("parser message!" + messageStr);

        BaseMessage baseMessage = JSON.parseObject(messageStr,BaseMessage.class);
        String messageType = baseMessage.getMessageType();

        if (messageType.equals(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_PAGE_PARSER)) {
            bloggerRelationPageParserService.handle(JSON.parseObject(messageStr,BloggerRelationPageParserMessage.class));
        }

        iMessagePersistence.delete(baseMessage.getMessageId());
    }
}
