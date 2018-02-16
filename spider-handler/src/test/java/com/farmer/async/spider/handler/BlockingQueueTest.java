package com.farmer.async.spider.handler;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/18
 */
public class BlockingQueueTest {

    @Test
    public void test() {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

        for (int i=0;i<11;i++) {
            long start = System.currentTimeMillis();

            try {
                queue.put("123");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(queue.size());

            long end = System.currentTimeMillis();

            System.out.println(end - start);
        }
    }
}
