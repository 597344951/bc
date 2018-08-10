package com.zltel.broadcast.common.cache;

import org.springframework.data.redis.core.RedisTemplate;

import com.zltel.broadcast.common.util.SpringContextUtils;

/**
 * 获取Springboot中RedisTemplate操作对象
 * 
 * @author wangch
 *
 */
public class RedisTemplateFacade {
    private RedisTemplateFacade() {}

    public static final <K, V> RedisTemplate<K, V> getInstince() {

        return SpringContextUtils.getBean("redisTemplate");
    }

}
