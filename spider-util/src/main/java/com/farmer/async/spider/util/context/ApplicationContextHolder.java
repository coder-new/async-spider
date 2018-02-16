package com.farmer.async.spider.util.context;

import org.springframework.context.ApplicationContext;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/11
 */
public final class ApplicationContextHolder {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {

        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) {

        return applicationContext.getBean(tClass);
    }
}
