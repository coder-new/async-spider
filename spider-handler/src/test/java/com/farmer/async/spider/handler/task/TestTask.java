package com.farmer.async.spider.handler.task;

import com.farmer.async.spider.handler.threadpool.ITask;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/12
 */
public class TestTask implements ITask<String>{


    @Override
    public String call() throws Exception {

        System.out.println("call current thread id : " + Thread.currentThread().getId());

        Thread.sleep(1000);

        return "";
    }

    @Override
    public void onSuccess(String result) {

        System.out.println("success current thread id : " + Thread.currentThread().getId());
    }

    @Override
    public void onFailure(Throwable t) {

        System.out.println("failure current thread id : " + Thread.currentThread().getId());
    }
}
