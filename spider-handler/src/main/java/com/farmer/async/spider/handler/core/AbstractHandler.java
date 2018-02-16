package com.farmer.async.spider.handler.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/10
 */
@Component
public abstract class AbstractHandler implements IHander{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHandler.class);

    @Autowired
    private HanderManager handerManager;

    @Override
    @PostConstruct
    public void register() {

        LOGGER.info("register messageType : {} *******************",getMessageType());
        LOGGER.info("register iHandler : {} ********************",this);

        handerManager.setHandler(getMessageType(),this);
    }

    public void setHanderManager(HanderManager handerManager) {

        this.handerManager = handerManager;
    }

    protected abstract String getMessageType();
}
