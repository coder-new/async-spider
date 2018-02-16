package com.farmer.async.spider.parser.task;

import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.parser.message.BloggerHomeParserMessage;
import com.farmer.async.spider.util.context.ApplicationContextHolder;
import com.farmer.async.spider.util.threadlocal.RequestIdThreadLocal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/20
 */
public class BloggerHomeParserTask extends AbstractParserTask<String>{

    private static final Logger LOGGER = LoggerFactory.getLogger(BloggerHomeParserTask.class);

    private ActiveMqMessageSend activeMqMessageSend = ApplicationContextHolder.getBean(ActiveMqMessageSend.class);

    private BloggerHomeParserMessage bloggerHomeParserMessage;

    public BloggerHomeParserTask(BloggerHomeParserMessage bloggerHomeParserMessage) {

        this.bloggerHomeParserMessage = bloggerHomeParserMessage;
    }

    @Override
    protected void sendSaveMessage(String result) {

    }

    private void sendDownloadMessage(String documentUrl) {

        if (null == documentUrl) {
            LOGGER.info("blogger : {} document url is null",bloggerHomeParserMessage.getBloggerName());
            return;
        }

        BloggerDocumentDownloadMessage bloggerDocumentDownloadMessage = new BloggerDocumentDownloadMessage();
        bloggerDocumentDownloadMessage.setMessageId(UUID.randomUUID().toString());
        bloggerDocumentDownloadMessage.setMessageType(MessageType.Cnblog.Blogger.CNBLOG_BLOGGER_DOCUMENT_DOWNLOAD);
        bloggerDocumentDownloadMessage.setRequestId(RequestIdThreadLocal.getRequestId());
        bloggerDocumentDownloadMessage.setBloggerDocumentUrl(documentUrl);
        bloggerDocumentDownloadMessage.setBloggerName(bloggerHomeParserMessage.getBloggerName());

        activeMqMessageSend.send(bloggerDocumentDownloadMessage);
    }

    private class BloggerDocumentDownloadMessage extends BaseMessage{

        private String bloggerName;

        private String bloggerDocumentUrl;

        public String getBloggerName() {
            return bloggerName;
        }

        public void setBloggerName(String bloggerName) {
            this.bloggerName = bloggerName;
        }

        public String getBloggerDocumentUrl() {
            return bloggerDocumentUrl;
        }

        public void setBloggerDocumentUrl(String bloggerDocumentUrl) {
            this.bloggerDocumentUrl = bloggerDocumentUrl;
        }
    }

    @Override
    public String call() throws Exception {

        Document doc = Jsoup.parse(bloggerHomeParserMessage.getBody());

        String bloggerDocumentUrl = null;

        if (null != doc) {
            Elements pageClasses = doc.getElementsByClass("postTitle");
            int size = pageClasses.size();
            if (size == 0) {
                pageClasses = doc.getElementsByClass("posthead");

                if (pageClasses.size() == 0) {

                    pageClasses = doc.getElementsByClass("post");

                    if (pageClasses.size() == 0) {
                        bloggerDocumentUrl =  null;
                    } else {

                        bloggerDocumentUrl = pageClasses.get(0).childNode(1).childNode(0).attributes().get("href");
                    }

                } else {

                    bloggerDocumentUrl = pageClasses.get(0).childNode(1).childNode(1).attributes().get("href");
                }

            } else {

                if (pageClasses.get(0).childNodes().size() == 1) {

                    bloggerDocumentUrl = pageClasses.get(0).childNode(0).attributes().get("href");

                    if ("".equals(bloggerDocumentUrl)) {
                        bloggerDocumentUrl = pageClasses.get(0).attributes().get("href");
                    }

                } else {

                    bloggerDocumentUrl = pageClasses.get(0).childNode(1).attributes().get("href");
                }
            }

        }


        sendDownloadMessage(bloggerDocumentUrl);

        return "";
    }
}
