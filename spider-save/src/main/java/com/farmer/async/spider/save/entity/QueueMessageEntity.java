package com.farmer.async.spider.save.entity;

import javax.persistence.Table;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/4
 */
@Table(name = "spider_queue_message")
public class QueueMessageEntity {

    private String id;

    private String queueName;

    private String messageStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getMessageStr() {
        return messageStr;
    }

    public void setMessageStr(String messageStr) {
        this.messageStr = messageStr;
    }
}
