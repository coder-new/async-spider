package com.farmer.async.spider.parser.task;

import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.parser.message.HomePageParserMessage;
import com.farmer.async.spider.save.entity.BloggerEntity;
import com.farmer.async.spider.save.message.BloggerSaveMessage;
import com.farmer.async.spider.util.context.ApplicationContextHolder;
import com.farmer.async.spider.util.threadlocal.RequestIdThreadLocal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/18
 */
public class HomePageParserTask extends AbstractParserTask<List<BloggerEntity>>{


    private static final Logger LOGGER = LoggerFactory.getLogger(HomePageParserTask.class);

    private ActiveMqMessageSend activeMqMessageSend = ApplicationContextHolder.getBean(ActiveMqMessageSend.class);

    private HomePageParserMessage homePageParserMessage;

    public HomePageParserTask(HomePageParserMessage homePageParserMessage) {

        this.homePageParserMessage = homePageParserMessage;
    }

    @Override
    public List<BloggerEntity> call() throws Exception {

        Document doc = Jsoup.parse(homePageParserMessage.getPageBody());

        List<BloggerEntity> bloggerEntities = new ArrayList<>();

        if (null != doc) {

            Elements bloggerClasses = doc.getElementsByClass("post_item_body");
            int size = bloggerClasses.size();

            for (int i = 0;i < size;i++) {

                Element bloggerClass = bloggerClasses.get(i);

                String documentUrl = bloggerClass.childNodes().get(1).childNode(0).attr("href");
                String bloggerUrl = bloggerClass.childNodes().get(5).childNode(1).attr("href");

                String[] strs = bloggerUrl.split("/");
                String bloggerName = strs[strs.length - 1];

                LOGGER.info("document url : {}",documentUrl);
                LOGGER.info("blogger name : {}",bloggerName);

                BloggerEntity bloggerEntity = new BloggerEntity();
                bloggerEntity.setBloggerName(bloggerName);
                bloggerEntity.setBloggerUrl(bloggerUrl);

                bloggerEntities.add(bloggerEntity);
            }
        }

        return bloggerEntities;
    }

    @Override
    protected void sendSaveMessage(List<BloggerEntity> bloggerEntities) {

        if (bloggerEntities.size() > 0) {
            BloggerSaveMessage bloggerSaveMessage = new BloggerSaveMessage();
            bloggerSaveMessage.setMessageType(MessageType.Cnblog.Homepage.CNBLOG_HOMEPAGE_SAVE);
            bloggerSaveMessage.setBloggerEntities(bloggerEntities);
            bloggerSaveMessage.setMessageId(UUID.randomUUID().toString());
            bloggerSaveMessage.setRequestId(RequestIdThreadLocal.getRequestId());

            activeMqMessageSend.send(bloggerSaveMessage);
        }
    }
}
