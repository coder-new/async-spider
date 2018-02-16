package com.farmer.async.spider.handler.core;

import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/27
 */
public class HandlerBean {

    private List<HandlerMethodInfo> runMethodList;

    private List<HandlerMethodInfo> successMethodList;

    private List<HandlerMethodInfo> failureMethodList;

    public List<HandlerMethodInfo> getRunMethodList() {
        return runMethodList;
    }

    public void setRunMethodList(List<HandlerMethodInfo> runMethodList) {
        this.runMethodList = runMethodList;
    }

    public List<HandlerMethodInfo> getSuccessMethodList() {
        return successMethodList;
    }

    public void setSuccessMethodList(List<HandlerMethodInfo> successMethodList) {
        this.successMethodList = successMethodList;
    }

    public List<HandlerMethodInfo> getFailureMethodList() {
        return failureMethodList;
    }

    public void setFailureMethodList(List<HandlerMethodInfo> failureMethodList) {
        this.failureMethodList = failureMethodList;
    }
}
