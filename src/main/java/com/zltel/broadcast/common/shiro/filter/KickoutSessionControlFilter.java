package com.zltel.broadcast.common.shiro.filter;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import com.zltel.broadcast.common.configuration.ResourceProviderConfig;
import com.zltel.broadcast.common.util.CacheUtil;
import com.zltel.broadcast.um.bean.SysUser;

/**
 * Reason: 单用户登陆控制
 */
public class KickoutSessionControlFilter extends AccessControlFilter {

    public static final Logger log = LoggerFactory.getLogger(KickoutSessionControlFilter.class);
    

    private boolean kickoutAfter = false; // 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 5; // 同一个帐号最大会话数 默认1

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache(CacheUtil.KICKOUT_SESSION);
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


   

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
            Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        //设定url加载资源对象
        request.setAttribute("urls", ResourceProviderConfig.getResourceProvider());
        
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            // 如果没有登录，直接进行之后的流程
            return true;
        }

        Session session = subject.getSession();
        SysUser sysuser = (SysUser) subject.getPrincipal();
        String username = sysuser.getUsername();
        Serializable sessionId = session.getId();

        //
        Deque<Serializable> deque = cache.get(username);
        if (deque == null) {
            deque = new LinkedList<>();
            cache.put(username, deque);
        }

        // 如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }

        // 如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if (kickoutAfter) { // 如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { // 否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession =
                        sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    kickoutSession.stop();// 清除会话
                }
            } catch (Exception e) {// ignore exception
                log.error(e.getMessage());
            }
        }

        return true;
    }
}
