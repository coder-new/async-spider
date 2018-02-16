package com.farmer.async.spider.request.config;

import io.netty.handler.codec.http.DefaultFullHttpRequest;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/10/29
 */
public class HttpRequestConfig {

    private HttpParamConfig httpParamConfig;

    private DefaultFullHttpRequest defaultFullHttpRequest;

    public HttpParamConfig getHttpParamConfig() {
        return httpParamConfig;
    }

    public void setHttpParamConfig(HttpParamConfig httpParamConfig) {
        this.httpParamConfig = httpParamConfig;
    }

    public DefaultFullHttpRequest getDefaultFullHttpRequest() {
        return defaultFullHttpRequest;
    }

    public void setDefaultFullHttpRequest(DefaultFullHttpRequest defaultFullHttpRequest) {
        this.defaultFullHttpRequest = defaultFullHttpRequest;
    }
}
