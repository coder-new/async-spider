package com.farmer.async.spider.service;

import com.farmer.async.spider.message.Constants;
import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.request.message.BloggerRelationDownloadMessage;
import com.farmer.async.spider.save.dao.BloggerDao;
import com.farmer.async.spider.save.entity.BloggerEntity;
import com.farmer.async.spider.service.message.BloggerListMessage;
import com.farmer.async.spider.service.message.BloggerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/25
 */
@Component
public class BloggerService {

    @Autowired
    private BloggerDao bloggerDao;

    @Autowired
    private ActiveMqMessageSend activeMqMessageSend;

    public void handle(BloggerMessage bloggerMessage) {

        BloggerEntity bloggerEntity = bloggerMessage.getBloggerEntity();
        bloggerDao.save(bloggerEntity);

        sendDownLoadMessage(bloggerEntity);
    }

    public void handleList(BloggerListMessage bloggerListMessage) {

        List<BloggerEntity> bloggerEntities = bloggerListMessage.getBloggerEntities();
        for (BloggerEntity bloggerEntity : bloggerEntities) {
            bloggerDao.save(bloggerEntity);
            sendDownLoadMessage(bloggerEntity);
        }
    }

    public void sendDownLoadMessage(BloggerEntity bloggerEntity) {

        Integer isRelation = bloggerEntity.getIsRelation();
        if (isRelation != 0) {
            return;
        }
        BloggerRelationDownloadMessage followerMessage = generateFollowerMessage(bloggerEntity.getBloggerName(),
                bloggerEntity.getBloggerUid(),bloggerEntity.getMaxFollowerPage(),bloggerEntity.getFetchFollowerPage());
        if (null != followerMessage) {
            activeMqMessageSend.sendMessageWithPersistence(followerMessage, Constants.DOWNLOAD_QUEUE_NAME);
        }

        BloggerRelationDownloadMessage followeeMessage = generateFolloweeMessage(bloggerEntity.getBloggerName(),
                bloggerEntity.getBloggerUid(),bloggerEntity.getMaxFolloweePage(),bloggerEntity.getFetchFolloweePage());
        if (null != followerMessage) {
            activeMqMessageSend.sendMessageWithPersistence(followeeMessage,Constants.DOWNLOAD_QUEUE_NAME);
        }
    }


    private BloggerRelationDownloadMessage generateFollowerMessage(String bloggerName, String bloggerUid,int max,int fetch) {

        String url = getRelationUrl(true,bloggerName,max,fetch);
        if (null == url) {
            return null;
        }

        BloggerRelationDownloadMessage bloggerRelationDownloadMessage = new BloggerRelationDownloadMessage();
        bloggerRelationDownloadMessage.setMessageId(UUID.randomUUID().toString());
        bloggerRelationDownloadMessage.setRequestId(UUID.randomUUID().toString());
        bloggerRelationDownloadMessage.setMessageType(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_PAGE_DOWNLOAD);
        bloggerRelationDownloadMessage.setBloggerRelationPageUrl(url);
        bloggerRelationDownloadMessage.setBloggerName(bloggerName);
        bloggerRelationDownloadMessage.setBloggerUid(bloggerUid);
        bloggerRelationDownloadMessage.setPageIndex(fetch + 1);
        bloggerRelationDownloadMessage.setFollower(true);

        return bloggerRelationDownloadMessage;
    }

    private BloggerRelationDownloadMessage generateFolloweeMessage(String bloggerName, String bloggerUid,int max,int fetch) {

        String url = getRelationUrl(false,bloggerName,max,fetch);
        if (null == url) {
            return null;
        }

        BloggerRelationDownloadMessage bloggerRelationDownloadMessage = new BloggerRelationDownloadMessage();
        bloggerRelationDownloadMessage.setMessageId(UUID.randomUUID().toString());
        bloggerRelationDownloadMessage.setRequestId(UUID.randomUUID().toString());
        bloggerRelationDownloadMessage.setMessageType(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_PAGE_DOWNLOAD);
        bloggerRelationDownloadMessage.setBloggerRelationPageUrl(url);
        bloggerRelationDownloadMessage.setBloggerName(bloggerName);
        bloggerRelationDownloadMessage.setBloggerUid(bloggerUid);
        bloggerRelationDownloadMessage.setPageIndex(fetch + 1);
        bloggerRelationDownloadMessage.setFollower(false);

        return bloggerRelationDownloadMessage;
    }

    private String getRelationUrl(boolean isFollower,String bloggerName,int max,int fetch) {

        if (max == -2) {
            return null;
        }

        if ((max != 0) && (fetch == max)) {
            return null;
        }

        if (max == 0) {
            return null;
        }

        String url = "https://home.cnblogs.com/u/" + bloggerName;
        if (isFollower) {
            url = url + "/followers?page=" + (fetch + 1);
        } else {
            url = url + "/followees?page=" + (fetch + 1);
        }

        return url;
    }
}
