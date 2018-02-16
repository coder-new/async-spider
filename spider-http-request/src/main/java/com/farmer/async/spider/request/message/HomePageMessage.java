package com.farmer.async.spider.request.message;

import com.farmer.async.spider.message.definition.BaseMessage;

import java.util.Map;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/2
 */
public class HomePageMessage extends BaseMessage{

    private String requestUrl;

    private String requestType;

    private Map<String,Object> requestBody;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Map<String, Object> getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Map<String, Object> requestBody) {
        this.requestBody = requestBody;
    }
}
