package com.zltel.broadcast.common.util;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;

import com.zltel.broadcast.um.bean.SysUser;

/**
 * 登录用户获取工具类
 * 
 * @author wangch
 *
 */
public class LoginUserUtil {

    private LoginUserUtil() {}

    /**
     * 获取当前登录用户
     * 
     * @return 登录页用户信息
     * @throws UnauthenticatedException 未登录异常
     */
    public static SysUser getSysUser() throws UnauthenticatedException {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        if (null == user) throw new UnauthenticatedException();
        return user;
    }
}
