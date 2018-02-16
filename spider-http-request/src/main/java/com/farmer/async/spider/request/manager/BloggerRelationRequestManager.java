package com.farmer.async.spider.request.manager;

import com.farmer.async.spider.request.RestTemplateHttpClient;
import com.farmer.async.spider.request.message.BloggerRelationDownloadMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/28
 */
@Component
public class BloggerRelationRequestManager {

    @Autowired
    private RestTemplateHttpClient restTemplateHttpClient;

    public String getBloggerFollowerPage(BloggerRelationDownloadMessage bloggerRelationDownloadMessage) {

        return restTemplateHttpClient.get(bloggerRelationDownloadMessage.getBloggerRelationPageUrl());
    }


}
