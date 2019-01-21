package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.SysUserRole;
import com.zltel.broadcast.um.service.SysUserRoleService;

import io.swagger.annotations.ApiOperation;

/**
 * 系统用户角色控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.13
 */
@RequestMapping(value="/sys/userRole")
@RestController
public class SysUserRoleController extends BaseController {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * 查询用户角色信息
	 * @param sysUserRole 条件
	 * @return
	 */
	@RequestMapping(value="/querySysUserRoles", method=RequestMethod.POST)
	@LogPoint("查询用户角色信息")
	@RequiresPermissions(value = {"sys:userRole:query"})
	@ApiOperation(value = "查询用户角色信息")
	public R querySysUserRoles(@RequestParam Map<String, Object> conditions, int pageNum, int pageSize) {
		try {
			return sysUserRoleService.querySysUserRoles(conditions, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询用户角色信息失败");
		}
	}
	
	/**
	 * 查询用户角色信息
	 * @param sysUserRole 条件
	 * @return
	 */
	@RequestMapping(value="/querySysUserRolesNotPage", method=RequestMethod.POST)
	@LogPoint("查询用户角色信息")
	@RequiresPermissions(value = {"sys:userRole:query"})
	@ApiOperation(value = "查询用户角色信息")
	public R querySysUserRolesNotPage(@RequestParam Map<String, Object> conditions) {
		try {
			return sysUserRoleService.querySysUserRolesNotPage(conditions);
		} catch (Exception e) {
			return R.error().setMsg("查询用户角色信息失败");
		}
	}

	
	/**
	 * 批量删除用户角色信息
	 * @param sysUserRole 要删除的用户角色
	 * @return
	 */
	@RequestMapping(value="/deleteSysUserRole", method=RequestMethod.POST)
	@LogPoint("批量删除用户角色信息")
	@RequiresPermissions(value = {"sys:userRole:delete"})
	@ApiOperation(value = "批量删除用户角色信息")
	public R deleteSysUserRole(SysUserRole sysUserRole) {
		try {
			return sysUserRoleService.deleteSysUserRole(sysUserRole);
		} catch (Exception e) {
			return R.error().setMsg("删除用户角色信息失败。");
		}
	}
	
	/**
	 * 添加用户角色信息
	 * @param sysUserRole 要添加的用户角色信息
	 * @return
	 */
	@RequestMapping(value="/insertSysUserRole", method=RequestMethod.POST)
	@LogPoint("添加用户角色信息")
	@RequiresPermissions(value = {"sys:user:role:update"})
	@ApiOperation(value = "添加用户角色信息")
	public R insertSysUserRole(SysUserRole sysUserRole) {
		try {
			return sysUserRoleService.insertSysUserRole(sysUserRole);
		} catch (Exception e) {
			return R.error().setMsg("变更用户角色信息失败。");
		}
	}
	
	/**
	 * 变更内置角色
	 * @param conditions 要变更的需要的信息
	 * @return
	 */
	@RequestMapping(value="/updateInnerManageRols", method=RequestMethod.POST)
	@RequiresPermissions(value = {"sys:userRole:update"})
	@ApiOperation(value = "变更内置角色")
	public R updateInnerManageRols(@RequestParam Map<String, Object> conditions) {
		try {
			return sysUserRoleService.updateInnerManageRols(conditions);
		} catch (Exception e) {
			return R.error().setMsg("变更内置角色失败。");
		}
	}
}
