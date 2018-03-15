package com.farmer.async.spider.cache.redis;

import com.farmer.async.spider.cache.CacheTestApplication;
import com.farmer.async.spider.cache.entity.BloggerCacheEntity;
import com.farmer.async.spider.cache.manager.BloggerCacheManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/3/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CacheTestApplication.class)
@TestPropertySource(locations="classpath:cache.properties")
public class RedisCacheTest {

    @Autowired
    private RedisTemplate<String,BloggerCacheEntity> redisTemplate;

    @Autowired
    private BloggerCacheManager bloggerCacheManager;

    @Test
    public void insert() {

        BloggerCacheEntity bloggerCacheEntity = new BloggerCacheEntity();
        bloggerCacheEntity.setBloggerName("test001");
        bloggerCacheEntity.setFetchFolloweePage(0);
        bloggerCacheEntity.setFetchFollowerPage(1);
        bloggerCacheEntity.setId(1);
        bloggerCacheEntity.setIsRelation(1);
        bloggerCacheEntity.setMaxFolloweePage(1);
        bloggerCacheEntity.setMaxFollowerPage(1);

        bloggerCacheManager.save(bloggerCacheEntity);

        BloggerCacheEntity entity = redisTemplate.opsForValue().get("test001");

        Assert.assertEquals(bloggerCacheEntity,redisTemplate.opsForValue().get("test001"));
    }

    @Test
    public void get() {

        BloggerCacheEntity bloggerCacheEntity = bloggerCacheManager.queryByBloggerName("test001");

        System.out.println("");
    }

    @Test
    public void getAll() {

        Set<String> keySet = redisTemplate.keys("*");
        for (String key : keySet) {
            BloggerCacheEntity entity = redisTemplate.opsForValue().get(key);
            System.out.println(entity);
        }
    }
}
