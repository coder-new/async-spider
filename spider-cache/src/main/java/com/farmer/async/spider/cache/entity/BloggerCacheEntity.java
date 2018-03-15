package com.farmer.async.spider.cache.entity;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/3/3
 */
public class BloggerCacheEntity {

    private Integer id;

    private String bloggerName;

    private Integer isRelation;

    private Integer maxFolloweePage;

    private Integer maxFollowerPage;

    private Integer fetchFolloweePage;

    private Integer fetchFollowerPage;

    @Override
    public String toString() {

        return "id : " + id + " bloggerName : " + bloggerName
                + " isRelation : " + isRelation + " maxFolloweePage : " + maxFolloweePage
                + " maxFollowerPage : " + maxFollowerPage + " fetchFolloweePage : " + fetchFolloweePage
                + " fetchFollowerPage : " + fetchFollowerPage;
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

    public Integer getIsRelation() {
        return isRelation;
    }

    public void setIsRelation(Integer isRelation) {
        this.isRelation = isRelation;
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
}
