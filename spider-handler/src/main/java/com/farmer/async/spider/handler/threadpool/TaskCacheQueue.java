package com.farmer.async.spider.handler.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/18
 */
@Component
public class TaskCacheQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskCacheQueue.class);

    private BlockingQueue<ITask> queue;

    @PostConstruct
    public void initQueue() {

        queue = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
    }

    public synchronized void putTask(ITask iTask) {

        LOGGER.info("begin to put...");

        try {
            queue.put(iTask);
            LOGGER.info("end to put...");
        } catch (InterruptedException e) {
            LOGGER.error("put task into cache queue interrupt : {}",e.getMessage());
        }
    }

    public synchronized ITask getTask() {

        if (0 == queue.size()) {
            return null;
        }

        LOGGER.info("queue size : {}",queue.size());

        try {
            ITask iTask = queue.take();
            return iTask;
        } catch (InterruptedException e) {
            LOGGER.error("get task from cache queue interrupt : {}",e.getMessage());
        }

        return null;
    }

    public synchronized int size() {

        return queue.size();
    }
}
