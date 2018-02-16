package com.farmer.async.spider.request.message;

import com.farmer.async.spider.message.definition.BaseMessage;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/28
 */
public class BloggerRelationDownloadMessage extends BaseMessage{

    private String bloggerRelationPageUrl;

    private String bloggerName;

    private String bloggerUid;

    private int pageIndex;

    private Boolean follower;

    public String getBloggerRelationPageUrl() {
        return bloggerRelationPageUrl;
    }

    public void setBloggerRelationPageUrl(String bloggerRelationPageUrl) {
        this.bloggerRelationPageUrl = bloggerRelationPageUrl;
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

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Boolean getFollower() {
        return follower;
    }

    public void setFollower(Boolean follower) {
        this.follower = follower;
    }
}
