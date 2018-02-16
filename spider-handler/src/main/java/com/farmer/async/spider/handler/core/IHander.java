package com.farmer.async.spider.handler.core;

import com.farmer.async.spider.message.definition.BaseMessage;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/10
 */
public interface IHander {

    void handle(BaseMessage baseMessage);

    void register();
}
