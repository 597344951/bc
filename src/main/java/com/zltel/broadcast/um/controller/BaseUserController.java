package com.zltel.broadcast.um.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.BaseUser;
import com.zltel.broadcast.um.service.BaseUserService;

import io.swagger.annotations.ApiOperation;

/**
 * 基础用户控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.11
 */
@RequestMapping(value="/base/user")
@RestController
public class BaseUserController extends BaseController {
	@Autowired
	private BaseUserService baseUserService;
	
	/**
	 * 查询基础用户信息
	 * @param baseUser 条件
	 * @return
	 */
	@RequestMapping(value="/queryBaseUsers", method=RequestMethod.POST)
	@LogPoint("查询基础用户信息")
	@RequiresPermissions(value = {"base:user:query"})
	@ApiOperation(value = "查询用户基本信息")
	public R queryBaseUsers(BaseUser baseUser, int pageNum, int pageSize) {
		try {
			//ValidatorUtils.validateEntity(baseUser);
			return baseUserService.queryBaseUsers(baseUser, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询基础用户信息失败");
		}
	}
	
	/**
	 * 查询基础用户信息
	 * @param baseUser 条件
	 * @return
	 */
	@RequestMapping(value="/queryBaseUsersNotPage", method=RequestMethod.POST)
	@LogPoint("查询基础用户信息")
	@RequiresPermissions(value = {"base:user:query"})
	@ApiOperation(value = "查询用户基本信息")
	public R queryBaseUsersNotPage(BaseUser baseUser) {
		try {
			//ValidatorUtils.validateEntity(baseUser);
			return baseUserService.queryBaseUsersNotPage(baseUser);
		} catch (Exception e) {
			return R.error().setMsg("查询基础用户信息失败");
		}
	}
	
	/**
	 * 修改基础用户信息
	 * @param baseUser 要修改的基础用户
	 * @return
	 */
	@RequestMapping(value="/updateBaseUser", method=RequestMethod.POST)
	@LogPoint("修改基础用户信息")
	@RequiresPermissions(value = {"base:user:update"})
	@ApiOperation(value = "修改基础用户信息")
	public R updateBaseUser(BaseUser baseUser) {
		try {
			return baseUserService.updateBaseUser(baseUser);
		} catch (Exception e) {
			return R.error().setMsg("基础用户信息修改出错。");
		}
	}
	
	/**
	 * 批量删除基础用户
	 * @param baseUser 要删除的基础用户
	 * @return
	 */
	@RequestMapping(value="/deleteBaseUser", method=RequestMethod.POST)
	@LogPoint("批量删除基础用户用户")
	@RequiresPermissions(value = {"base:user:delete"})
	@ApiOperation(value = "批量删除基础用户用户")
	public R deleteBaseUser(BaseUser baseUser) {
		try {
			return baseUserService.deleteBaseUser(baseUser);
		} catch (Exception e) {
			return R.error().setMsg("删除基础用户失败。");
		}
	}
	
	/**
	 * 添加基础用户
	 * @param baseUser 要添加的基础用户信息
	 * @return
	 */
	@RequestMapping(value="/insertBaseUser", method=RequestMethod.POST)
	@LogPoint("添加基础用户")
	@RequiresPermissions(value = {"base:user:insert"})
	@ApiOperation(value = "添加基础用户")
	public R insertBaseUser(BaseUser baseUser) {
		try {
			return baseUserService.insertBaseUser(baseUser);
		} catch (Exception e) {
			return R.error().setMsg("添加基础用户失败。");
		}
	}

	
	
	
	
	
	public BaseUserService getBaseUserService() {
		return baseUserService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}
}
