package com.farmer.async.spider.request;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateHttpClientTest {


    @Autowired
    private RestTemplateHttpClient restTemplateHttpClient;

    @Test
    public void testGet() {

        String url = "https://www.cnblogs.com/NeverCtrl-C/";

        String body = restTemplateHttpClient.get(url);

        System.out.println(body);
    }

    @Test
    public void testGetFollowerPage() {

        String url = "https://home.cnblogs.com/u/xrq730/followers";
        String body = restTemplateHttpClient.get(url);

        System.out.println(body);
    }
}
