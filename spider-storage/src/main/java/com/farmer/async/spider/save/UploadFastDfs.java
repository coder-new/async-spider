package com.farmer.async.spider.save;

import com.farmer.async.spider.message.definition.StorageMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/29
 */
@Component
public class UploadFastDfs {

    @Autowired
    private FastDfsConfig fastDfsConfig;

    public void upload(StorageMessage storageMessage) {


    }
}
