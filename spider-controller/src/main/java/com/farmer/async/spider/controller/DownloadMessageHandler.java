package com.farmer.async.spider.controller;

import com.alibaba.fastjson.JSON;
import com.farmer.async.spider.message.Constants;
import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.message.persistence.IMessagePersistence;
import com.farmer.async.spider.request.manager.BloggerRelationDownloadService;
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
public class DownloadMessageHandler {

    @Autowired
    private IMessagePersistence iMessagePersistence;

    @Autowired
    private BloggerRelationDownloadService bloggerRelationDownloadService;

    @JmsListener(destination = Constants.DOWNLOAD_QUEUE_NAME,id = "down-1")
    public void receive(String messageStr) {

        System.out.println("download message!" + messageStr);

        BaseMessage baseMessage = JSON.parseObject(messageStr,BaseMessage.class);
        String messageType = baseMessage.getMessageType();

        if (messageType.equals(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_PAGE_DOWNLOAD)) {
            bloggerRelationDownloadService.handle(JSON.parseObject(messageStr,BloggerRelationDownloadMessage.class));
        }

        iMessagePersistence.delete(baseMessage.getMessageId());
    }
}
