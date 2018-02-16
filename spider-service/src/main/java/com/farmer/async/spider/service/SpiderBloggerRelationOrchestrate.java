package com.farmer.async.spider.service;

import com.farmer.async.spider.handler.core.HandlerBean;
import com.farmer.async.spider.handler.core.HandlerMethodInfo;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.request.manager.BloggerRelationRequestManager;
import com.farmer.async.spider.save.dao.BloggerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;


/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/27
 */
@Component
public class SpiderBloggerRelationOrchestrate {

    @Autowired
    private ActiveMqMessageSend activeMqMessageSend;

    @Autowired
    private BloggerDao bloggerDao;

    public void orchestrate() {


        Map<String,HandlerBean> handlerBeanMap = new HashMap<>();
    }

    private void registerFollowerHandler(Map<String,HandlerBean> handlerBeanMap) {

        HandlerBean handlerBean = new HandlerBean();

        HandlerMethodInfo handlerMethodInfo = new HandlerMethodInfo(BloggerRelationRequestManager.class,
                "getBloggerFollowerPage");
        handlerBean.setRunMethodList(asList(handlerMethodInfo));


    }

}
