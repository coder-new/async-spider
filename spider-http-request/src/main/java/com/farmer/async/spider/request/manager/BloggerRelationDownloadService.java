package com.farmer.async.spider.request.manager;

import com.farmer.async.spider.message.Constants;
import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.parser.message.BloggerRelationPageParserMessage;
import com.farmer.async.spider.request.RestTemplateHttpClient;
import com.farmer.async.spider.request.message.BloggerRelationDownloadMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/23
 */
@Component
public class BloggerRelationDownloadService {

    @Autowired
    private RestTemplateHttpClient restTemplateHttpClient;

    @Autowired
    private ActiveMqMessageSend activeMqMessageSend;

    public void handle(BloggerRelationDownloadMessage bloggerRelationDownloadMessage) {

        String body = restTemplateHttpClient.get(bloggerRelationDownloadMessage.getBloggerRelationPageUrl());

        BloggerRelationPageParserMessage bloggerRelationPageParserMessage
                = new BloggerRelationPageParserMessage();
        bloggerRelationPageParserMessage
                .setMessageType(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_PAGE_PARSER);
        bloggerRelationPageParserMessage.setMessageId(UUID.randomUUID().toString());
        bloggerRelationPageParserMessage.setRequestId("");
        bloggerRelationPageParserMessage.setBloggerName(bloggerRelationDownloadMessage.getBloggerName());
        bloggerRelationPageParserMessage.setBloggerUid(bloggerRelationDownloadMessage.getBloggerUid());
        bloggerRelationPageParserMessage.setBody(body);
        bloggerRelationPageParserMessage.setPageIndex(bloggerRelationDownloadMessage.getPageIndex());
        bloggerRelationPageParserMessage.setFollower(bloggerRelationDownloadMessage.getFollower());

        activeMqMessageSend.sendMessageWithPersistence(bloggerRelationPageParserMessage, Constants.PARSER_QUEUE_NAME);
    }
}
