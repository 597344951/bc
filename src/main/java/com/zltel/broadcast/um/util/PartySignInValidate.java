package com.zltel.broadcast.um.util;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.ShiroException;
import org.springframework.stereotype.Component;

import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.dao.SysUserMapper;

/**
 * 党员登录校验
 * @author 张毅
 * @since jdk 1.8.0_172
 * Date: 2018.7.21
 */
@Component
public class PartySignInValidate {
	
	@Resource
    private SysUserMapper sysUserMapper;
	
	public void isFirstSignIn(String userName) throws ShiroException {
		SysUser sysUser = new SysUser();
		sysUser.setUsername(userName);
		List<SysUser> sysUsers = sysUserMapper.querySysUsers(sysUser);
		if (sysUsers != null & sysUsers.size() == 1) {
			sysUser = sysUsers.get(0);
			if (sysUser.getUserType() == 1 && sysUser.getLastSignInTime() == null) {
				throw new PartyFirstSignInError();
			} else {
				return;
			}
		} else {
			return;
		}
	}
}
