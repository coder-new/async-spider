package com.farmer.async.spider.handler.core;

import com.farmer.async.spider.message.definition.BaseMessage;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/28
 */
public class HandlerContext {

    private BaseMessage baseMessage;

    private Class<?> messageClass;

    public BaseMessage getBaseMessage() {
        return baseMessage;
    }

    public void setBaseMessage(BaseMessage baseMessage) {
        this.baseMessage = baseMessage;
    }

    public Class<?> getMessageClass() {
        return messageClass;
    }

    public void setMessageClass(Class<?> messageClass) {
        this.messageClass = messageClass;
    }
}
