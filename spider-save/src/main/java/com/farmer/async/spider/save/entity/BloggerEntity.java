package com.farmer.async.spider.save.entity;

import javax.persistence.Table;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/18
 */
@Table(name="spider_blogger")
public class BloggerEntity {

    private Integer id;

    private String bloggerName;

    private String bloggerUrl;

    private String bloggerUid;

    private Integer isRelation;

    private Integer maxFolloweePage;

    private Integer maxFollowerPage;

    private Integer fetchFolloweePage;

    private Integer fetchFollowerPage;

    @Override
    public String toString() {

        return "bloggerName : " + bloggerName + " bloggerUrl: " + bloggerUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBloggerName() {
        return bloggerName;
    }

    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }

    public String getBloggerUrl() {
        return bloggerUrl;
    }

    public void setBloggerUrl(String bloggerUrl) {
        this.bloggerUrl = bloggerUrl;
    }

    public String getBloggerUid() {
        return bloggerUid;
    }

    public void setBloggerUid(String bloggerUid) {
        this.bloggerUid = bloggerUid;
    }

    public Integer getMaxFolloweePage() {
        return maxFolloweePage;
    }

    public void setMaxFolloweePage(Integer maxFolloweePage) {
        this.maxFolloweePage = maxFolloweePage;
    }

    public Integer getMaxFollowerPage() {
        return maxFollowerPage;
    }

    public void setMaxFollowerPage(Integer maxFollowerPage) {
        this.maxFollowerPage = maxFollowerPage;
    }

    public Integer getFetchFolloweePage() {
        return fetchFolloweePage;
    }

    public void setFetchFolloweePage(Integer fetchFolloweePage) {
        this.fetchFolloweePage = fetchFolloweePage;
    }

    public Integer getFetchFollowerPage() {
        return fetchFollowerPage;
    }

    public void setFetchFollowerPage(Integer fetchFollowerPage) {
        this.fetchFollowerPage = fetchFollowerPage;
    }

    public Integer getIsRelation() {
        return isRelation;
    }

    public void setIsRelation(Integer isRelation) {
        this.isRelation = isRelation;
    }
}
