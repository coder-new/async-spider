package com.farmer.async.spider.save.message;

import com.farmer.async.spider.message.definition.BaseMessage;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/20
 */
public class BloggerUidUpdateMessage extends BaseMessage{

    private String bloggerName;

    private String bloggerUid;

    public String getBloggerName() {
        return bloggerName;
    }

    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }

    public String getBloggerUid() {
        return bloggerUid;
    }

    public void setBloggerUid(String bloggerUid) {
        this.bloggerUid = bloggerUid;
    }
}
