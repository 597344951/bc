package com.zltel.broadcast.common.cache;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
/**
 * mybatis Redis二级缓存
 * 
 * @author wangch
 *
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Mybatis二级缓存mybatis实现
 * 
 * @author wangch
 *
 */
public class MybatisRedisCache implements Cache {
    /** MyBatis二级缓存缓存时间 **/
    public static final Integer CACHE_TIME_OUT = 10;

    private static final Logger log = LoggerFactory.getLogger(MybatisRedisCache.class);

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id; // cache instance id



    public RedisTemplate<Object, Object> getRedisTemplate() {
        return RedisTemplateFacade.getInstince();
    }

    public MybatisRedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        log.debug("创建 mybatis redis二级缓存:{}", id);
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        this.getRedisTemplate().opsForHash().put(this.getId(), key, value);
        this.getRedisTemplate().expire(this.getId(), CACHE_TIME_OUT, TimeUnit.MINUTES);
        log.debug("保存数据: {},{}", this.getId(), key);
    }

    @Override
    public Object getObject(Object key) {
        log.debug("获取数据:{},{}", this.getId(), key);
        return this.getRedisTemplate().opsForHash().get(this.getId(), key);
    }

    @Override
    public Object removeObject(Object key) {
        log.debug("删除数据:{},{}", this.getId(), key);
        return this.getRedisTemplate().opsForHash().delete(this.getId(), key);
    }

    @Override
    public void clear() {
        log.debug("清空缓存{}", this.getId());
        this.getRedisTemplate().opsForHash().getOperations().delete(this.getId());
    }

    @Override
    public int getSize() {

        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

}
