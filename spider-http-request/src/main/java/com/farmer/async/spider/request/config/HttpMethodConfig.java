package com.farmer.async.spider.request.config;

import io.netty.handler.codec.http.HttpMethod;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/10/29
 */
public class HttpMethodConfig {

    private HttpHostConfig httpHostConfig;

    private HttpMethod httpMethod;

    public HttpHostConfig getHttpHostConfig() {
        return httpHostConfig;
    }

    public void setHttpHostConfig(HttpHostConfig httpHostConfig) {
        this.httpHostConfig = httpHostConfig;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }
}
