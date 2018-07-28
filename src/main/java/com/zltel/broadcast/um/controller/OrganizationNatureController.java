package com.zltel.broadcast.um.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationNature;
import com.zltel.broadcast.um.service.OrganizationNatureService;

import io.swagger.annotations.ApiOperation;

/**
 * 组织性质控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.7.4
 */
@RequestMapping(value="/org/nature")
@RestController
public class OrganizationNatureController {
	@Autowired
	private OrganizationNatureService organizationNatureService;
	
	/**
	 * 查询组织性质
	 * @param organizationNature 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgNaturesNotPage", method=RequestMethod.POST)
	@LogPoint("查询组织性质")
	@ApiOperation(value = "查询组织性质")
	public R queryOrgNaturesNotPage(OrganizationNature organizationNature) {
		try {
			return organizationNatureService.queryOrgNaturesNotPage(organizationNature);
		} catch (Exception e) {
			return R.error().setMsg("查询组织性质失败");
		}
	}
}
