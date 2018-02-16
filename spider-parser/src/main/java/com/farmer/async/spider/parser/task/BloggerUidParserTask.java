package com.farmer.async.spider.parser.task;

import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.parser.message.BloggerUidParserMessage;
import com.farmer.async.spider.save.dao.BloggerDao;
import com.farmer.async.spider.save.message.BloggerUidUpdateMessage;
import com.farmer.async.spider.util.context.ApplicationContextHolder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/20
 */
public class BloggerUidParserTask extends AbstractParserTask<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BloggerUidParserTask.class);

    private BloggerUidParserMessage bloggerUidParserMessage;

    private ActiveMqMessageSend activeMqMessageSend = ApplicationContextHolder.getBean(ActiveMqMessageSend.class);

    public BloggerUidParserTask(BloggerUidParserMessage bloggerUidParserMessage) {

        this.bloggerUidParserMessage = bloggerUidParserMessage;
    }

    @Override
    protected void sendSaveMessage(String result) {

        if (null == result) {
            LOGGER.info("blogger : {} uid parser null!",bloggerUidParserMessage.getBloggerName());
            return;
        }

        BloggerUidUpdateMessage bloggerUidUpdateMessage = new BloggerUidUpdateMessage();
        bloggerUidUpdateMessage.setBloggerName(bloggerUidParserMessage.getBloggerName());
        bloggerUidUpdateMessage.setBloggerUid(result);
        bloggerUidUpdateMessage.setMessageId(UUID.randomUUID().toString());
        bloggerUidUpdateMessage.setMessageType(MessageType.Cnblog.Blogger.CNBLOG_BLOGGER_UID_UPDATE);
        bloggerUidUpdateMessage.setRequestId("");

        activeMqMessageSend.send(bloggerUidUpdateMessage);
    }

    @Override
    public String call() throws Exception {

        Document doc = Jsoup.parse(bloggerUidParserMessage.getDocumentBody());
        Elements es = doc.getElementsByTag("script");
        String blogUserGuid = null;
        for (Element e : es) {

            if (null != e.childNodes() && e.childNodes().size() != 0) {
                String data = e.childNode(0).attr("data");
                if (data.contains("cb_blogUserGuid")) {
                    String[] arr = data.split(",");
                    for (String str : arr) {
                        if (str.contains("cb_blogUserGuid")) {

                            String[] strs = str.split("=");
                            if (null == strs || strs.length < 2) {
                                continue;
                            }

                            String s = str.split("=")[1];
                            blogUserGuid = s.substring(1, s.length() - 1);
                            break;
                        }
                    }
                }
            }

            if (blogUserGuid != null) {
                break;
            }
        }

        return blogUserGuid;
    }

}
