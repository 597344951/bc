package com.zltel.broadcast.common.shiro.realm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.zltel.broadcast.common.util.Constant;
import com.zltel.broadcast.um.bean.SysMenu;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.service.SysMenuService;
import com.zltel.broadcast.um.service.SysUserService;



public class UserRealm extends AuthorizingRealm {
    /** 日志输出对象 **/
    public static final Log logout = LogFactory.getLog(UserRealm.class);

    public static final String REALM_NAME = "UserPassWordRealm";

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        logout.info("查找" + user.getUsername() + " 权限");
        Integer userId = user.getUserId();

        List<String> permsList = null;
        // 系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenu> menuList = sysMenuService.queryForList(null);
            permsList = menuList.stream().map(SysMenu::getPerms).collect(Collectors.toList());
        } else {
            permsList = this.sysUserService.queryAllPerms(userId);
        }
        // 用户权限列表
        Set<String> permsSet =
                permsList.stream().filter(StringUtils::isNotBlank).flatMap(s -> Arrays.stream(s.split(",")))// 多个权限描述字符串
                        .collect(Collectors.toSet());


        Set<String> roles = new HashSet<>(this.sysUserService.queryAllRoles(userId));
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permsSet);

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        logout.info("尝试使用 用户名/密码方式登陆");
        SimpleAuthenticationInfo authenticationInfo = null;
        String username = (String) token.getPrincipal();
        SysUser user = this.sysUserService.selectByUserName(username);
        if (user == null) throw new UnknownAccountException();
        authenticationInfo = new SimpleAuthenticationInfo(user, // 用户名
                user.getPassword(), // 密码
                ByteSource.Util.bytes(user.getSalt()), // salt=username
                getName() // realm name
        );
        return authenticationInfo;
    }

    @Override
    public String getName() {
        return REALM_NAME;
    }



}
