package com.farmer.async.spider.handler.threadpool;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/18
 */
@Component
public class TaskExecute {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecute.class);

    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private TaskCacheQueue taskCacheQueue;

    @Autowired
    private BlockingQueue<Runnable> blockingQueue;

    @Value("${message.handler.thread.pool.queuesize}")
    private int handlerQueueSize;

    @Value("${message.handler.thread.pool.coresize}")
    private int handlerCoreSize;

    @Value("${message.handler.thread.pool.maxsize}")
    private int handlerMaxSize;

    public void submit(ITask task) {

        LOGGER.info("submit current thread id : {}",Thread.currentThread().getId());

        int cacheSize = taskCacheQueue.size();
        int blockingQueueSize = blockingQueue.size();

        LOGGER.info("cacheSize : {} blockingQueueSize : {}",cacheSize,blockingQueueSize);

        if ((cacheSize > 0) && (blockingQueueSize < handlerQueueSize)) {

            ITask iTask = taskCacheQueue.getTask();
            submitTask(iTask);

            taskCacheQueue.putTask(task);

        } else if (blockingQueueSize == handlerQueueSize) {

            taskCacheQueue.putTask(task);
        } else if ((cacheSize == 0) && (blockingQueueSize < handlerQueueSize)) {

            submitTask(task);
        }

    }

    public boolean canSubmit() {

        return blockingQueue.size() < handlerQueueSize;
    }

    public void submitTask(ITask task) {

        ListenableFuture listenableFuture = listeningExecutorService.submit(task);
        Futures.addCallback(listenableFuture, task);
    }
}
