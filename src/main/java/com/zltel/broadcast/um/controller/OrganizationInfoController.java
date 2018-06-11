package com.zltel.broadcast.um.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationInfo;
import com.zltel.broadcast.um.service.OrganizationInfoService;

import io.swagger.annotations.ApiOperation;

/**
 * 组织信息控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.13
 */
@RequestMapping(value="/org/info")
@RestController
public class OrganizationInfoController extends BaseController {
	@Autowired
	private OrganizationInfoService organizationInfoService;
	
	/**
	 * 查询组织信息
	 * @param organizationInfo 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfos", method=RequestMethod.POST)
	@LogPoint("查询组织信息")
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询组织信息")
	public R queryOrgInfos(OrganizationInfo organizationInfo) {
		try {
			return organizationInfoService.queryOrgInfos(organizationInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询组织信息失败");
		}
	}
	
	/**
	 * 查询组织信息生成树
	 * @param organizationInfo 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfosToTree", method=RequestMethod.POST)
	@LogPoint("查询组织信息")
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询组织信息")
	public R queryOrgInfosToTree() {
		try {
			return organizationInfoService.queryOrgInfosToTree();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询组织信息失败");
		}
	}
	
	/**
	 * 查询组织信息
	 * @param organizationInfo 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfosNotPage", method=RequestMethod.POST)
	@LogPoint("查询组织信息")
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询组织信息")
	public R queryOrgInfosNotPage(OrganizationInfo organizationInfo) {
		try {
			return organizationInfoService.queryOrgInfosNotPage(organizationInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询组织信息失败");
		}
	}
	
	/**
	 * 修改组织信息
	 * @param organizationInfo 要修改的组织信息
	 * @return
	 */
	@RequestMapping(value="/updateOrgInfo", method=RequestMethod.POST)
	@LogPoint("修改组织信息")
	@RequiresPermissions(value = {"org:info:update"})
	@ApiOperation(value = "修改组织信息")
	public R updateOrgInfo(OrganizationInfo organizationInfo) {
		try {
			return organizationInfoService.updateOrgInfo(organizationInfo);	//开始修改系统用户信息
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("组织信息修改出错。");
		}
	}
	
	/**
	 * 批量删除组织信息
	 * @param organizationInfo 要删除的组织信息
	 * @return
	 */
	@RequestMapping(value="/deleteOrgInfo", method=RequestMethod.POST)
	@LogPoint("批量删除组织信息")
	@RequiresPermissions(value = {"org:info:delete"})
	@ApiOperation(value = "批量删除组织信息")
	public R deleteOrgInfo(OrganizationInfo organizationInfo) {
		try {
			return organizationInfoService.deleteOrgInfo(organizationInfo);	//开始删除组织信息
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("组织信息删除失败。");
		}
	}
	
	
	/**
	 * 添加组织信息
	 * @param organizationInfo 要添加的组织信息
	 * @return
	 */
	@RequestMapping(value="/insertOrgInfo", method=RequestMethod.POST)
	@LogPoint("添加组织信息")
	@RequiresPermissions(value = {"org:info:insert"})
	@ApiOperation(value = "添加组织信息")
	public R insertOrgInfo(OrganizationInfo organizationInfo) {
		try {
			return organizationInfoService.insertOrgInfo(organizationInfo);	//开始添加组织信息
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("组织信息添加失败。");
		}
	}
}
