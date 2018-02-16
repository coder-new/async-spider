package com.farmer.async.spider.request.manager;

import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.request.message.HomePageMessage;
import com.farmer.async.spider.util.threadlocal.RequestIdThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/2
 */
@Component
public class HomePageHttpManager {

    @Autowired
    private ActiveMqMessageSend activeMqMessageSend;

    public void start(int size) {

        List<HomePageMessage> homePageMessages = getHomePageHttpMessage(size);

        for (HomePageMessage homePageMessage : homePageMessages) {
            activeMqMessageSend.send(homePageMessage);
        }
    }

    private List<HomePageMessage> getHomePageHttpMessage(int size) {

        List<HomePageMessage> homePageMessages = new ArrayList<>();

        for (int i=1;i<size;i++) {

            HomePageMessage homePageMessage = new HomePageMessage();
            homePageMessage.setRequestUrl("https://www.cnblogs.com/mvc/AggSite/PostList.aspx");
            homePageMessage.setRequestType("POST");
            homePageMessage.setRequestBody(getHomePageHttpBody(i));

            homePageMessage.setMessageId(UUID.randomUUID().toString());
            homePageMessage.setMessageType(MessageType.Cnblog.Homepage.CNBLOG_HOMEPAGE_DOWNLOAD);
            homePageMessage.setRequestId(RequestIdThreadLocal.getRequestId());

            homePageMessages.add(homePageMessage);
        }

        return homePageMessages;
    }

    private Map<String,Object> getHomePageHttpBody(int i) {

        Map<String,Object> body = new HashMap<>();
        body.put("CategoryId",808);
        body.put("CategoryType","SiteHome");
        body.put("ItemListActionName","PostList");
        body.put("PageIndex",i);
        body.put("ParentCategoryId",0);
        body.put("TotalPostCount",4000);

        return body;
    }
}
