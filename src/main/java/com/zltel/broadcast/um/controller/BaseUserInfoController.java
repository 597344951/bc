package com.zltel.broadcast.um.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.BaseUserInfo;
import com.zltel.broadcast.um.service.BaseUserInfoService;

import io.swagger.annotations.ApiOperation;

/**
 * 基础用户控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.6.4
 */
@RequestMapping(value="/base/userInfo")
@RestController
public class BaseUserInfoController {
	@Autowired
	private BaseUserInfoService baseUserInfoService;
	
	/**
	 * 查询基础用户信息
	 * @param baseUser 条件
	 * @return
	 */
	@RequestMapping(value="/queryBaseUserInfos", method=RequestMethod.POST)
	@LogPoint("查询基础用户信息")
	@RequiresPermissions(value = {"base:user:query"})
	@ApiOperation(value = "查询用户基本信息")
	public R queryBaseUserInfos(BaseUserInfo baseUserInfo, int pageNum, int pageSize) {
		try {
			return baseUserInfoService.queryBaseUserInfos(baseUserInfo, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询基础用户信息失败");
		}
	}
}
