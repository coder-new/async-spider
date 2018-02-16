package com.farmer.async.spider.controller;

import com.farmer.async.spider.message.Constants;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/4
 */
@Component
public class DownloadMessageHandler {

    @JmsListener(destination = Constants.DOWNLOAD_QUEUE_NAME)
    public void receive(String messageStr) {

        System.out.println("download message!" + messageStr);
    }
}
