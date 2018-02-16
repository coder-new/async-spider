package com.farmer.async.spider.save.entity;

import javax.persistence.Table;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/20
 */
@Table(name = "spider_blogger_relation")
public class BloggerRelationEntity {

    private Integer id;

    private String bloggerFollower;

    private String bloggerFollowee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBloggerFollower() {
        return bloggerFollower;
    }

    public void setBloggerFollower(String bloggerFollower) {
        this.bloggerFollower = bloggerFollower;
    }

    public String getBloggerFollowee() {
        return bloggerFollowee;
    }

    public void setBloggerFollowee(String bloggerFollowee) {
        this.bloggerFollowee = bloggerFollowee;
    }
}
