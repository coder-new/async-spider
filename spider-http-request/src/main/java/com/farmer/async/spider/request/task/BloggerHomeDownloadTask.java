package com.farmer.async.spider.request.task;

import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.parser.message.BloggerHomeParserMessage;
import com.farmer.async.spider.request.RestTemplateHttpClient;
import com.farmer.async.spider.request.message.BloggerHomeDownloadMessage;
import com.farmer.async.spider.util.context.ApplicationContextHolder;
import com.farmer.async.spider.util.threadlocal.RequestIdThreadLocal;

import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/20
 */
public class BloggerHomeDownloadTask extends AbstractHttpTask<String>{



    private RestTemplateHttpClient restTemplateHttpClient = ApplicationContextHolder.getBean(RestTemplateHttpClient.class);

    private ActiveMqMessageSend activeMqMessageSend = ApplicationContextHolder.getBean(ActiveMqMessageSend.class);

    private BloggerHomeDownloadMessage bloggerHomeDownloadMessage;

    public BloggerHomeDownloadTask(BloggerHomeDownloadMessage bloggerHomeDownloadMessage) {

        this.bloggerHomeDownloadMessage = bloggerHomeDownloadMessage;
    }

    @Override
    protected void sendParserMessage(String result) {

        if (null == result) {
            return;
        }

        BloggerHomeParserMessage bloggerHomeParserMessage = new BloggerHomeParserMessage();
        bloggerHomeParserMessage.setBloggerName(bloggerHomeDownloadMessage.getBloggerName());
        bloggerHomeParserMessage.setBody(result);
        bloggerHomeParserMessage.setMessageId(UUID.randomUUID().toString());
        bloggerHomeParserMessage.setMessageType(MessageType.Cnblog.Blogger.CNBLOG_BLOGGER_HOME_PARSER);
        bloggerHomeParserMessage.setRequestId(RequestIdThreadLocal.getRequestId());

        activeMqMessageSend.send(bloggerHomeParserMessage);
    }

    @Override
    protected void sendStorageMessage() {

    }

    @Override
    protected void sendSaveMessage() {

    }

    @Override
    public String call() throws Exception {

        String body = restTemplateHttpClient.get(bloggerHomeDownloadMessage.getBloggerUrl());

        return body;
    }
}
