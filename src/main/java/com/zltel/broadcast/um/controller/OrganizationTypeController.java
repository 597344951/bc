package com.zltel.broadcast.um.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationType;
import com.zltel.broadcast.um.service.OrganizationTypeService;

import io.swagger.annotations.ApiOperation;

/**
 * 组织类型控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.13
 */
@RequestMapping(value="/org/type")
@RestController
public class OrganizationTypeController extends BaseController {
	@Autowired
	private OrganizationTypeService organizationTypeService;
	
	/**
	 * 查询组织类型
	 * @param organizationType 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgTypes", method=RequestMethod.POST)
	@LogPoint("查询组织类型")
	@RequiresPermissions(value = {"org:type:query"})
	@ApiOperation(value = "查询组织类型")
	public R queryOrgTypes(OrganizationType organizationType, int pageNum, int pageSize) {
		try {
			return organizationTypeService.queryOrgTypes(organizationType, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询组织类型失败");
		}
	}
	
	/**
	 * 查询组织类型
	 * @param organizationType 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgTypesNotPage", method=RequestMethod.POST)
	@LogPoint("查询组织类型")
	@RequiresPermissions(value = {"org:type:query"})
	@ApiOperation(value = "查询组织类型")
	public R queryOrgTypesNotPage(OrganizationType organizationType) {
		try {
			return organizationTypeService.queryOrgTypesNotPage(organizationType);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询组织类型失败");
		}
	}
}
