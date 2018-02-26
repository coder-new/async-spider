package com.farmer.async.spider.controller.cnblogs;

import com.alibaba.fastjson.JSON;
import com.farmer.async.spider.message.Constants;
import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.request.message.BloggerHomeDownloadMessage;
import com.farmer.async.spider.request.message.BloggerRelationDownloadMessage;
import com.farmer.async.spider.save.dao.BloggerDao;
import com.farmer.async.spider.save.entity.BloggerEntity;
import com.farmer.async.spider.save.entity.QueueMessageEntity;
import com.farmer.async.spider.save.mapper.QueueMessageMapper;
import com.farmer.async.spider.service.BloggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/20
 */
@RestController
public class BloggerSpiderEndPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(BloggerSpiderEndPoint.class);

    @Autowired
    private BloggerDao bloggerDao;

    @Autowired
    private ActiveMqMessageSend activeMqMessageSend;

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private QueueMessageMapper queueMessageMapper;

    @RequestMapping(value = "/spider/cnblogs/blogger/uid/{size}",
            method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void spiderBloggerUid(@RequestHeader("X-Request-ID") String requestId,@PathVariable int size) {

        List<BloggerEntity> bloggerEntities = bloggerDao.queryUidNullBlogger();
        if (null != bloggerEntities && bloggerEntities.size() > 0) {

            int i = 0;

            for (BloggerEntity bloggerEntity : bloggerEntities) {
                BloggerHomeDownloadMessage bloggerHomeDownloadMessage = new BloggerHomeDownloadMessage();
                bloggerHomeDownloadMessage.setMessageId(UUID.randomUUID().toString());
                bloggerHomeDownloadMessage.setMessageType(MessageType.Cnblog.Blogger.CNBLOG_BLOGGER_HOME_DOWNLOAD);
                bloggerHomeDownloadMessage.setRequestId(requestId);
                bloggerHomeDownloadMessage.setBloggerName(bloggerEntity.getBloggerName());
                bloggerHomeDownloadMessage.setBloggerUrl(bloggerEntity.getBloggerUrl());

                activeMqMessageSend.send(bloggerHomeDownloadMessage);

                if (size == 0) {
                    continue;
                }

                i = i + 1;
                if (i == size) {
                    return;
                }
            }
        }
    }

    @RequestMapping(value = "/spider/cnblogs/blogger/relation",
            method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void spiderBloggerRelation() {

//        List<BloggerEntity> bloggerEntities = new ArrayList<>();
//        BloggerEntity entity = new BloggerEntity();
//        entity.setBloggerUid("");
//        entity.setBloggerName("liugh");
//        entity.setIsRelation(0);
//        entity.setFetchFolloweePage(0);
//        entity.setFetchFollowerPage(0);
//        entity.setMaxFolloweePage(-1);
//        entity.setMaxFollowerPage(-1);
//
//        bloggerEntities.add(entity);

        List<BloggerEntity> bloggerEntities = new ArrayList<>();
        BloggerEntity entity = bloggerDao.queryOneRelationZeroBlogger();
        if (null == entity) {
            LOGGER.info("***************no is relation zero!");
            return;
        }
        bloggerEntities.add(entity);
        //List<BloggerEntity> bloggerEntities = bloggerDao.queryIsRelationZeroBlogger();
        if (null != bloggerEntities && bloggerEntities.size() > 0) {

            for (int i=0;i<bloggerEntities.size();i++) {
                BloggerEntity bloggerEntity = bloggerEntities.get(i);
                bloggerService.sendDownLoadMessage(bloggerEntity);
            }

        }
    }


}
