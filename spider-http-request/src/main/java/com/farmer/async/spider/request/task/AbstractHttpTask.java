package com.farmer.async.spider.request.task;

import com.farmer.async.spider.handler.threadpool.AbstractTask;
import com.farmer.async.spider.handler.threadpool.ITask;
import com.farmer.async.spider.request.RequestMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/2
 */
@Component
public abstract class AbstractHttpTask<T> extends AbstractTask<T> implements ITask<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpTask.class);

    @Override
    public void afterSuccessCall(T result) {

        sendParserMessage(result);

        sendStorageMessage();

        sendSaveMessage();
    }

    @Override
    public void onFailure(Throwable t) {

        LOGGER.error("failure : {}",t.getMessage());
    }

    protected abstract void sendParserMessage(T result);

    protected abstract void sendStorageMessage();

    protected abstract void sendSaveMessage();
}
