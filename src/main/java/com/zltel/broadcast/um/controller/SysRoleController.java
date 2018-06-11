package com.zltel.broadcast.um.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.SysRole;
import com.zltel.broadcast.um.service.SysRoleService;

import io.swagger.annotations.ApiOperation;

/**
 * 系统角色控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.12
 */
@RequestMapping(value="/sys/role")
@RestController
public class SysRoleController extends BaseController {
	@Autowired
	private SysRoleService sysRoleService;
	
	/**
	 * 查询系统角色信息
	 * @param sysRole 条件
	 * @return
	 */
	@RequestMapping(value="/querySysRoles", method=RequestMethod.POST)
	@LogPoint("查询系统角色信息")
	@RequiresPermissions(value = {"sys:role:query"})
	@ApiOperation(value = "查询系统角色信息")
	public R querySysRoles(SysRole sysRole, int pageNum, int pageSize) {
		try {
			return sysRoleService.querySysRoles(sysRole, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询系统角色信息失败");
		}
	}
	
	/**
	 * 查询系统角色信息
	 * @param sysRole 条件
	 * @return
	 */
	@RequestMapping(value="/querySysRolesNotPage", method=RequestMethod.POST)
	@LogPoint("查询系统角色信息")
	@RequiresPermissions(value = {"sys:role:query"})
	@ApiOperation(value = "查询系统角色信息")
	public R querySysRolesNotPage(SysRole sysRole) {
		try {
			return sysRoleService.querySysRolesNotPage(sysRole);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询系统角色信息失败");
		}
	}
	
	/**
	 * 修改系统角色信息
	 * @param sysRole 要修改的角色
	 * @return
	 */
	@RequestMapping(value="/updateSysRole", method=RequestMethod.POST)
	@LogPoint("修改系统角色信息")
	@RequiresPermissions(value = {"sys:role:update"})
	@ApiOperation(value = "修改系统角色信息")
	public R updateSysRole(SysRole sysRole) {
		try {
			return sysRoleService.updateSysRole(sysRole);	//开始修改系统角色信息
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("系统角色信息修改出错。");
		}
	}
	
	/**
	 * 批量删除系统角色
	 * @param sysRole 要删除的角色s
	 * @return
	 */
	@RequestMapping(value="/deleteSysRole", method=RequestMethod.POST)
	@LogPoint("批量删除系统角色")
	@RequiresPermissions(value = {"sys:role:delete"})
	@ApiOperation(value = "批量删除系统角色")
	public R deleteSysRole(SysRole sysRole) {
		try {
			return sysRoleService.deleteSysRole(sysRole);	//开始删除系统角色信息
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("系统角色删除失败。");
		}
	}
	
	
	/**
	 * 添加系统角色
	 * @param sysRole 要添加的系统角色
	 * @return
	 */
	@RequestMapping(value="/insertSysRole", method=RequestMethod.POST)
	@LogPoint("添加系统角色")
	@RequiresPermissions(value = {"sys:role:insert"})
	@ApiOperation(value = "添加系统角色")
	public R insertSysRole(SysRole sysRole) {
		try {
			return sysRoleService.insertSysRole(sysRole);	//开始添加系统角色信息
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("系统角色添加失败。");
		}
	}
	
	
	
	
	
	
	
	

	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}

	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}
}
