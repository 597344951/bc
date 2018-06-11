package com.zltel.broadcast.common.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zltel.broadcast.common.util.PasswordHelper;

/**
 * 模拟 用户以token登陆
 */
public class TokenRealm extends UserRealm {

    public static final Logger log = LoggerFactory.getLogger(TokenRealm.class);

    public static final String REALM_NAME = "TokenRealm";

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        log.info("尝试使用Token验证的方式登陆.");

        // 假设用户 输入 用户名,token值
        String username = "develop";// (String) token.getPrincipal();

        String t = "654321";
        String tks = PasswordHelper.encryptPassword(t,  username);
        // 如果身份认证验证成功，返回一个AuthenticationInfo实现；
        String salt = username;
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, // 用户名
                tks, // 密码
                ByteSource.Util.bytes(salt), // salt=username
                getName() // realm name
        );
        // authenticationInfo = new SimpleAuthenticationInfo(username,ps,getName());

        return authenticationInfo;

    }

    @Override
    public String getName() {
        return REALM_NAME;
    }

}
