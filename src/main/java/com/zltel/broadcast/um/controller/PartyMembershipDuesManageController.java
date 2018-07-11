package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.service.PartyMembershipDuesManageService;

import io.swagger.annotations.ApiOperation;

/**
 * 党费缴纳记录控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.7.6
 */
@RequestMapping(value="/party/pmdm")
@RestController
public class PartyMembershipDuesManageController {
	@Autowired
	private PartyMembershipDuesManageService partyMembershipDuesManageService;
	
	/**
	 * 查询党费缴纳记录
	 * @param conditionMaps 条件
	 * @return
	 */
	@RequestMapping(value="/queryPartyMembershipDues", method=RequestMethod.POST)
	@RequiresPermissions(value = {"party:pmdm:query"})
	@ApiOperation(value = "查询党费缴纳记录")
	public R queryPartyMembershipDues(@RequestParam Map<String, Object> conditionMaps, int pageNum, int pageSize) {
		try {
			return partyMembershipDuesManageService.queryPartyMembershipDues(conditionMaps, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询党费缴纳记录失败");
		}
	}
	
	/**
	 * 查询党费缴纳记录里的党组织
	 * @param conditionMaps 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfoOfPMDM", method=RequestMethod.POST)
	@RequiresPermissions(value = {"party:pmdm:query"})
	@ApiOperation(value = "查询党费缴纳记录里的党组织")
	public R queryOrgInfoOfPMDM(@RequestParam Map<String, Object> conditionMaps, int pageNum, int pageSize) {
		try {
			return partyMembershipDuesManageService.queryOrgInfoOfPMDM(conditionMaps, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询党费缴纳记录里的党组织失败");
		}
	}
	
	/**
	 * 查询此组织的缴费统计
	 * @param conditionMaps 条件
	 * @return
	 */
	@RequestMapping(value="/queryPMDMChartForOrgInfo", method=RequestMethod.POST)
	@RequiresPermissions(value = {"party:pmdm:query"})
	@ApiOperation(value = "查询此组织的缴费统计")
	public R queryPMDMChartForOrgInfo(@RequestParam Map<String, Object> conditionMaps) {
		try {
			return partyMembershipDuesManageService.queryPMDMChartForOrgInfo(conditionMaps);
		} catch (Exception e) {
			return R.error().setMsg("查询此组织的缴费统计失败");
		}
	}
}
