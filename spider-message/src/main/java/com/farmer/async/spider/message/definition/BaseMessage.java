package com.farmer.async.spider.message.definition;

import java.io.Serializable;

public class BaseMessage implements Serializable{

    private static final long serialVersionUID = 1001101278115368918L;


    private String messageId;

    private String messageType;

    private String requestId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
