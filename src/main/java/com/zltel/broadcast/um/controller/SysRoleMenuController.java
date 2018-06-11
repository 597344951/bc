package com.zltel.broadcast.um.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.SysRoleMenu;
import com.zltel.broadcast.um.service.SysRoleMenuService;

import io.swagger.annotations.ApiOperation;

/**
 * 系统角色权限控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.13
 */
@RequestMapping(value="/sys/roleMenu")
@RestController
public class SysRoleMenuController extends BaseController {
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	/**
	 * 查询角色权限信息
	 * @param sysRoleMenu 条件
	 * @return
	 */
	@RequestMapping(value="/querySysRoleMenus", method=RequestMethod.POST)
	@LogPoint("查询角色权限信息")
	@RequiresPermissions(value = {"sys:roleMenu:query"})
	@ApiOperation(value = "查询角色权限信息")
	public R querySysRoleMenus(SysRoleMenu sysRoleMenu, int pageNum, int pageSize) {
		try {
			return sysRoleMenuService.querySysRoleMenus(sysRoleMenu, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询角色权限信息失败");
		}
	}
	
	/**
	 * 查询角色权限信息
	 * @param sysRoleMenu 条件
	 * @return
	 */
	@RequestMapping(value="/querySysRoleMenusNotPage", method=RequestMethod.POST)
	@LogPoint("查询角色权限信息")
	@RequiresPermissions(value = {"sys:roleMenu:query"})
	@ApiOperation(value = "查询角色权限信息")
	public R querySysRoleMenusNotPage(SysRoleMenu sysRoleMenu) {
		try {
			return sysRoleMenuService.querySysRoleMenusNotPage(sysRoleMenu);
		} catch (Exception e) {
			return R.error().setMsg("查询角色权限信息失败");
		}
	}

	
	/**
	 * 批量删除权限信息
	 * @param sysRoleMenu 要删除的权限信息
	 * @return
	 */
	@RequestMapping(value="/deleteSysRoleMenu", method=RequestMethod.POST)
	@LogPoint("批量删除权限信息")
	@RequiresPermissions(value = {"sys:roleMenu:delete"})
	@ApiOperation(value = "批量删除权限信息")
	public R deleteSysRoleMenu(SysRoleMenu sysRoleMenu) {
		try {
			return sysRoleMenuService.deleteSysRoleMenu(sysRoleMenu);
		} catch (Exception e) {
			return R.error().setMsg("删除权限信息失败。");
		}
	}
	
	/**
	 * 添加权限信息
	 * @param sysRoleMenu 要添加的权限信息信息
	 * @return
	 */
	@RequestMapping(value="/insertSysRoleMenu", method=RequestMethod.POST)
	@LogPoint("添加权限信息")
	@RequiresPermissions(value = {"sys:roleMenu:insert"})
	@ApiOperation(value = "添加权限信息")
	public R insertSysRoleMenu(SysRoleMenu sysRoleMenu, 
				@RequestParam(required = false, name = "menuIds[]") List<Integer> menuIds) {
		try {
			return sysRoleMenuService.insertSysRoleMenu(sysRoleMenu, menuIds);
		} catch (Exception e) {
			return R.error().setMsg("添加权限信息失败。");
		}
	}
}
