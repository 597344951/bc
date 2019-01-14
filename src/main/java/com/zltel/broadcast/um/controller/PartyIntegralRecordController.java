package com.zltel.broadcast.um.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyIntegralRecord;
import com.zltel.broadcast.um.service.PartyIntegralRecordService;

import io.swagger.annotations.ApiOperation;

/**
 * 党员积分记录
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.7.12
 */
@RequestMapping(value="/party/integral")
@RestController
public class PartyIntegralRecordController {
	@Autowired
	private PartyIntegralRecordService partyIntegralRecordService;
	
	/**
	 * 查询用户积分变更轨迹
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryUserIntegralChangeTrajectory", method=RequestMethod.POST)
	@ApiOperation(value = "查询用户积分变更轨迹")
	public R queryUserIntegralChangeTrajectory(@RequestParam Map<String, Object> conditions) {
		try {
			return R.ok().setData(partyIntegralRecordService.queryUserIntegralChangeTrajectory(conditions));
		} catch (Exception e) {
			return R.error().setMsg("查询用户积分变更轨迹");
		}
	}
	
	/**
	 * 查询积分记录
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryPartyIntegralRecords", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:pirc:query"})
	@ApiOperation(value = "查询积分记录")
	public R queryPartyIntegralRecords(@RequestParam Map<String, Object> conditions, int pageNum, int pageSize) {
		try {
			return partyIntegralRecordService.queryPartyIntegralRecords(conditions, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询积分记录失败");
		}
	}
	
	/**
	 * 添加积分变更记录
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/insertPartyUserIntegralRecord", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:pirc:insert"})
	@ApiOperation(value = "添加积分变更记录")
	public R insertPartyUserIntegralRecord(PartyIntegralRecord pir) {
		try {
			return partyIntegralRecordService.insertPartyUserIntegralRecord(pir);
		} catch (Exception e) {
			return R.error().setMsg("添加积分变更记录失败");
		}
	}
	
	/**
	 * 下载积分记录明细导入excel格式示例
	 * @param response 条件
	 * @return
	 */
	@RequestMapping(value="/exportIntegralExcelExample", method=RequestMethod.GET)
	@ApiOperation(value = "下载积分记录明细导入excel格式示例")
	public void exportIntegralExcelExample(HttpServletResponse response) {
		try {
			partyIntegralRecordService.exportIntegralExcelExample(response);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 批量导入积分记录明细
	 * @param response 条件
	 * @return
	 */
	@RequestMapping(value="/importIntegralExcel", method=RequestMethod.POST)
	@ApiOperation(value = "批量导入积分记录明细")
	public R importIntegralExcel(HttpServletResponse response, MultipartFile file) {
		try {
			return partyIntegralRecordService.importIntegralExcel(response, file);
		} catch (Exception e) {
			return R.error().setMsg("导入失败");
		}
	}
}
