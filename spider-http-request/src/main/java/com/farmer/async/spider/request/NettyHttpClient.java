package com.farmer.async.spider.request;

import com.farmer.async.spider.request.config.HttpRequestConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/10/29
 */
@Component
public class NettyHttpClient {

//    private static final Logger LOGGER = LoggerFactory.getLogger(NettyHttpClient.class);
//
//    private EventLoopGroup eventLoopGroup;
//
//    private Bootstrap bootstrap;
//
//    private ChannelFuture channelFuture;
//
//    @Autowired
//    private HttpClientInboundHandler httpClientInboundHandler;

//    @PostConstruct
//    public void init() {
//
//        LOGGER.info("init netty client");
//
//        eventLoopGroup = new NioEventLoopGroup();
//        bootstrap = new Bootstrap();
//        bootstrap.group(eventLoopGroup);
//        bootstrap.channel(NioSocketChannel.class);
//        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
//
//        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
//            @Override
//            public void initChannel(SocketChannel ch) throws Exception {
//
//                ch.pipeline().addLast(new HttpResponseDecoder());
//                ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
//                ch.pipeline().addLast(new HttpRequestEncoder());
//                ch.pipeline().addLast(httpClientInboundHandler);
//            }
//        });
//
//        initChannelFuture();
//    }
//
//    private void initChannelFuture() {
//
//        LOGGER.info("init channel future!");
//
//        try {
//            channelFuture = bootstrap.connect("www.cnblogs.com", 80).sync();
//        } catch (InterruptedException e) {
//            LOGGER.error("get channel exception!");
//        }
//    }
//
//    public void executePostRequest(String url,String body) {
//
//        if (null == channelFuture) {
//            initChannelFuture();
//        }
//
//        URI uri;
//        try {
//
//            uri = new URI(url);
//            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
//                    uri.toASCIIString(), Unpooled.wrappedBuffer(body.getBytes("UTF-8")));
//
//            request.headers().set(HttpHeaderNames.HOST, "www.cnblogs.com");
//            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//            request.headers().set(HttpHeaderNames.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36 ");
//            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
//
//            channelFuture.addListener((ChannelFutureListener) future -> {
//                if (future.isSuccess()) {
//                    LOGGER.info("future success!");
//
//                    channelFuture.channel().write(request);
//                    channelFuture.channel().flush();
//
//                } else {
//                    LOGGER.error("future error!");
//                }
//            });
//
//            channelFuture.channel().closeFuture().sync();
//
//        } catch (URISyntaxException e) {
//            LOGGER.error(e.getMessage());
//        } catch (UnsupportedEncodingException e) {
//            LOGGER.error(e.getMessage());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    public void connect(String host,int port) throws Exception {
//
//        ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
//
//        System.out.println("connect!");
//
//        SyncRequest syncRequest = new SyncRequest();
//
//        for (int i = 1;i < 2;i++) {
//            syncRequest.request(host,"https://q.cnblogs.com/q/rank?page=" + i,channelFuture);
//        }
//
//        channelFuture.channel().closeFuture().sync();
//
//        eventLoopGroup.shutdownGracefully();
//    }
//
//    @PreDestroy
//    public void closeChannel() {
//
//        if (channelFuture != null) {
//            try {
//                channelFuture.channel().closeFuture().sync();
//            } catch (InterruptedException e) {
//                LOGGER.error(e.getMessage());
//            }
//        }
//
//        if (eventLoopGroup != null) {
//            eventLoopGroup.shutdownGracefully();
//        }
//    }

}
