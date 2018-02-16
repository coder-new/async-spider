package com.farmer.async.spider.handler.threadpool;

import com.google.common.util.concurrent.FutureCallback;

import java.util.concurrent.Callable;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/18
 */
public interface ITask<T> extends Callable<T>, FutureCallback<T> {

    T call() throws Exception;

    void onSuccess(T result);

    void onFailure(Throwable t);
}
