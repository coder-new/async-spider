package com.farmer.async.spider.parser.manager;

import com.farmer.async.spider.message.Constants;
import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.parser.message.BloggerRelationPageParserMessage;
import com.farmer.async.spider.save.dao.BloggerDao;
import com.farmer.async.spider.save.entity.BloggerEntity;
import com.farmer.async.spider.save.entity.BloggerRelationEntity;
import com.farmer.async.spider.save.message.BloggerRelationSaveMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/23
 */
@Component
public class BloggerRelationPageParserService {

    @Autowired
    private ActiveMqMessageSend activeMqMessageSend;

    @Autowired
    private BloggerDao bloggerDao;

    public void handle(BloggerRelationPageParserMessage bloggerRelationPageParserMessage) {

        String body = bloggerRelationPageParserMessage.getBody();
        List<String> bloggerList = parserRelationBloggers(body);

        if (bloggerList.size() > 0) {
            List<BloggerRelationEntity> bloggerRelationEntities
                    = bloggerList.stream()
                    .map(bloggerName -> generateBloggerRelation(bloggerRelationPageParserMessage,bloggerName))
                    .collect(Collectors.toList());

            BloggerRelationSaveMessage bloggerRelationSaveMessage = new BloggerRelationSaveMessage();
            bloggerRelationSaveMessage.setMessageId(UUID.randomUUID().toString());
            bloggerRelationSaveMessage.setRequestId("");
            bloggerRelationSaveMessage.setMessageType(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_SAVE);
            bloggerRelationSaveMessage.setBloggerRelationEntities(bloggerRelationEntities);

            activeMqMessageSend.sendMessageWithPersistence(bloggerRelationSaveMessage, Constants.SAVE_QUEUE_NAME);

            sendBloggerMessage(bloggerList);
        }

        updateBlogger(bloggerRelationPageParserMessage,body,bloggerList);
    }

    private void sendBloggerMessage(List<String> bloggerList) {

        List<BloggerEntity> bloggerEntities = new ArrayList<>();

        for (String bloggerName : bloggerList) {

            BloggerEntity bloggerEntity = new BloggerEntity();
            bloggerEntity.setBloggerName(bloggerName);
            bloggerEntity.setBloggerUrl("");
            bloggerEntity.setIsRelation(0);
            bloggerEntity.setMaxFolloweePage(-1);
            bloggerEntity.setMaxFollowerPage(-1);
            bloggerEntity.setFetchFollowerPage(0);
            bloggerEntity.setFetchFolloweePage(0);

            bloggerEntities.add(bloggerEntity);
        }

        BloggerListMessage bloggerListMessage = new BloggerListMessage();
        bloggerListMessage.setBloggerEntities(bloggerEntities);
        bloggerListMessage.setMessageId(UUID.randomUUID().toString());
        bloggerListMessage.setMessageType(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_BLOGGER_LIST);
        bloggerListMessage.setRequestId(UUID.randomUUID().toString());

        activeMqMessageSend.sendMessageWithPersistence(bloggerListMessage,Constants.BLOGGER_QUEUE_NAME);
    }

    private class BloggerListMessage extends BaseMessage {

        private List<BloggerEntity> bloggerEntities;

        public List<BloggerEntity> getBloggerEntities() {
            return bloggerEntities;
        }

        public void setBloggerEntities(List<BloggerEntity> bloggerEntities) {
            this.bloggerEntities = bloggerEntities;
        }
    }

    private class BloggerMessage extends BaseMessage{

        private BloggerEntity bloggerEntity;

        public BloggerEntity getBloggerEntity() {
            return bloggerEntity;
        }

        public void setBloggerEntity(BloggerEntity bloggerEntity) {
            this.bloggerEntity = bloggerEntity;
        }
    }

