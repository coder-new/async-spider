package com.farmer.async.spider.save.message;

import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.save.entity.BloggerEntity;

import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/19
 */
public class BloggerSaveMessage extends BaseMessage{

    private List<BloggerEntity> bloggerEntities;

    public List<BloggerEntity> getBloggerEntities() {
        return bloggerEntities;
    }

    public void setBloggerEntities(List<BloggerEntity> bloggerEntities) {
        this.bloggerEntities = bloggerEntities;
    }
}
