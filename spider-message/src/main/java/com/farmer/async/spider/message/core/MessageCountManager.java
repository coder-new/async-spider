package com.farmer.async.spider.message.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/26
 */
@Component
public class MessageCountManager {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(MessageCountManager.class);

    private int cnt = 0;

    public synchronized void increment() {

        cnt++;

        if (cnt%1000 == 0) {
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                LOGGER.error("cnt error!");
            }
        }
    }


}
