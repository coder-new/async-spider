package com.farmer.async.spider.handler.task;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/12
 */
public class TaskExecuteTest {

    @Test
    public void test() {

        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(100);

        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 5000,
                TimeUnit.MILLISECONDS, blockingQueue);

        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);

        for (int i=0;i<2;i++) {
            ListenableFuture listenableFuture = listeningExecutorService.submit(new TestTask());
            Futures.addCallback(listenableFuture, new TestTask());
        }


        System.out.println("submit current thread id : " + Thread.currentThread().getId());

//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
