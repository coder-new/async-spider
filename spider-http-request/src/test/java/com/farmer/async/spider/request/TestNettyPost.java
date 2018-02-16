package com.farmer.async.spider.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/10/29
 */
public class TestNettyPost {


    @Test
    public void test() throws InterruptedException, URISyntaxException, UnsupportedEncodingException {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                ch.pipeline().addLast(new HttpResponseDecoder());
                ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                ch.pipeline().addLast(new HttpRequestEncoder());
                ch.pipeline().addLast(new HttpClientInboundHandler());
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("www.cnblogs.com", 80).sync();
        System.out.println("connect!");

        URI uri = new URI("https://www.cnblogs.com/mvc/AggSite/PostList.aspx");

        JSONObject obj = new JSONObject();
        obj.put("CategoryId",808);
        obj.put("CategoryType","SiteHome");
        obj.put("ItemListActionName","PostList");
        obj.put("PageIndex",3);
        obj.put("ParentCategoryId",0);
        obj.put("TotalPostCount",4000);

        String requestBody = JSON.toJSONString(obj);

        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                uri.toASCIIString(), Unpooled.wrappedBuffer(requestBody.getBytes("UTF-8")));

        request.headers().set(HttpHeaderNames.HOST, "www.cnblogs.com");
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36 ");
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        // 发送http请求
        channelFuture.channel().write(request);
        channelFuture.channel().flush();

        channelFuture.channel().closeFuture().sync();

        eventLoopGroup.shutdownGracefully();

    }
}
