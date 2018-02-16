package com.farmer.async.spider.request;

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

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/9
 */
public class NettyGetTest {


    @Test
    public void test() throws InterruptedException, URISyntaxException {

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

        URI uri = new URI("http://www.cnblogs.com/tdfblog/p/DeepCopy-By-IL.html");
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                uri.toASCIIString());

        request.headers().set(HttpHeaderNames.HOST, "www.cnblogs.com");
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        channelFuture.channel().write(request);
        channelFuture.channel().flush();

        channelFuture.channel().closeFuture().sync();

        eventLoopGroup.shutdownGracefully();
    }
}
