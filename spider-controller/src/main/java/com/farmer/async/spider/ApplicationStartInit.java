package com.farmer.async.spider;

import com.farmer.async.spider.message.persistence.IMessagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/23
 */
@Component
public class ApplicationStartInit {

    @Autowired
    private IMessagePersistence messagePersistence;

    public void init() {

        //messagePersistence.restore();
    }
}
