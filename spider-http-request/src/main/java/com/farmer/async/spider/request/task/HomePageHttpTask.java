package com.farmer.async.spider.request.task;

import com.farmer.async.spider.message.MessageType;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.parser.message.HomePageParserMessage;
import com.farmer.async.spider.request.RequestMessageHandler;
import com.farmer.async.spider.request.RestTemplateHttpClient;
import com.farmer.async.spider.request.message.HomePageMessage;
import com.farmer.async.spider.util.context.ApplicationContextHolder;
import com.farmer.async.spider.util.threadlocal.RequestIdThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/11
 */
public class HomePageHttpTask extends AbstractHttpTask<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomePageHttpTask.class);

    private RestTemplate restTemplate = ApplicationContextHolder.getBean(RestTemplate.class);

    private ActiveMqMessageSend activeMqMessageSend = ApplicationContextHolder.getBean(ActiveMqMessageSend.class);

    private HomePageMessage homePageMessage;

    public HomePageHttpTask(HomePageMessage homePageMessage) {

        this.homePageMessage = homePageMessage;
    }

    @Override
    protected void sendParserMessage(String pageBody) {

        HomePageParserMessage homePageParserMessage = new HomePageParserMessage();
        homePageParserMessage.setMessageId(UUID.randomUUID().toString());
        homePageParserMessage.setMessageType(MessageType.Cnblog.Homepage.CNBLOG_HOMEPAGE_PARSER);
        homePageParserMessage.setRequestId(RequestIdThreadLocal.getRequestId());
        homePageParserMessage.setPageBody(pageBody);

        activeMqMessageSend.send(homePageParserMessage);

        LOGGER.info("sendParserMessage : {}",homePageParserMessage);
    }

    @Override
    protected void sendStorageMessage() {

        LOGGER.info("sendStorageMessage");
    }

    @Override
    protected void sendSaveMessage() {

        LOGGER.info("sendSaveMessage");
    }

    @Override
    public String call() throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");

        HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(homePageMessage.getRequestBody(), httpHeaders);

        String url = "https://www.cnblogs.com/mvc/AggSite/PostList.aspx";

        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.POST,requestEntity, String.class);

        LOGGER.info("execute success : {}",resp.getStatusCode());

        return resp.getBody();
    }
}
