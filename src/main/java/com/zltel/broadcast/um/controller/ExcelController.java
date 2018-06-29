package com.zltel.broadcast.um.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyUser;
import com.zltel.broadcast.um.service.ExcelService;

import io.swagger.annotations.ApiOperation;

/**
 * 导入excel
 * @author 张毅
 * @since jdk:1.8.0_172
 * Date: 2018.5.24
 */
@RequestMapping(value="/excel")
@RestController
public class ExcelController extends BaseController{
	
	@Autowired
	private ExcelService excelService;
	
	/**
	 * 批量导入党员信息
	 * @param baseUser 条件
	 * @return
	 */
	@RequestMapping(value="/importPartyUsersExcel", method=RequestMethod.POST)
	@LogPoint("批量导入党员信息")
	@RequiresPermissions(value = {"party:user:insert"})
	@ApiOperation(value = "批量导入党员信息")
	public R importPartyUsersExcel(HttpServletResponse response, @RequestParam MultipartFile file) {
		try {
			return excelService.importPartyUsersExcel(response, file);
		} catch (Exception e) {
			return R.error().setMsg("批量导入党员信息失败");
		}
	}
	
	/**
	 * 校验错误信息下载
	 * @param response 条件
	 * @return
	 */
	@RequestMapping(value="/downloadValidataMsg", method=RequestMethod.GET)
	@LogPoint("校验错误信息下载")
	@RequiresPermissions(value = {"party:user:insert"})
	@ApiOperation(value = "校验错误信息下载")
	public R downloadValidataMsg(HttpServletResponse response) {
		try {
			return excelService.downloadValidataMsg(response);
		} catch (Exception e) {
			return R.error().setMsg("错误信息下载失败");
		}
	}
	
	/**
	 * 导出党员信息
	 * @param response 条件
	 * @return
	 */
	@RequestMapping(value="/exportPartyUsersExcel", method=RequestMethod.GET)
	@LogPoint("导出党员信息")
	@RequiresPermissions(value = {"party:user:insert"})
	@ApiOperation(value = "导出党员信息")
	public R exportPartyUsersExcel(HttpServletResponse response, PartyUser partyUser) {
		try {
			excelService.exportPartyUsersExcel(response, partyUser);
			return R.ok().setMsg("下载成功");
		} catch (Exception e) {
			return R.error().setMsg("错误信息下载失败");
		}
	}
	
	
	/**
	 * 下载党员导入excel格式示例
	 * @param baseUser 条件
	 * @return
	 */
	@RequestMapping(value="/exportPartyUsersExcelExample", method=RequestMethod.GET)
	@LogPoint("下载党员导入excel格式示例")
	@RequiresPermissions(value = {"party:user:insert"})
	@ApiOperation(value = "下载党员导入excel格式示例")
	public void exportPartyUsersExcelExample(HttpServletResponse response) {
		try {
			excelService.exportPartyUsersExcelExample(response);
		} catch (Exception e) {
			logout.error(e.getMessage());
		} 
	}
	
	
	
}
