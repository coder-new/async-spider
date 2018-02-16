package com.farmer.async.spider.util.threadlocal;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/2
 */
public final class RequestIdThreadLocal {

    private static final ThreadLocal<String> requestIdThreadLocal = new ThreadLocal<>();

    public static String getRequestId() {

        return requestIdThreadLocal.get();
    }

    public static void setRequestId(String requestId) {
        requestIdThreadLocal.set(requestId);
    }

    public static void removeRequestId() {

        requestIdThreadLocal.remove();
    }
}
