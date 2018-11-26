package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.DeedsUser;
import com.zltel.broadcast.um.service.DeedsUserService;

import io.swagger.annotations.ApiOperation;

/**
 * 用户事迹
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.8.3
 */
@RequestMapping(value="/user/duc")
@RestController
public class DeedsUserController {
	@Autowired
	private DeedsUserService deedsUserService;
	
	/**
	 * 查询用户事迹
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryDeedsUsers", method=RequestMethod.POST)
	@RequiresPermissions(value = {"base:user:query"})
	@ApiOperation(value = "查询用户事迹")
	public R queryDeedsUsers(Map<String, Object> conditions) {
		try {
			return deedsUserService.queryDeedsUsers(conditions);
		} catch (Exception e) {
			return R.error().setMsg("查询用户事迹失败");
		}
	}
	
	/**
	 * 添加
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/insertDeedsUser", method=RequestMethod.POST)
	@RequiresPermissions(value = {"base:user:insert"})
	@ApiOperation(value = "添加事迹")
	public R insertDeedsUser(@RequestParam Map<String, Object> conditions) {
		try {
			return deedsUserService.insertDeedsUser(conditions);
		} catch (Exception e) {
			return R.error().setMsg("添加失败");
		}
	}
	
	/**
	 * 补充事迹
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/insertSupplyDeedsUser", method=RequestMethod.POST)
	@RequiresPermissions(value = {"base:user:insert"})
	@ApiOperation(value = "补充事迹")
	public R insertSupplyDeedsUser(@RequestParam Map<String, Object> conditions) {
		try {
			return deedsUserService.insertSupplyDeedsUser(conditions);
		} catch (Exception e) {
			return R.error().setMsg("添加失败");
		}
	}
	
	/**
	 * 删除事迹
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/deleteDeedsUser", method=RequestMethod.POST)
	@RequiresPermissions(value = {"base:user:delete"})
	@ApiOperation(value = "删除事迹")
	public R deleteDeedsUser(DeedsUser du) {
		try {
			return deedsUserService.deleteDeedsUser(du);
		} catch (Exception e) {
			return R.error().setMsg("删除失败");
		}
	}
	
	/**
	 * 修改事迹
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/updateDeedsUser", method=RequestMethod.POST)
	@RequiresPermissions(value = {"base:user:update"})
	@ApiOperation(value = "修改事迹")
	public R updateDeedsUser(@RequestParam Map<String, Object> conditions) {
		try {
			return deedsUserService.updateDeedsUser(conditions);
		} catch (Exception e) {
			return R.error().setMsg("修改失败");
		}
	}
}
