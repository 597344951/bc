package com.zltel.broadcast.um.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.service.SysUserService;

import io.swagger.annotations.ApiOperation;

/**
 * 系统用户控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.9
 */
@RequestMapping(value="/sys/user")
@RestController
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 查询系统用户信息
	 * @param sysUser 条件
	 * @return
	 */
	@RequestMapping(value="/querySysUsers", method=RequestMethod.POST)
	@LogPoint("查询系统用户信息")
	@RequiresPermissions(value = {"sys:user:query"})
	@ApiOperation(value = "查询系统用户信息")
	public R querySysUsers(SysUser sysUser, int pageNum, int pageSize) {
		try {
			return sysUserService.querySysUsers(sysUser, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询系统用户信息失败");
		}
	}
	
	/**
	 * 查询系统用户信息
	 * @param sysUser 条件
	 * @return
	 */
	@RequestMapping(value="/querySysUsersNotPage", method=RequestMethod.POST)
	@LogPoint("查询系统用户信息")
	@RequiresPermissions(value = {"sys:user:query"})
	@ApiOperation(value = "查询系统用户信息")
	public R querySysUsersNotPage(SysUser sysUser) {
		try {
			return sysUserService.querySysUsersNotPage(sysUser);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询系统用户信息失败");
		}
	}
	
	/**
	 * 修改系统用户信息
	 * @param sysUser 要修改的用户
	 * @return
	 */
	@RequestMapping(value="/updateSysUser", method=RequestMethod.POST)
	@LogPoint("修改系统用户信息")
	@RequiresPermissions(value = {"sys:user:update"})
	@ApiOperation(value = "修改系统用户信息")
	public R updateSysUser(SysUser sysUser) {
		try {
			return sysUserService.updateSysUser(sysUser);	//开始修改系统用户信息
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("系统用户信息修改出错。");
		}
	}
	
	/**
	 * 修改系统用户密码
	 * @param sysUser 要修改的用户
	 * @return
	 */
	@RequestMapping(value="/updateSysUserPwd", method=RequestMethod.POST)
	@LogPoint("修改系统用户密码")
	@RequiresPermissions(value = {"sys:user:update"})
	@ApiOperation(value = "修改系统用户密码")
	public R updateSysUserPwd(SysUser sysUser) {
		try {
			return sysUserService.updateSysUserPwd(sysUser);	//开始修改系统用户密码
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("系统用户密码修改出错。");
		}
	}
	
	/**
	 * 批量删除系统用户
	 * @param sysUser 要删除的用户s
	 * @return
	 */
	@RequestMapping(value="/deleteSysUser", method=RequestMethod.POST)
	@LogPoint("批量删除系统用户")
	@RequiresPermissions(value = {"sys:user:delete"})
	@ApiOperation(value = "批量删除系统用户")
	public R deleteSysUser(SysUser sysUser) {
		try {
			return sysUserService.deleteSysUser(sysUser);	//开始删除系统用户信息
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("系统用户删除失败。");
		}
	}
	
	
	/**
	 * 添加系统用户
	 * @param sysUser 要添加的系统用户
	 * @return
	 */
	@RequestMapping(value="/insertSysUser", method=RequestMethod.POST)
	@LogPoint("添加系统用户")
	@RequiresPermissions(value = {"sys:user:insert"})
	@ApiOperation(value = "添加系统用户")
	public R insertSysUser(SysUser sysUser) {
		try {
			return sysUserService.insertSysUser(sysUser);	//开始添加系统用户信息
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("系统用户添加失败。");
		}
	}

	
	
	
	
	
	
	
	
	
	


	public SysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	
	
	
	
	
	
	
	
	
}
