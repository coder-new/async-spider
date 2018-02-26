package com.farmer.async.spider.save.service;

import com.farmer.async.spider.save.dao.BloggerDao;
import com.farmer.async.spider.save.entity.BloggerEntity;
import com.farmer.async.spider.save.message.BloggerSaveMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/25
 */
@Component
public class BloggerSaveService {

    @Autowired
    private BloggerDao bloggerDao;

    public void handle(BloggerSaveMessage bloggerSaveMessage) {

        List<BloggerEntity> bloggerEntities = bloggerSaveMessage.getBloggerEntities();
        if ((null == bloggerEntities) || bloggerEntities.size() == 0) {
            return;
        }
        bloggerDao.saveList(bloggerEntities);
    }
}
