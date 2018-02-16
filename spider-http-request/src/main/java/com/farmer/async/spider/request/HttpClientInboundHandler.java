package com.farmer.async.spider.request;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/10/29
 */
@ChannelHandler.Sharable
@Component
public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientInboundHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{

        System.out.println("*******************" + ctx.channel().remoteAddress());

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("**********close*********");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpResponse) {

            HttpResponse response = (HttpResponse) msg;

            System.out.println("CONTENT LENGTH : " + response.headers().get(HttpHeaderNames.CONTENT_LENGTH));
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaderNames.CONTENT_TYPE));

        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent)msg;
            ByteBuf buf = content.content();
            //System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));

            buf.release();

            System.out.println("content");
        }


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //System.out.println("complete");

        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
