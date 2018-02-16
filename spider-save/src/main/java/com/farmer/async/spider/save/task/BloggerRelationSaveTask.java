package com.farmer.async.spider.save.task;

import com.farmer.async.spider.save.dao.BloggerRelationDao;
import com.farmer.async.spider.save.entity.BloggerRelationEntity;
import com.farmer.async.spider.save.message.BloggerRelationSaveMessage;
import com.farmer.async.spider.util.context.ApplicationContextHolder;

import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/4
 */
public class BloggerRelationSaveTask extends AbstractSaveTask<String>{


    private BloggerRelationSaveMessage bloggerRelationSaveMessage;

    private BloggerRelationDao bloggerRelationDao = ApplicationContextHolder.getBean(BloggerRelationDao.class);

    public BloggerRelationSaveTask(BloggerRelationSaveMessage bloggerRelationSaveMessage) {

        this.bloggerRelationSaveMessage = bloggerRelationSaveMessage;
    }

    @Override
    public String call() throws Exception {

        List<BloggerRelationEntity> bloggerRelationEntities
                = bloggerRelationSaveMessage.getBloggerRelationEntities();
        if (null == bloggerRelationEntities) {
            return "";
        }

        bloggerRelationDao.saveList(bloggerRelationEntities);

        return "";
    }
}
