package com.zltel.broadcast.um.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.util.AdminRoleUtil;
import com.zltel.broadcast.um.bean.BaseUserInfo;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.service.PartyUserInfoService;

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
	@Resource
	private PartyUserInfoService partyUserInfoService;
	
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
	
	/**
	 * 得到当前登录用户
	 * @return
	 */
	@RequestMapping(value="/getSignAccount", method=RequestMethod.POST)
	@ApiOperation(value = "得到当前登录账户")
	public R getSignAccount() {
		//用户id
		SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
		
		BaseUserInfo bui = new BaseUserInfo();
		bui.setIdCard(sysUser.getUsername());
		
		return getSignAccountIdCard(bui);
	}
	
	/**
	 * 得到当前登录用户
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getSignAccountIdCard", method=RequestMethod.POST)
	@ApiOperation(value = "得到当前登录账户")
	public R getSignAccountIdCard(BaseUserInfo bui) {
		Map<String, Object> condition = new HashMap<>();
		condition.put("idCard", bui.getIdCard());
		
		R r = null;
		try {
			r = partyUserInfoService.queryPartyUserInfos(condition, 1, 999, false);
		} catch (Exception e) {
			return R.error().setMsg("没有查询到当前登录普通用户信息");
		}
		if (Integer.parseInt(String.valueOf(r.get("code"))) == 200 && ((PageInfo<Map<String, Object>>)r.get("data")) != null) {			
			List<Map<String, Object>> puis = ((PageInfo<Map<String, Object>>)r.get("data")).getList();
			if (puis == null || puis.size() != 1) {		//查询当前用户信息不正确或查询不到
	        	return R.error().setMsg("没有查询到当前登录普通用户信息");
			}
			return R.ok().setData(puis.get(0));
		} else {
			return R.error().setMsg("没有查询到当前登录普通用户信息");
		}
		
	}

}
