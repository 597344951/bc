package com.zltel.broadcast.um.util;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.util.AdminRoleUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 得到当前登录账户的类型（平台管理员/组织管理员/个人）
 * @author 张毅
 * @since jdk 1.8.0_172
 * time：2018.7.24
 */
@RequestMapping(value="/siat")
@RestController
public class SignInAccountType {
	/**
	 * 得到当前登录账户的类型
	 * @param academicDegreeLevel 条件
	 * @return
	 */
	@RequestMapping(value="/getSignInAccountType", method=RequestMethod.POST)
	@ApiOperation(value = "得到当前登录账户的类型")
	public R getSignInAccountType() {
        if (AdminRoleUtil.isPlantAdmin()) {	//如果是平台管理员
        	return R.ok().setData("plant_admin");
        } else if (AdminRoleUtil.isOrgAdmin()) {	//如果是组织管理员
        	return R.ok().setData("org_admin");
        } else {	//个人用户，即党员
        	return R.ok().setData("party_role");
        }
	}

}
