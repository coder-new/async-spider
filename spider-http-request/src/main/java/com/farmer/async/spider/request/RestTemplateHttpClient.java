package com.farmer.async.spider.request;

import com.farmer.async.spider.request.util.CookieManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/16
 */
@Component
public class RestTemplateHttpClient {


    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateHttpClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Autowired
    private CookieManager cookieManager;

    public String get(String url) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie",cookieManager.getCookie());

        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String body = responseEntity.getBody();
        int statusCodeValue = responseEntity.getStatusCodeValue();

        if (200 == statusCodeValue) {
            return body;
        }

        return null;
    }

    public void asyncGet(String url,CallBackExecute callBackExecute) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie",cookieManager.getCookie());

        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
        ListenableFuture<ResponseEntity<String>> forEntity
                = asyncRestTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("get url : {} error : {}",url, ex.getMessage());
            }

            @Override
            public void onSuccess(ResponseEntity<String> result) {

                int statusCodeValue = result.getStatusCodeValue();
                if (200 == statusCodeValue) {
                    callBackExecute.execute(result.getBody());
                } else {
                    LOGGER.error("get url : {} error,status : {}",url,statusCodeValue);
                }

            }
        });
    }
}
