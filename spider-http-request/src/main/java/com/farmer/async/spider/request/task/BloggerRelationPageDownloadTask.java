package com.farmer.async.spider.request.task;

import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.parser.message.BloggerRelationPageParserMessage;
import com.farmer.async.spider.request.RestTemplateHttpClient;
import com.farmer.async.spider.request.message.BloggerRelationDownloadMessage;
import com.farmer.async.spider.util.context.ApplicationContextHolder;

import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/3
 */
public class BloggerRelationPageDownloadTask extends AbstractHttpTask<String>{

    private RestTemplateHttpClient restTemplateHttpClient = ApplicationContextHolder.getBean(RestTemplateHttpClient.class);

    private ActiveMqMessageSend activeMqMessageSend = ApplicationContextHolder.getBean(ActiveMqMessageSend.class);

    private BloggerRelationDownloadMessage bloggerRelationDownloadMessage;

    public BloggerRelationPageDownloadTask(BloggerRelationDownloadMessage bloggerRelationDownloadMessage) {

        this.bloggerRelationDownloadMessage = bloggerRelationDownloadMessage;
    }

    @Override
    protected void sendParserMessage(String result) {

        BloggerRelationPageParserMessage bloggerRelationPageParserMessage
                = new BloggerRelationPageParserMessage();
        bloggerRelationPageParserMessage
                .setMessageType(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_PAGE_PARSER);
        bloggerRelationPageParserMessage.setMessageId(UUID.randomUUID().toString());
        bloggerRelationPageParserMessage.setRequestId("");
        bloggerRelationPageParserMessage.setBloggerName(bloggerRelationDownloadMessage.getBloggerName());
        bloggerRelationPageParserMessage.setBloggerUid(bloggerRelationDownloadMessage.getBloggerUid());
        bloggerRelationPageParserMessage.setBody(result);
        bloggerRelationPageParserMessage.setPageIndex(bloggerRelationDownloadMessage.getPageIndex());
        bloggerRelationPageParserMessage.setFollower(bloggerRelationDownloadMessage.getFollower());

        activeMqMessageSend.send(bloggerRelationPageParserMessage);
    }

    @Override
    protected void sendStorageMessage() {

    }

    @Override
    protected void sendSaveMessage() {

    }

    @Override
    public String call() throws Exception {

        String body = restTemplateHttpClient.get(bloggerRelationDownloadMessage.getBloggerRelationPageUrl());

        return body;
    }
}
