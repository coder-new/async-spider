package com.farmer.async.spider.save.task;

import com.farmer.async.spider.save.dao.BloggerDao;
import com.farmer.async.spider.save.entity.BloggerEntity;
import com.farmer.async.spider.save.message.BloggerSaveMessage;
import com.farmer.async.spider.util.context.ApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/19
 */
public class BloggerSaveTask extends AbstractSaveTask<String>{

    private static final Logger LOGGER = LoggerFactory.getLogger(BloggerSaveTask.class);

    private BloggerDao bloggerDao = ApplicationContextHolder.getBean(BloggerDao.class);

    private BloggerSaveMessage bloggerSaveMessage;

    public BloggerSaveTask(BloggerSaveMessage bloggerSaveMessage) {

        this.bloggerSaveMessage = bloggerSaveMessage;
    }

    @Override
    public String call() throws Exception {

        List<BloggerEntity> bloggerEntities = bloggerSaveMessage.getBloggerEntities();

        if (null == bloggerEntities) {
            LOGGER.info("bloggerSaveMessage : {}",bloggerSaveMessage);
            return "";
        }

        try {
            bloggerDao.saveList(bloggerEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }
}
