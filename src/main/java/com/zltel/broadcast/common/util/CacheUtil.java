package com.zltel.broadcast.common.util;

import java.io.Serializable;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import com.zltel.broadcast.um.bean.SysUser;


/**
 * 缓存控制器
 */
@Component
public class CacheUtil {
    /** 账户登陆用户数 记录信息 **/
    public static final String KICKOUT_SESSION = "shiro-kickout-session";
    /** 账户登录重试次数 记录 **/
    public static final String PASSWORD_RETRY = "passwordRetryCache";
    /** 权限信息 缓存 **/
    public static final String AUTHORIZATION_CACHE = "authorizationCache";
    /** token 授权信息缓存 **/
    public static final String AUTHENTICATIONCACHE_TOKEN = "authenticationCache_token";
    /** 用户名/密码 授权信息缓存 **/
    public static final String AUTHENTICATIONCACHE_UP = "authenticationCache_up";

    private static CacheManager cacheManager;

    @Resource
    public void setCache(CacheManager cm) {
        CacheUtil.cacheManager = cm;
    }

    /** 获取密码重试缓存 **/
    public static Cache<String, AtomicInteger> getPasswordRetryCache() {
        return cacheManager.getCache(CacheUtil.PASSWORD_RETRY);
    }

    /** 获取登陆限制缓存 **/
    public static Cache<String, Deque<Serializable>> getKickOutCache() {
        return cacheManager.getCache(CacheUtil.KICKOUT_SESSION);
    }

    /** 获取 权限信息缓存 **/
    public static Cache<Object, AuthorizationInfo> getAuthorizationCache() {
        return cacheManager.getCache(CacheUtil.AUTHORIZATION_CACHE);
    }

    /** 获取用户名/密码 登陆缓存 **/
    public static Cache<Object, AuthenticationInfo> getUpAuthenticationCache() {
        return cacheManager.getCache(CacheUtil.AUTHENTICATIONCACHE_UP);
    }

    /** 获取 token 登陆缓存 **/
    public static Cache<Object, AuthenticationInfo> getTokenAuthenticationCache() {
        return cacheManager.getCache(CacheUtil.AUTHENTICATIONCACHE_TOKEN);
    }

    /** 清除 登陆凭据缓存 **/
    public static void clearAuthenticationCache(String un) {
        getUpAuthenticationCache().remove(un);
    }

    /** 清楚 权限数据缓存 **/
    public static void clearAuthorizationCache(String un) {
        List<Object> datas = getAuthorizationCache().keys().stream().filter(a -> {
            return getSysUser(a).getUsername().equals(un);
        }).collect(Collectors.toList());
        datas.forEach(k -> getAuthorizationCache().remove(k));
    }

    /**
     * 清除 登出session 计数
     */
    public static void clearLogCountCache(Session session) {
        getKickOutCache().values().forEach(l -> l.remove(session.getId()));
    }

    /**
     * 清除 登出session 计数
     */
    public static void clearLogCountCache(String sid) {
        getKickOutCache().values().forEach(l -> {
            l.remove(sid);
        });
    }

    /**
     * 清除 账户重试信息
     */
    public static void clearRetryCache(String un) {
        getPasswordRetryCache().remove(un);
    }

    /** 获取用户信息 **/
    public static SysUser getSysUser(Object princ) {

        return (SysUser) ((PrincipalCollection) princ).getPrimaryPrincipal();
    }

}
