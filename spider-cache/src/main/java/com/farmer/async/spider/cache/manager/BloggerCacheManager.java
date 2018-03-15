package com.farmer.async.spider.cache.manager;

import com.farmer.async.spider.cache.entity.BloggerCacheEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/3/3
 */
@Component
public class BloggerCacheManager {

    @Autowired
    private RedisTemplate<String,BloggerCacheEntity> redisTemplate;

    public BloggerCacheEntity queryByBloggerName(String bloggerName) {

        return redisTemplate.opsForValue().get(bloggerName);
    }

    public void save(BloggerCacheEntity bloggerCacheEntity) {

        redisTemplate.opsForValue().set(bloggerCacheEntity.getBloggerName(),bloggerCacheEntity);
    }
}
