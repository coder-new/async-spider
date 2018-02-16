package com.farmer.async.spider.handler.core;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/27
 */
public class HandlerMethodInfo {

    private Class<?> beanClass;

    private String methodName;

    public HandlerMethodInfo(Class<?> beanClass, String methodName) {
        this.beanClass = beanClass;
        this.methodName = methodName;
    }

    public Class<?> getBeanName() {
        return beanClass;
    }

    public String getMethodName() {
        return methodName;
    }

}
