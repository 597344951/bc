package com.zltel.broadcast.common.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.zltel.broadcast.common.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import com.zltel.broadcast.common.shiro.dao.RedisSessionDao;
import com.zltel.broadcast.common.shiro.filter.KickoutSessionControlFilter;
import com.zltel.broadcast.common.shiro.realm.TokenRealm;
import com.zltel.broadcast.common.shiro.realm.UserRealm;
import com.zltel.broadcast.common.util.PasswordHelper;

@Configuration
@ConfigurationProperties(prefix = "shiro.config")
public class ShiroConfig {
    public static final Logger log = LoggerFactory.getLogger(ShiroConfig.class);
    /** hash算法 **/
    private static String hashalgorithmname = "md5";
    /** hash计算次数 **/
    private static int hashiterations = 2;
    
    private static final int SESSION_VALIDATION_INTERVAL = 30 * 60000;
    private static final int GLOBAL_SESSION_TIMEOUT = 30 * 60 * 1000;

    /** 测试名称 **/
    private static String name = "test";
    // ----------------setter----------------

    public static String getName() {
        return name;
    }

    public static void setName(String nm) {
        name = nm;
    }

    public static String getHashalgorithmname() {
        return hashalgorithmname;
    }

    public static void setHashalgorithmname(String hnm) {
        hashalgorithmname = hnm;
        PasswordHelper.setAlgorithmName(hashalgorithmname);
    }

    public static int getHashiterations() {
        return hashiterations;
    }

    public static void setHashiterations(int hi) {
        hashiterations = hi;
        PasswordHelper.setHashIterations(hashiterations);
    }
    // ----------------setter----------------


    /** 会话ID生成器 **/
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }



    /** 会话Cookie模板 **/
    @Bean
    public Cookie sessionIdCookie() {
        SimpleCookie sessionIdCookie = new SimpleCookie("my.session.id");
        sessionIdCookie.setHttpOnly(true);
        sessionIdCookie.setMaxAge(180000);
        return sessionIdCookie;
    }

    /** 会话DAO **/
    @Bean
    public SessionDAO sessionDAO(SessionIdGenerator sessionIdGenerator) {

        RedisSessionDao dao = new RedisSessionDao();
        dao.setSessionIdGenerator(sessionIdGenerator);

        return dao;
    }

    /** Realm实现 **/
    @Bean
    public Realm userRealm(CredentialsMatcher credentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        userRealm.setCachingEnabled(true);

        userRealm.setAuthenticationCachingEnabled(true);
        userRealm.setAuthenticationCacheName("authenticationCache_up");
        
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setAuthorizationCacheName("authorizationCache");
        log.debug("UserRealm bean 已创建");
        return userRealm;
    }

    public Realm tokenRealm(CredentialsMatcher credentialsMatcher) {
        TokenRealm userRealm = new TokenRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        userRealm.setCachingEnabled(true);

        userRealm.setAuthenticationCachingEnabled(true);
        userRealm.setAuthenticationCacheName("authenticationCache_token");
        
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setAuthorizationCacheName("authorizationCache");
        log.debug("TokenRealm bean 已创建");
        return userRealm;
    }

    /**
     * 缓存管理
     */
    @Bean
    public CacheManager cacheManager() {
        EhCacheManager cache = new EhCacheManager();
        cache.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cache;
    }

    /** 凭证管理器 **/
    @Bean
    public CredentialsMatcher credentialsMatcher(CacheManager cacheManager) {
        RetryLimitHashedCredentialsMatcher credential =
                new RetryLimitHashedCredentialsMatcher(cacheManager);
        credential.setHashAlgorithmName(hashalgorithmname);
        credential.setHashIterations(hashiterations);
        credential.setStoredCredentialsHexEncoded(true);
        log.debug("CredentialsMatcher bean 已创建");
        return credential;
    }

    /** 当用户登陆 拦截器 **/
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter(CacheManager cacheManager,
            SessionManager sessionManager) {
        KickoutSessionControlFilter filter = new KickoutSessionControlFilter();
        filter.setCacheManager(cacheManager);
        filter.setSessionManager(sessionManager);
        filter.setKickoutAfter(false);// true:剔除后登陸的，false:剔除前面登陸的
        filter.setMaxSession(1);// 一个账户最多多少个人登陆
        log.debug("KickoutSessionControlFilter bean 已创建");
        return filter;
    }


    /** 过滤器 **/
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager,
            KickoutSessionControlFilter kickfilter) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setUnauthorizedUrl("/");

        // 配置拦截器信息
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("kickout", kickfilter);
        shiroFilter.setFilters(filters);

        // 配置拦截权限
        // anon 不适用权限拦截
        // logout 登出拦截
        // user
        //
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/static/**", "anon");
        filterMap.put("/asserts/**", "anon");
        filterMap.put("/errorPage/**", "anon");

        filterMap.put("/login/**", "anon");
        filterMap.put("/logout/**", "logout");
        filterMap.put("/view/**", "kickout,user");// view 目录下鉴定权限

        filterMap.put("/**", "kickout,anon");// 其他路径不不做过滤

        shiroFilter.setFilterChainDefinitionMap(filterMap);

        log.debug("ShiroFilter bean 已创建");
        return shiroFilter;
    }

    /** 会话管理器 **/
    @Bean("sessionManager")
    public SessionManager sessionManager(SessionDAO sessionDAO, Cookie sessionIdCookie) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        // 设置session过期时间，默认为30分钟
        sessionManager.setGlobalSessionTimeout(GLOBAL_SESSION_TIMEOUT);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionValidationInterval(SESSION_VALIDATION_INTERVAL); // 清理回话频率,默认按小时

        // 将session信息写入url中. 可以预防浏览器cookie禁用的情况
        sessionManager.setSessionIdUrlRewritingEnabled(true);

        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie);

        sessionManager.setSessionDAO(sessionDAO);

        log.debug("sessionManager bean 已创建");
        return sessionManager;
    }


    @Bean
    public FilterRegistrationBean<Filter> delegatingFilterProxy() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }


}
