package com.farmer.async.spider.request.config;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/10/29
 */
public class HttpUrlConfig {


    private HttpMethodConfig httpMethodConfig;

    private String url;

    public HttpMethodConfig getHttpMethodConfig() {
        return httpMethodConfig;
    }

    public void setHttpMethodConfig(HttpMethodConfig httpMethodConfig) {
        this.httpMethodConfig = httpMethodConfig;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
