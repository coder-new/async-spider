package com.farmer.async.spider.parser.message;

import com.farmer.async.spider.message.definition.BaseMessage;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/18
 */
public class HomePageParserMessage extends BaseMessage {

    private String pageBody;

    public String getPageBody() {
        return pageBody;
    }

    public void setPageBody(String pageBody) {
        this.pageBody = pageBody;
    }
}
