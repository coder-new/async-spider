package com.farmer.async.spider.request;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostResquestTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test() {

        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("CategoryId",String.valueOf(808));
        hashMap.put("CategoryType","SiteHome");
        hashMap.put("ItemListActionName","PostList");
        hashMap.put("PageIndex",String.valueOf(3));
        hashMap.put("ParentCategoryId",String.valueOf(0));
        hashMap.put("TotalPostCount",String.valueOf(4000));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");

        HttpEntity<String> requestEntity = new HttpEntity<>(hashMap.toString(), httpHeaders);

        String url = "https://www.cnblogs.com/mvc/AggSite/PostList.aspx";

        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.POST,requestEntity, String.class);

        System.out.println(resp.getStatusCode());
        System.out.println(resp.getBody());
    }
}
