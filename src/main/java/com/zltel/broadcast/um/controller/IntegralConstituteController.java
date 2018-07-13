package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.IntegralConstitute;
import com.zltel.broadcast.um.service.IntegralConstituteService;

import io.swagger.annotations.ApiOperation;

/**
 * 组织积分结构
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.7.11
 */
@RequestMapping(value="/org/ic")
@RestController
public class IntegralConstituteController {
	@Autowired
	private IntegralConstituteService integralConstituteService;
	
	/**
	 * 查询组织信息生成树
	 * @param organizationInformation 条件
	 * @return
	 */
	@RequestMapping(value="/insertIntegralConstitute", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:insert"})
	@ApiOperation(value = "查询组织信息生成树")
	public R insertIntegralConstitute(IntegralConstitute ic) {
		try {
			return integralConstituteService.insertIntegralConstitute(ic);
		} catch (Exception e) {
			return R.error().setMsg("查询组织信息生成树失败");
		}
	}
	
	/**
	 * 查询拥有积分结构的组织
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfoForIc", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:insert"})
	@ApiOperation(value = "查询拥有积分结构的组织")
	public R queryOrgInfoForIc(@RequestParam Map<String, Object> conditions, int pageNum, int pageSize) {
		try {
			return integralConstituteService.queryOrgInfoForIc(conditions, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询拥有积分结构的组织失败");
		}
	}
	
	/**
	 * 查询该组织拥有的党员，仅为党员积分功能服务
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryPartyUserInfoAndIcInfo", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:insert"})
	@ApiOperation(value = "查询该组织拥有的党员，仅为党员积分功能服务")
	public R queryPartyUserInfoAndIcInfo(@RequestParam Map<String, Object> conditions, int pageNum, int pageSize) {
		try {
			return integralConstituteService.queryPartyUserInfoAndIcInfo(conditions, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询该组织拥有的党员失败");
		}
	}
}
