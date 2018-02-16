package com.farmer.async.spider.parser.message;

import com.farmer.async.spider.message.definition.BaseMessage;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/20
 */
public class BloggerHomeParserMessage extends BaseMessage{

    private String bloggerName;

    private String body;

    public String getBloggerName() {
        return bloggerName;
    }

    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
