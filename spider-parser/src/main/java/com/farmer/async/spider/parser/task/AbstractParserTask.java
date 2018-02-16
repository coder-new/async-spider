package com.farmer.async.spider.parser.task;

import com.farmer.async.spider.handler.threadpool.AbstractTask;
import com.farmer.async.spider.handler.threadpool.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/18
 */
public abstract class AbstractParserTask<T> extends AbstractTask<T> implements ITask<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractParserTask.class);

    @Override
    protected void afterSuccessCall(T result) {

        sendSaveMessage(result);
    }

    @Override
    public void onFailure(Throwable t) {

        LOGGER.error("failure : {}",t.getMessage());
    }

    protected abstract void sendSaveMessage(T result);
}
