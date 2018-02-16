package com.farmer.async.spider.handler.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/18
 */
@Component
public class CacheQueueRejectedExecutionHandler implements RejectedExecutionHandler {


    private static final Logger LOGGER = LoggerFactory.getLogger(CacheQueueRejectedExecutionHandler.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        LOGGER.info(String.valueOf(Thread.currentThread().getId()));

        LOGGER.info("###############reject task,current queue size :{}",String.valueOf(executor.getQueue().size()));

        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {

            LOGGER.error(e.getMessage());
        }
    }
}
