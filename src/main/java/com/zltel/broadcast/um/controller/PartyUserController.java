package com.zltel.broadcast.um.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyUser;
import com.zltel.broadcast.um.service.PartyUserService;

import io.swagger.annotations.ApiOperation;

/**
 * 党员用户控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.11
 */
@RequestMapping(value="/party/user")
@RestController
public class PartyUserController extends BaseController {
	@Autowired
	private PartyUserService partyUserService;

	/**
	 * 查询党员用户信息
	 * @param partyUser 条件
	 * @return
	 */
	@RequestMapping(value="/queryPartyUsers", method=RequestMethod.POST)
	@LogPoint("查询党员用户信息")
	@RequiresPermissions(value = {"party:user:query"})
	@ApiOperation(value = "查询党员用户信息")
	public R queryPartyUsers(PartyUser partyUser, int pageNum, int pageSize) {
		try {
			return partyUserService.queryPartyUsers(partyUser, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询党员用户信息失败");
		}
	}
	
	/**
	 * 查询党员用户信息
	 * @param partyUser 条件
	 * @return
	 */
	@RequestMapping(value="/queryPartyUsersNotPage", method=RequestMethod.POST)
	@LogPoint("查询党员用户信息")
	@RequiresPermissions(value = {"party:user:query"})
	@ApiOperation(value = "查询党员用户信息")
	public R queryPartyUsersNotPage(PartyUser partyUser) {
		try {
			return partyUserService.queryPartyUsersNotPage(partyUser);
		} catch (Exception e) {
			return R.error().setMsg("查询党员用户信息失败");
		}
	}
	
	/**
	 * 修改党员用户信息
	 * @param partyUser 要修改的党员
	 * @return
	 */
	@RequestMapping(value="/updatePartyUser", method=RequestMethod.POST)
	@LogPoint("修改党员用户信息")
	@RequiresPermissions(value = {"party:user:update"})
	@ApiOperation(value = "修改党员用户信息")
	public R updatePartyUser(PartyUser partyUser) {
		try {
			return partyUserService.updatePartyUser(partyUser);
		} catch (Exception e) {
			return R.error().setMsg("党员用户信息修改出错。");
		}
	}
	
	/**
	 * 批量删除党员
	 * @param partyUser 要删除的党员, 目前只删除一个党员
	 * @return
	 */
	@RequestMapping(value="/deletePartyUser", method=RequestMethod.POST)
	@LogPoint("批量删除党员用户")
	@RequiresPermissions(value = {"party:user:delete"})
	@ApiOperation(value = "批量删除党员用户")
	public R deletePartyUser(PartyUser partyUser) {
		try {
			return partyUserService.deletePartyUser(partyUser);
		} catch (Exception e) {
			return R.error().setMsg("删除党员失败。");
		}
	}
	
	/**
	 * 添加党员
	 * @param partyUser 要添加的党员信息
	 * @return
	 */
	@RequestMapping(value="/insertPartyUser", method=RequestMethod.POST)
	@LogPoint("添加党员用户")
	@RequiresPermissions(value = {"party:user:insert"})
	@ApiOperation(value = "添加党员用户")
	public R insertPartyUser(PartyUser partyUser) {
		try {
			return partyUserService.insertPartyUser(partyUser);
		} catch (Exception e) {
			return R.error().setMsg("添加党员用户失败。");
		}
	}
	
	
	
	
	
	
	
	
	public PartyUserService getPartyUserService() {
		return partyUserService;
	}

	public void setPartyUserService(PartyUserService partyUserService) {
		this.partyUserService = partyUserService;
	}
	
}
