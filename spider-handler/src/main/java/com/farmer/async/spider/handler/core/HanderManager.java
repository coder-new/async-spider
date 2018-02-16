package com.farmer.async.spider.handler.core;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/10
 */
@Component
public class HanderManager {

    private Map<String,IHander> handlerMap;

    @PostConstruct
    public void init() {

        handlerMap = new HashMap<>();
    }

    public IHander getHandler(String messageType) {

        return handlerMap.get(messageType);
    }

    public void setHandler(String messageType,IHander iHander) {

        handlerMap.put(messageType,iHander);
    }
}
