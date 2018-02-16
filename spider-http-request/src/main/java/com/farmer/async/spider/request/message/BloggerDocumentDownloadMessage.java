package com.farmer.async.spider.request.message;

import com.farmer.async.spider.message.definition.BaseMessage;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/20
 */
public class BloggerDocumentDownloadMessage extends BaseMessage{

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
