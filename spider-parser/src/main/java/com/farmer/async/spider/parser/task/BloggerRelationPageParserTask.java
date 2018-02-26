package com.farmer.async.spider.parser.task;

import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.parser.message.BloggerRelationPageParserMessage;
import com.farmer.async.spider.save.entity.BloggerRelationEntity;
import com.farmer.async.spider.save.message.BloggerRelationSaveMessage;
import com.farmer.async.spider.util.context.ApplicationContextHolder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/3
 */
public class BloggerRelationPageParserTask extends AbstractParserTask<List<String>>{


    private ActiveMqMessageSend activeMqMessageSend = ApplicationContextHolder.getBean(ActiveMqMessageSend.class);

    private BloggerRelationPageParserMessage bloggerRelationPageParserMessage;

    public BloggerRelationPageParserTask(BloggerRelationPageParserMessage bloggerRelationPageParserMessage) {

        this.bloggerRelationPageParserMessage = bloggerRelationPageParserMessage;
    }

    @Override
    protected void sendSaveMessage(List<String> result) {

        List<BloggerRelationEntity> bloggerRelationEntities
                = result.stream().map(this::generateBloggerRelation).collect(Collectors.toList());

        BloggerRelationSaveMessage bloggerRelationSaveMessage = new BloggerRelationSaveMessage();
        bloggerRelationSaveMessage.setMessageId(UUID.randomUUID().toString());
        bloggerRelationSaveMessage.setRequestId("");
        bloggerRelationSaveMessage.setMessageType(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_SAVE);
        bloggerRelationSaveMessage.setBloggerRelationEntities(bloggerRelationEntities);

        activeMqMessageSend.send(bloggerRelationSaveMessage);
    }

    private BloggerRelationEntity generateBloggerRelation(String bloggerName) {

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

    @Override
    public List<String> call() throws Exception {

        String body = bloggerRelationPageParserMessage.getBody();
        List<String> bloggerList = parserRelationBloggers(body);

        if (bloggerRelationPageParserMessage.getPageIndex() == 1) {

            int lastIndex = body.indexOf("last\">");

            if (lastIndex != -1) {
                String str = body.substring(lastIndex + 7);
                int rightIndex = str.indexOf(",");
                int pageCnt = Integer.valueOf(str.substring(0,rightIndex));
                if (pageCnt > 1) {
                    for (int i=1;i<pageCnt;i++) {
                        sendRelationPageDownloadMessage(i + 1);
                    }

                }
            }

        }

        return bloggerList;
    }

    private void sendRelationPageDownloadMessage(int pageIndex) {

        BloggerRelationMessage bloggerRelationMessage = new BloggerRelationMessage();
        bloggerRelationMessage.setMessageId(UUID.randomUUID().toString());
        bloggerRelationMessage.setRequestId(UUID.randomUUID().toString());
        bloggerRelationMessage.setMessageType(MessageType.Cnblog.BloggerRelation.CNBLOG_BLOGGER_RELATION_PAGE_DOWNLOAD);
        String url = "https://home.cnblogs.com/u/" + bloggerRelationPageParserMessage.getBloggerName();
        if (bloggerRelationPageParserMessage.getFollower()) {
            url = url + "/followers?page=" + pageIndex;
        } else {
            url = url + "/followees?page=" + pageIndex;
        }

        bloggerRelationMessage.setBloggerRelationPageUrl(url);
        bloggerRelationMessage.setBloggerName(bloggerRelationPageParserMessage.getBloggerName());
        bloggerRelationMessage.setBloggerUid(bloggerRelationPageParserMessage.getBloggerUid());
        bloggerRelationMessage.setPageIndex(pageIndex);
        bloggerRelationMessage.setFollower(bloggerRelationPageParserMessage.getFollower());

        activeMqMessageSend.send(bloggerRelationMessage);
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


    private List<String> parserRelationBloggers(String body) {

        Document doc = Jsoup.parse(body);
        Elements bloggerClasses = doc.getElementsByClass("avatar_list");

        List<String> bloggers = new ArrayList<>();
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
