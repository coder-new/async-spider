package com.farmer.async.spider.request.message;

import com.farmer.async.spider.message.definition.BaseMessage;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/20
 */
public class BloggerHomeDownloadMessage extends BaseMessage{

    private String bloggerUrl;

    private String bloggerName;

    public String getBloggerUrl() {
        return bloggerUrl;
    }

    public void setBloggerUrl(String bloggerUrl) {
        this.bloggerUrl = bloggerUrl;
    }

    public String getBloggerName() {
        return bloggerName;
    }

    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }
}
