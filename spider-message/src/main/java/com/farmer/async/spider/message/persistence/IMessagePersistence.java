package com.farmer.async.spider.message.persistence;

import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/22
 */
public interface IMessagePersistence {

    void persistence(String id,String queueName,String messageStr);

    void delete(String id);

    void restore();
}
