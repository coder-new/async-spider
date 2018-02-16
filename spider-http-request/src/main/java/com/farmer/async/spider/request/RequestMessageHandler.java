package com.farmer.async.spider.request;

import com.farmer.async.spider.request.message.HomePageMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/1
 */
@Component
public class RequestMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestMessageHandler.class);

    @Autowired
    private NettyHttpClient nettyHttpClient;

    public String handler(HomePageMessage homePageMessage) {

        LOGGER.info("handler : {}",homePageMessage);

        //nettyHttpClient.executePostRequest(homePageMessage.getRequestUrl(),homePageMessage.getRequestBody());

        return "";
    }
}
