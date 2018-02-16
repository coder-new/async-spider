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
//        bloggerEntities.add(entity);

        List<BloggerEntity> bloggerEntities = bloggerDao.queryUidNotNullBlogger();
        if (null != bloggerEntities && bloggerEntities.size() > 0) {

            bloggerEntities.stream()
                    .map(bloggerEntity -> generateFollowerMessage(bloggerEntity.getBloggerName(),bloggerEntity.getBloggerUid()))
                    .forEach(bloggerRelationDownloadMessage -> {

                        QueueMessageEntity queueMessageEntity = new QueueMessageEntity();
                        queueMessageEntity.setQueueName(Constants.DOWNLOAD_QUEUE_NAME);
                        queueMessageEntity.setMessageStr(JSON.toJSONString(bloggerRelationDownloadMessage));
                        queueMessageMapper.insert(queueMessageEntity);

                        activeMqMessageSend.sendMessage(bloggerRelationDownloadMessage,Constants.DOWNLOAD_QUEUE_NAME);
                    });

            bloggerEntities.stream()
                    .map(bloggerEntity -> generateFolloweeMessage(bloggerEntity.getBloggerName(),bloggerEntity.getBloggerUid()))
                    .forEach(bloggerRelationDownloadMessage -> {
                        QueueMessageEntity queueMessageEntity = new QueueMessageEntity();
                        queueMessageEntity.setQueueName(Constants.DOWNLOAD_QUEUE_NAME);
                        queueMessageEntity.setMessageStr(JSON.toJSONString(bloggerRelationDownloadMessage));
                        queueMessageMapper.insert(queueMessageEntity);
                        activeMqMessageSend.sendMessage(bloggerRelationDownloadMessage,Constants.DOWNLOAD_QUEUE_NAME);
                    });
        }
    }

    private BloggerRelationDownloadMessage generateFollowerMessage(String bloggerName, String bloggerUid) {

        BloggerRelationDownloadMessage bloggerRelationDownloadMessage = new BloggerRelationDownloadMessage();
        bloggerRelationDownloadMessage.setMessageId(UUID.randomUUID().toString());
        bloggerRelationDownloadMessage.setRequestId(UUID.randomUUID().toString());
        bloggerRelationDownloadMessage.setMessageType(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_PAGE_DOWNLOAD);
        bloggerRelationDownloadMessage.setBloggerRelationPageUrl("https://home.cnblogs.com/u/" + bloggerName + "/followers");
        bloggerRelationDownloadMessage.setBloggerName(bloggerName);
        bloggerRelationDownloadMessage.setBloggerUid(bloggerUid);
        bloggerRelationDownloadMessage.setPageIndex(1);
        bloggerRelationDownloadMessage.setFollower(true);

        return bloggerRelationDownloadMessage;
    }

    private BloggerRelationDownloadMessage generateFolloweeMessage(String bloggerName, String bloggerUid) {

        BloggerRelationDownloadMessage bloggerRelationDownloadMessage = new BloggerRelationDownloadMessage();
        bloggerRelationDownloadMessage.setMessageId(UUID.randomUUID().toString());
        bloggerRelationDownloadMessage.setRequestId(UUID.randomUUID().toString());
        bloggerRelationDownloadMessage.setMessageType(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_PAGE_DOWNLOAD);
        bloggerRelationDownloadMessage.setBloggerRelationPageUrl("https://home.cnblogs.com/u/" + bloggerName + "/followees");
        bloggerRelationDownloadMessage.setBloggerName(bloggerName);
        bloggerRelationDownloadMessage.setBloggerUid(bloggerUid);
        bloggerRelationDownloadMessage.setPageIndex(1);
        bloggerRelationDownloadMessage.setFollower(false);

        return bloggerRelationDownloadMessage;
    }
}
