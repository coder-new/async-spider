package com.farmer.async.spider.controller;

import com.alibaba.fastjson.JSON;
import com.farmer.async.spider.handler.threadpool.TaskExecute;
import com.farmer.async.spider.message.Constants;
import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.parser.message.BloggerHomeParserMessage;
import com.farmer.async.spider.parser.message.BloggerRelationPageParserMessage;
import com.farmer.async.spider.parser.message.BloggerUidParserMessage;
import com.farmer.async.spider.parser.message.HomePageParserMessage;
import com.farmer.async.spider.parser.task.BloggerHomeParserTask;
import com.farmer.async.spider.parser.task.BloggerRelationPageParserTask;
import com.farmer.async.spider.parser.task.BloggerUidParserTask;
import com.farmer.async.spider.parser.task.HomePageParserTask;
import com.farmer.async.spider.request.message.BloggerDocumentDownloadMessage;
import com.farmer.async.spider.request.message.BloggerHomeDownloadMessage;
import com.farmer.async.spider.request.message.BloggerRelationDownloadMessage;
import com.farmer.async.spider.request.message.HomePageMessage;
import com.farmer.async.spider.request.task.BloggerDocumentDownloadTask;
import com.farmer.async.spider.request.task.BloggerHomeDownloadTask;
import com.farmer.async.spider.request.task.BloggerRelationPageDownloadTask;
import com.farmer.async.spider.request.task.HomePageHttpTask;
import com.farmer.async.spider.save.message.BloggerRelationSaveMessage;
import com.farmer.async.spider.save.message.BloggerSaveMessage;
import com.farmer.async.spider.save.message.BloggerUidUpdateMessage;
import com.farmer.async.spider.save.task.BloggerRelationSaveTask;
import com.farmer.async.spider.save.task.BloggerSaveTask;
import com.farmer.async.spider.save.task.BloggerUidUpdateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/10/28
 */
@Component
public class MessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);

    @Autowired
    private TaskExecute taskExecute;

    @Value("${message.handler.thread.pool.queuesize}")
    private int handlerQueueSize;

    @JmsListener(destination = Constants.QUEUE_NAME)
    public void receive(String messageStr) {

        LOGGER.info("receive : {}",messageStr);

        submitToPool(messageStr);
    }

    private void submitToPool(final String messageStr) {

        BaseMessage baseMessage = JSON.parseObject(messageStr,BaseMessage.class);
        String messageType = baseMessage.getMessageType();

        switch (messageType) {

            case MessageType.Cnblog.Homepage.CNBLOG_HOMEPAGE_DOWNLOAD :

                HomePageMessage homePageMessage = JSON.parseObject(messageStr,HomePageMessage.class);
                taskExecute.submit(new HomePageHttpTask(homePageMessage));

                break;

            case MessageType.Cnblog.Homepage.CNBLOG_HOMEPAGE_PARSER :

                HomePageParserMessage homePageParserMessage = JSON.parseObject(messageStr,HomePageParserMessage.class);
                taskExecute.submit(new HomePageParserTask(homePageParserMessage));

                break;

            case MessageType.Cnblog.Homepage.CNBLOG_HOMEPAGE_SAVE :

                BloggerSaveMessage bloggerSaveMessage = JSON.parseObject(messageStr,BloggerSaveMessage.class);
                taskExecute.submit(new BloggerSaveTask(bloggerSaveMessage));

                break;

            case MessageType.Cnblog.Blogger.CNBLOG_BLOGGER_HOME_DOWNLOAD :

                BloggerHomeDownloadMessage bloggerHomeDownloadMessage = JSON.parseObject(messageStr,BloggerHomeDownloadMessage.class);
                taskExecute.submit(new BloggerHomeDownloadTask(bloggerHomeDownloadMessage));

                break;

            case MessageType.Cnblog.Blogger.CNBLOG_BLOGGER_HOME_PARSER :

                BloggerHomeParserMessage bloggerHomeParserMessage = JSON.parseObject(messageStr,BloggerHomeParserMessage.class);
                taskExecute.submit(new BloggerHomeParserTask(bloggerHomeParserMessage));

                break;
            case MessageType.Cnblog.Blogger.CNBLOG_BLOGGER_DOCUMENT_DOWNLOAD :

                BloggerDocumentDownloadMessage bloggerDocumentDownloadMessage = JSON.parseObject(messageStr,BloggerDocumentDownloadMessage.class);
                taskExecute.submit(new BloggerDocumentDownloadTask(bloggerDocumentDownloadMessage));

                break;

            case MessageType.Cnblog.Blogger.CNBLOG_BLOGGER_UID_PARSER :

                BloggerUidParserMessage bloggerUidParserMessage = JSON.parseObject(messageStr,BloggerUidParserMessage.class);
                taskExecute.submit(new BloggerUidParserTask(bloggerUidParserMessage));

                break;
            case MessageType.Cnblog.Blogger.CNBLOG_BLOGGER_UID_UPDATE :

                BloggerUidUpdateMessage bloggerUidUpdateMessage = JSON.parseObject(messageStr,BloggerUidUpdateMessage.class);
                taskExecute.submit(new BloggerUidUpdateTask(bloggerUidUpdateMessage));

                break;

            case MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_PAGE_DOWNLOAD:

                BloggerRelationDownloadMessage bloggerRelationDownloadMessage
                        = JSON.parseObject(messageStr,BloggerRelationDownloadMessage.class);
                taskExecute.submit(new BloggerRelationPageDownloadTask(bloggerRelationDownloadMessage));

                break;

            case MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_PAGE_PARSER:
                BloggerRelationPageParserMessage bloggerRelationPageParserMessage
                        = JSON.parseObject(messageStr,BloggerRelationPageParserMessage.class);
                taskExecute.submit(new BloggerRelationPageParserTask(bloggerRelationPageParserMessage));

                break;

            case MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_SAVE:
                BloggerRelationSaveMessage bloggerRelationSaveMessage
                        = JSON.parseObject(messageStr,BloggerRelationSaveMessage.class);
                taskExecute.submit(new BloggerRelationSaveTask(bloggerRelationSaveMessage));

                break;
        }
    }
}
