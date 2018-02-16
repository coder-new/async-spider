package com.farmer.async.spider.save.task;

import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.save.dao.BloggerDao;
import com.farmer.async.spider.save.message.BloggerUidUpdateMessage;
import com.farmer.async.spider.util.context.ApplicationContextHolder;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/20
 */
public class BloggerUidUpdateTask extends AbstractSaveTask<String>{


    private BloggerDao bloggerDao = ApplicationContextHolder.getBean(BloggerDao.class);

    private BloggerUidUpdateMessage bloggerUidUpdateMessage;

    public BloggerUidUpdateTask(BloggerUidUpdateMessage bloggerUidUpdateMessage) {

        this.bloggerUidUpdateMessage = bloggerUidUpdateMessage;
    }

    @Override
    public String call() throws Exception {

        bloggerDao.updateBloggerUid(bloggerUidUpdateMessage.getBloggerName(),bloggerUidUpdateMessage.getBloggerUid());

        return "";
    }
}
