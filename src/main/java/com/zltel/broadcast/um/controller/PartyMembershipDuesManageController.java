package com.zltel.broadcast.um.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	private static final Logger logout = LoggerFactory.getLogger(PartyMembershipDuesManageController.class);
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
	 * 查询党费缴纳记录里的党组织
	 * @param conditionMaps 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfoOfPMDMNotPage", method=RequestMethod.POST)
	@RequiresPermissions(value = {"party:pmdm:query"})
	@ApiOperation(value = "查询党费缴纳记录里的党组织")
	public R queryOrgInfoOfPMDMNotPage(@RequestParam Map<String, Object> conditionMaps) {
		try {
			return partyMembershipDuesManageService.queryOrgInfoOfPMDMNotPage(conditionMaps);
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
	
	/**
	 * 下载党费缴纳记录导入excel格式示例
	 * @param response 条件
	 * @return
	 */
	@RequestMapping(value="/exportPMDMExcelExample", method=RequestMethod.GET)
	@RequiresPermissions(value = {"party:pmdm:query"})
	@ApiOperation(value = "下载党费缴纳记录导入excel格式示例")
	public void exportPMDMExcelExample(HttpServletResponse response) {
		try {
			partyMembershipDuesManageService.exportPMDMExcelExample(response);
		} catch (Exception e) {
			logout.error(e.getMessage());
		}
	}
	
	/**
	 * 批量导入党费缴纳记录
	 * @param response 条件
	 * @return
	 */
	@RequestMapping(value="/importPMDMsExcel", method=RequestMethod.POST)
	@RequiresPermissions(value = {"party:pmdm:query"})
	@ApiOperation(value = "批量导入党费缴纳记录")
	public R importPMDMsExcel(HttpServletResponse response, MultipartFile file) {
		try {
			return partyMembershipDuesManageService.importPMDMsExcel(response, file);
		} catch (Exception e) {
			return R.error().setMsg("导入失败");
		}
	}
	
	/**
	 * 批量导出党费缴纳记录
	 * @param response 条件
	 * @return
	 */
	@RequestMapping(value="/exportPMDMExcel", method=RequestMethod.GET)
	@RequiresPermissions(value = {"party:pmdm:query"})
	@ApiOperation(value = "批量导出党费缴纳记录")
	public void exportPMDMExcel(HttpServletResponse response, @RequestParam Map<String, Object> conditionMaps) {
		try {
			partyMembershipDuesManageService.exportPMDMExcel(response, conditionMaps);
		} catch (Exception e) {
			
		}
	}
}
