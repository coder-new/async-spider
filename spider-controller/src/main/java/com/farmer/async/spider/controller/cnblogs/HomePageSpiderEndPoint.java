package com.farmer.async.spider.controller.cnblogs;

import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.request.manager.HomePageHttpManager;
import com.farmer.async.spider.util.threadlocal.RequestIdThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/10/29
 */
@RestController
public class HomePageSpiderEndPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomePageSpiderEndPoint.class);


    @Autowired
    private HomePageHttpManager homePageHttpManager;

    @Autowired
    private ActiveMqMessageSend activeMqMessageSend;

    @RequestMapping(value = "/spider/cnblogs/homepage/{size}",
            method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void start(@RequestHeader("X-Request-ID") String requestId,@PathVariable int size) {

        RequestIdThreadLocal.setRequestId(requestId);

        long start = System.currentTimeMillis();

        homePageHttpManager.start(size);

        long end = System.currentTimeMillis();

        LOGGER.info("time : {}",end - start);
    }
}
