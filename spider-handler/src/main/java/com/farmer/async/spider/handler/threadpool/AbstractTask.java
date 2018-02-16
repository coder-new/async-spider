package com.farmer.async.spider.handler.threadpool;

import com.farmer.async.spider.util.context.ApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/1
 */
public abstract class AbstractTask<T> implements ITask<T>{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTask.class);

    private TaskExecute taskExecute = ApplicationContextHolder.getBean(TaskExecute.class);

    private TaskCacheQueue taskCacheQueue = ApplicationContextHolder.getBean(TaskCacheQueue.class);

    @Override
    public void onSuccess(T result) {

        LOGGER.info("success current thread id : {}",Thread.currentThread().getId());

        if (taskExecute == null) {
            LOGGER.info("taskExecute is null!");
        }

        if (taskCacheQueue == null) {
            LOGGER.info("taskCacheQueue is null!");
        }

        afterSuccessCall(result);

        LOGGER.info("success!");

        LOGGER.info("begin to submit task!");

        ITask iTask = taskCacheQueue.getTask();

        boolean canSubmit = taskExecute.canSubmit();

        LOGGER.info(String.valueOf(canSubmit));

        if (null != iTask && canSubmit) {
            taskExecute.submitTask(iTask);
        } else if(null != iTask) {
            taskCacheQueue.putTask(iTask);
        }

        LOGGER.info("end submit task!");

    }

    protected abstract void afterSuccessCall(T result);

}