    private void updateBlogger(BloggerRelationPageParserMessage bloggerRelationPageParserMessage,
                               String body,List<String> bloggerList) {

        String bloggerName = bloggerRelationPageParserMessage.getBloggerName();
        BloggerEntity entity = bloggerDao.queryByBloggerName(bloggerName);
        if (null == entity) {
            return;
        }
        int fetch = bloggerRelationPageParserMessage.getPageIndex();
        boolean follower = bloggerRelationPageParserMessage.getFollower();
        int maxFollower = entity.getMaxFollowerPage();
        int maxFollowee = entity.getMaxFolloweePage();
        int fetchFollower = entity.getFetchFollowerPage();
        int fetchFollowee = entity.getFetchFolloweePage();
        int id = entity.getId();
        if (fetch == 1) {

            int maxPage = parserMaxPage(body);
            int updateMax = -2;
            if (-1 == maxPage) {
                if (bloggerList.size() == 0) {
                    updateMax = -2;
                } else {
                    updateMax = 1;
                }
            } else if (1 == maxPage) {
                updateMax = 1;
            } else {
                updateMax = maxPage;
                for (int i=2;i<=maxPage;i++) {
                    sendRelationPageDownloadMessage(i,follower,bloggerRelationPageParserMessage.getBloggerName());
                }
            }

            if (follower) {
                BloggerEntity e = new BloggerEntity();
                e.setMaxFollowerPage(updateMax);
                if (fetchFollowee == maxFollowee) {
                    if ((updateMax == -2) || (updateMax == 1)) {
                        e.setIsRelation(1);
                    }
                }
                if (updateMax == -2) {
                    e.setFetchFollowerPage(-2);
                } else {
                    e.setFetchFollowerPage(1);
                }
                e.setBloggerName(bloggerName);
                bloggerDao.updateBloggerEntity(e,id);
            } else {
                BloggerEntity e = new BloggerEntity();
                e.setMaxFolloweePage(updateMax);
                if (fetchFollower == maxFollower) {
                    if ((updateMax == -2) || (updateMax == 1)) {
                        e.setIsRelation(1);
                    }
                }
                if (updateMax == -2) {
                    e.setFetchFolloweePage(-2);
                } else {
                    e.setFetchFolloweePage(1);
                }
                e.setBloggerName(bloggerName);
                bloggerDao.updateBloggerEntity(e,id);
            }

        } else {

            if (follower) {
                if (fetch == maxFollower) {
                    if (fetchFollowee == maxFollowee) {
                        BloggerEntity e = new BloggerEntity();
                        e.setIsRelation(1);
                        e.setFetchFollowerPage(fetch);
                        e.setBloggerName(bloggerName);
                        bloggerDao.updateBloggerEntity(e,id);
                    } else {
                        BloggerEntity e = new BloggerEntity();
                        e.setFetchFollowerPage(fetch);
                        e.setBloggerName(bloggerName);
                        bloggerDao.updateBloggerEntity(e,id);
                    }
                }
            } else {

                if (fetch == maxFollowee) {
                    if (fetchFollower == maxFollower) {
                        BloggerEntity e = new BloggerEntity();
                        e.setIsRelation(1);
                        e.setFetchFolloweePage(fetch);
                        e.setBloggerName(bloggerName);
                        bloggerDao.updateBloggerEntity(e,id);
                    } else {
                        BloggerEntity e = new BloggerEntity();
                        e.setFetchFolloweePage(fetch);
                        e.setBloggerName(bloggerName);
                        bloggerDao.updateBloggerEntity(e,id);
                    }
                }
            }
        }
    }

    private void sendRelationPageDownloadMessage(int pageIndex,boolean isFollower,String bloggerName) {

        BloggerRelationMessage bloggerRelationMessage = new BloggerRelationMessage();
        bloggerRelationMessage.setMessageId(UUID.randomUUID().toString());
        bloggerRelationMessage.setRequestId(UUID.randomUUID().toString());
        bloggerRelationMessage.setMessageType(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_PAGE_DOWNLOAD);
        String url = "https://home.cnblogs.com/u/" + bloggerName;
        if (isFollower) {
            url = url + "/followers?page=" + pageIndex;
        } else {
            url = url + "/followees?page=" + pageIndex;
        }

        bloggerRelationMessage.setBloggerRelationPageUrl(url);
        bloggerRelationMessage.setBloggerName(bloggerName);
        bloggerRelationMessage.setBloggerUid("");
        bloggerRelationMessage.setPageIndex(pageIndex);
        bloggerRelationMessage.setFollower(isFollower);

        activeMqMessageSend.sendMessageWithPersistence(bloggerRelationMessage,Constants.DOWNLOAD_QUEUE_NAME);
    }

    private class BloggerRelationMessage extends BaseMessage {

        private String bloggerRelationPageUrl;

        private String bloggerName;

        private String bloggerUid;

        private int pageIndex;

        private Boolean follower;

        public String getBloggerRelationPageUrl() {
            return bloggerRelationPageUrl;
        }

        public void setBloggerRelationPageUrl(String bloggerRelationPageUrl) {
            this.bloggerRelationPageUrl = bloggerRelationPageUrl;
        }

        public String getBloggerName() {
            return bloggerName;
        }

        public void setBloggerName(String bloggerName) {
            this.bloggerName = bloggerName;
        }

        public String getBloggerUid() {
            return bloggerUid;
        }

        public void setBloggerUid(String bloggerUid) {
            this.bloggerUid = bloggerUid;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public Boolean getFollower() {
            return follower;
        }

        public void setFollower(Boolean follower) {
            this.follower = follower;
        }
    }

    private int parserMaxPage(String body) {

        int lastIndex = body.indexOf("last\">");

        if (lastIndex != -1) {
            String str = body.substring(lastIndex + 6);
            int rightIndex = str.indexOf("<");
            return Integer.valueOf(str.substring(0,rightIndex));
        }

        return -1;
    }

    private BloggerRelationEntity generateBloggerRelation(BloggerRelationPageParserMessage bloggerRelationPageParserMessage,
                                                          String bloggerName) {

        BloggerRelationEntity bloggerRelationEntity = new BloggerRelationEntity();
        if (bloggerRelationPageParserMessage.getFollower()) {
            bloggerRelationEntity.setBloggerFollowee(bloggerRelationPageParserMessage.getBloggerName());
            bloggerRelationEntity.setBloggerFollower(bloggerName);
        } else {
            bloggerRelationEntity.setBloggerFollowee(bloggerName);
            bloggerRelationEntity.setBloggerFollower(bloggerRelationPageParserMessage.getBloggerName());
        }

        return bloggerRelationEntity;
    }

    private List<String> parserRelationBloggers(String body) {

        Document doc = Jsoup.parse(body);
        Elements bloggerClasses = doc.getElementsByClass("avatar_list");
        List<String> bloggers = new ArrayList<>();

        if (bloggerClasses.size() == 0) {
            return bloggers;
        }

        List<Node> nodes = bloggerClasses.get(0).childNodes();
        for (Node node : nodes) {
            if (node.childNodes().size() == 0) {
                continue;
            }
            String[] arr = node.childNode(1).attr("href").split("/");
            bloggers.add(arr[2]);
        }
        return bloggers;
    }
}
