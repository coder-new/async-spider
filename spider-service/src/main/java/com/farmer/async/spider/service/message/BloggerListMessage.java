package com.farmer.async.spider.service.message;

import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.save.entity.BloggerEntity;

import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/3/4
 */
public class BloggerListMessage extends BaseMessage {

    private List<BloggerEntity> bloggerEntities;

    public List<BloggerEntity> getBloggerEntities() {
        return bloggerEntities;
    }

    public void setBloggerEntities(List<BloggerEntity> bloggerEntities) {
        this.bloggerEntities = bloggerEntities;
    }
}
