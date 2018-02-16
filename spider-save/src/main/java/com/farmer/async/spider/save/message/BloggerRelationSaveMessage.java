package com.farmer.async.spider.save.message;

import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.save.entity.BloggerRelationEntity;

import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/4
 */
public class BloggerRelationSaveMessage extends BaseMessage{

    private List<BloggerRelationEntity> bloggerRelationEntities;

    public List<BloggerRelationEntity> getBloggerRelationEntities() {
        return bloggerRelationEntities;
    }

    public void setBloggerRelationEntities(List<BloggerRelationEntity> bloggerRelationEntities) {
        this.bloggerRelationEntities = bloggerRelationEntities;
    }
}
