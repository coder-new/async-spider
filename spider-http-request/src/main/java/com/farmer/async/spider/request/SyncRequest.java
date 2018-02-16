package com.farmer.async.spider.request;

import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/10/29
 */
public class SyncRequest {


    public void request(String host,String url,ChannelFuture channelFuture) throws URISyntaxException {

        URI uri = new URI(url);

        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                uri.toASCIIString());

        request.headers().set(HttpHeaderNames.HOST, host);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        // 发送http请求
        channelFuture.channel().write(request);
        channelFuture.channel().flush();
    }
}
