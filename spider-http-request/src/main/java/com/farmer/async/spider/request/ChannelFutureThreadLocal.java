package com.farmer.async.spider.request;

import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/12/11
 */
public final class ChannelFutureThreadLocal {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelFutureThreadLocal.class);

    private static ThreadLocal<ChannelFuture> channelFutureThreadLocal = null;

    public static synchronized void setChannelFuture(ChannelFuture channelFuture) {

        LOGGER.info("set channel future : {}",channelFuture);

        if (null == channelFutureThreadLocal) {
            channelFutureThreadLocal = ThreadLocal.withInitial(() -> channelFuture);
        }
    }

    public static ChannelFuture getChannelFuture() {

        return channelFutureThreadLocal.get();
    }
}
