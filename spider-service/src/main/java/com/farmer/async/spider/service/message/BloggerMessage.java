package com.farmer.async.spider.service.message;

import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.save.entity.BloggerEntity;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/25
 */
public class BloggerMessage extends BaseMessage{

    private BloggerEntity bloggerEntity;

    public BloggerEntity getBloggerEntity() {
        return bloggerEntity;
    }

    public void setBloggerEntity(BloggerEntity bloggerEntity) {
        this.bloggerEntity = bloggerEntity;
    }
}
