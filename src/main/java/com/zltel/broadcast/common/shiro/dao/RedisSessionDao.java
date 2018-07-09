package com.zltel.broadcast.common.shiro.dao;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.zltel.broadcast.common.util.CacheUtil;


@Component
public class RedisSessionDao extends EnterpriseCacheSessionDAO {

    public static final Logger log = LoggerFactory.getLogger(RedisSessionDao.class);

    @Resource
    RedisTemplate<Serializable, Session> redisTemplate;

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        log.info("创建session:{}",sessionId);
        redisTemplate.opsForValue().set(sessionId, session, session.getTimeout(),
                TimeUnit.MILLISECONDS);
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = redisTemplate.opsForValue().get(sessionId);
        log.debug("读取redis session:{}",session);
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    protected void doUpdate(Session session) {
        redisTemplate.opsForValue().set(session.getId(), session, session.getTimeout(),
                TimeUnit.MILLISECONDS);
    }

    // 删除session
    @Override
    protected void doDelete(Session session) {
        log.debug("删除session:{}",session);
        redisTemplate.delete(session.getId());
        CacheUtil.clearLogCountCache(session);
    }

}
