package com.farmer.async.spider.parser.message;

import com.farmer.async.spider.message.definition.BaseMessage;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/28
 */
public class BloggerFollowerParserMessage extends BaseMessage{

    private String pageBody;

    private String bloggerName;

    private String bloggerUid;

    public String getPageBody() {
        return pageBody;
    }

    public void setPageBody(String pageBody) {
        this.pageBody = pageBody;
    }

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
