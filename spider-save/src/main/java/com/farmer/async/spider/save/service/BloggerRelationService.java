package com.farmer.async.spider.save.service;

import com.farmer.async.spider.save.dao.BloggerRelationDao;
import com.farmer.async.spider.save.entity.BloggerRelationEntity;
import com.farmer.async.spider.save.message.BloggerRelationSaveMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/25
 */
@Component
public class BloggerRelationService {

    @Autowired
    private BloggerRelationDao bloggerRelationDao;

    public void handle(BloggerRelationSaveMessage bloggerRelationSaveMessage) {

        List<BloggerRelationEntity> relationEntities = bloggerRelationSaveMessage.getBloggerRelationEntities();

        if ((null == relationEntities) || (relationEntities.size() == 0)) {
            return;
        }

        bloggerRelationDao.saveList(relationEntities);
    }
}
