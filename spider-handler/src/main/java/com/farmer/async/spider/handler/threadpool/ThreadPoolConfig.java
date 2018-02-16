package com.farmer.async.spider.handler.threadpool;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/18
 */
@Configuration
public class ThreadPoolConfig {


    @Value("${message.handler.thread.pool.coresize}")
    private int handlerCoreSize;

    @Value("${message.handler.thread.pool.maxsize}")
    private int handlerMaxSize;

    @Value("${message.handler.thread.pool.keeplive}")
    private long handlerKeepAlive;

    @Value("${message.handler.thread.pool.queuesize}")
    private int handlerQueueSize;

    @Bean
    public BlockingQueue<Runnable> blockingQueue() {

        return new LinkedBlockingQueue<>(handlerQueueSize);
    }

    @Bean
    public ExecutorService executorService(BlockingQueue<Runnable> blockingQueue) {

        ExecutorService executorServive = new ThreadPoolExecutor(handlerCoreSize, handlerMaxSize, handlerKeepAlive,
                TimeUnit.MILLISECONDS, blockingQueue);

        return executorServive;
    }

    @Bean
    public ListeningExecutorService listeningExecutorService(ExecutorService executorService) {

        return MoreExecutors.listeningDecorator(executorService);
    }
}
