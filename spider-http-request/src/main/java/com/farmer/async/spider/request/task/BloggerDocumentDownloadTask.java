package com.farmer.async.spider.request.task;

import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.parser.message.BloggerUidParserMessage;
import com.farmer.async.spider.request.RestTemplateHttpClient;
import com.farmer.async.spider.request.message.BloggerDocumentDownloadMessage;
import com.farmer.async.spider.util.context.ApplicationContextHolder;

import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/20
 */
public class BloggerDocumentDownloadTask extends AbstractHttpTask<String>{

    private BloggerDocumentDownloadMessage bloggerDocumentDownloadMessage;

    private RestTemplateHttpClient restTemplateHttpClient = ApplicationContextHolder.getBean(RestTemplateHttpClient.class);

    private ActiveMqMessageSend activeMqMessageSend = ApplicationContextHolder.getBean(ActiveMqMessageSend.class);

    public BloggerDocumentDownloadTask(BloggerDocumentDownloadMessage bloggerDocumentDownloadMessage) {

        this.bloggerDocumentDownloadMessage = bloggerDocumentDownloadMessage;
    }

    @Override
    protected void sendParserMessage(String result) {

        if (null == result) {
            return;
        }

        BloggerUidParserMessage bloggerUidParserMessage = new BloggerUidParserMessage();
        bloggerUidParserMessage.setMessageId(UUID.randomUUID().toString());
        bloggerUidParserMessage.setMessageType(MessageType.Cnblog.Blogger.CNBLOG_BLOGGER_UID_PARSER);
        bloggerUidParserMessage.setRequestId("");
        bloggerUidParserMessage.setBloggerName(bloggerDocumentDownloadMessage.getBloggerName());
        bloggerUidParserMessage.setDocumentBody(result);

        activeMqMessageSend.send(bloggerUidParserMessage);
    }

    @Override
    protected void sendStorageMessage() {

    }

    @Override
    protected void sendSaveMessage() {

    }

    @Override
    public String call() throws Exception {

        String body = restTemplateHttpClient.get(bloggerDocumentDownloadMessage.getBloggerDocumentUrl());

        return body;
    }
}
