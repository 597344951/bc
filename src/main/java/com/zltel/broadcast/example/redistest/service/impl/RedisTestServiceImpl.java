package com.zltel.broadcast.example.redistest.service.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.zltel.broadcast.example.redistest.service.RedisTestService;

@Component
public class RedisTestServiceImpl implements RedisTestService {
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void saveStr(String key, String value) {
        this.stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String readStr(String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
    }

}
