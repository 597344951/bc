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
	 * 修改
	 * @param organizationInformation 条件
	 * @return
	 */
	@RequestMapping(value="/updateIntegralConstitute", method=RequestMethod.POST)
	@ApiOperation(value = "修改积分结构")
	public R updateIntegralConstitute(IntegralConstitute ic) {
		try {
			return integralConstituteService.updateIntegralConstitute(ic);
		} catch (Exception e) {
			return R.error().setMsg("变更失败");
		}
	}
	
	/**
	 * 删除
	 * @param organizationInformation 条件
	 * @return
	 */
	@RequestMapping(value="/deleteIntegralConstitute", method=RequestMethod.POST)
	@ApiOperation(value = "删除积分结构")
	public R deleteIntegralConstitute(IntegralConstitute ic) {
		try {
			return integralConstituteService.deleteIntegralConstitute(ic);
		} catch (Exception e) {
			return R.error().setMsg("删除失败");
		}
	}
	
	/**
	 * 查询组织信息生成树
	 * @param organizationInformation 条件
	 * @return
	 */
	@RequestMapping(value="/insertIntegralConstitute", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:query"})
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
	@RequiresPermissions(value = {"org:ic:query"})
	@ApiOperation(value = "查询拥有积分结构的组织")
	public R queryOrgInfoForIc(@RequestParam Map<String, Object> conditions, int pageNum, int pageSize) {
		try {
			return integralConstituteService.queryOrgInfoForIc(conditions, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询拥有积分结构的组织失败");
		}
	}
	
	/**
	 * 查询拥有积分结构的组织
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfoForIcNotPage", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:query"})
	@ApiOperation(value = "查询拥有积分结构的组织")
	public R queryOrgInfoForIcNotPage(@RequestParam Map<String, Object> conditions) {
		try {
			return integralConstituteService.queryOrgInfoForIcNotPage(conditions);
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
	@RequiresPermissions(value = {"org:ic:query"})
	@ApiOperation(value = "查询该组织拥有的党员，仅为党员积分功能服务")
	public R queryPartyUserInfoAndIcInfo(@RequestParam Map<String, Object> conditions, int pageNum, int pageSize) {
		try {
			return integralConstituteService.queryPartyUserInfoAndIcInfo(conditions, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询该组织拥有的党员失败");
		}
	}
	
	/**
	 * 得到积分的子节点信息
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgIntegralInfo", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:query"})
	@ApiOperation(value = "得到积分的子节点信息")
	public R queryOrgIntegralInfo(@RequestParam Map<String, Object> conditions) {
		try {
			return integralConstituteService.queryOrgIntegralInfo(conditions);
		} catch (Exception e) {
			return R.error().setMsg("得到积分的子节点信息失败");
		}
	}
	
	/**
	 * 得到积分的子节点信息
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgIntegralInfo_IcType", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:query"})
	@ApiOperation(value = "得到积分的子节点信息")
	public R queryOrgIntegralInfo_IcType(@RequestParam Map<String, Object> conditions) {
		try {
			return integralConstituteService.queryOrgIntegralInfo_IcType(conditions);
		} catch (Exception e) {
			return R.error().setMsg("得到积分的子节点信息失败");
		}
	}
	
	/**
	 * 查询组织积分信息
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgIntegralConstituteInfo", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:query"})
	@ApiOperation(value = "查询组织积分信息")
	public R queryOrgIntegralConstituteInfo(@RequestParam Map<String, Object> conditions) {
		try {
			return integralConstituteService.queryOrgIntegralConstituteInfo(conditions);
		} catch (Exception e) {
			return R.error().setMsg("查询组织积分信息失败");
		}
	}
	
	/**
	 * 修改组织积分信息
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/updateOrgIntegralConstituteInfo", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:update"})
	@ApiOperation(value = "修改组织积分信息")
	public R updateOrgIntegralConstituteInfo(@RequestParam Map<String, Object> conditions) {
		try {
			return integralConstituteService.updateOrgIntegralConstituteInfo(conditions);
		} catch (Exception e) {
			return R.error().setMsg("修改组织积分信息失败");
		}
	}
	
	/**
	 * 填写积分验证
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/integralValidator", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:insert"})
	@ApiOperation(value = "填写积分验证")
	public R integralValidator(@RequestParam Map<String, Object> conditions) {
		try {
			return integralConstituteService.integralValidator(conditions);
		} catch (Exception e) {
			return R.error().setMsg("填写积分验证失败");
		}
	}
}
