package com.farmer.async.spider.request.config;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/10/29
 */
public class HttpParamConfig {

    private HttpUrlConfig httpUrlConfig;

    private String param;

    public HttpUrlConfig getHttpUrlConfig() {
        return httpUrlConfig;
    }

    public void setHttpUrlConfig(HttpUrlConfig httpUrlConfig) {
        this.httpUrlConfig = httpUrlConfig;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
