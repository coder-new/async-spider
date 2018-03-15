package com.farmer.async.spider.cache.config;

import com.farmer.async.spider.cache.entity.BloggerCacheEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import static javafx.scene.input.KeyCode.R;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/3/3
 */
@Configuration
public class RedisConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxidle;

    @Value("${spring.redis.pool.min-idle}")
    private int minidle;

    @Value("${spring.redis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWait;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisPoolConfig config = new JedisPoolConfig();
//      最大空闲连接数, 默认8个
        config.setMaxIdle(maxidle);
//      最小空闲连接数, 默认0
        config.setMinIdle(minidle);
//      最大连接数, 默认8个
        config.setMaxTotal(maxActive);
//      获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        config.setMaxWaitMillis(maxWait);

        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setDatabase(database);
        factory.setHostName(host);
        factory.setPort(port);
        factory.setTimeout(timeout);
        factory.setPoolConfig(config);
        return factory;
    }

    @Bean
    public RedisTemplate<String,BloggerCacheEntity> redisTemplate() {
        RedisTemplate<String, BloggerCacheEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<BloggerCacheEntity> jackson2JsonRedisSerializer
                = new Jackson2JsonRedisSerializer<>(BloggerCacheEntity.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        template.setValueSerializer(jackson2JsonRedisSerializer);
        LOGGER.info("create RedisTemplate success");
        return template;
    }
}
