package com.farmer.async.spider.controller;

import com.farmer.async.spider.controller.cnblogs.HomePageSpiderEndPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/10/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHomePageSpiderEndPoint {

    @Autowired
    private HomePageSpiderEndPoint homePageSpiderEndPoint;

    @Test
    public void test() {

        //homePageSpiderEndPoint.start();
    }
}
