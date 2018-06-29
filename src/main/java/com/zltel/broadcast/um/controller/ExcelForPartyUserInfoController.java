package com.zltel.broadcast.um.controller;

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

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyUserInfo;
import com.zltel.broadcast.um.service.ExcelForPartyUserInfoService;

import io.swagger.annotations.ApiOperation;

/**
 * 党员信息excel操作
 * @author 张毅
 * @since jdk:1.8.0_172
 * Date: 2018.6.4
 */
@RequestMapping(value="party/user/excel")
@RestController
public class ExcelForPartyUserInfoController {
    
    private static final Logger logout = LoggerFactory.getLogger(ExcelForPartyUserInfoController.class);

	@Autowired
	private ExcelForPartyUserInfoService excelForPartyUserInfoService;
	
	/**
	 * 批量导入党员信息
	 * @param file 条件
	 * @return
	 */
	@RequestMapping(value="/importPartyUserInfosExcel", method=RequestMethod.POST)
	@LogPoint("批量导入党员信息")
	@RequiresPermissions(value = {"party:user:insert"})
	@ApiOperation(value = "批量导入党员信息")
	public R importPartyUserInfosExcel(HttpServletResponse response, @RequestParam MultipartFile file) {
		try {
			return excelForPartyUserInfoService.importPartyUserInfosExcel(response, file);
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
	public void downloadValidataMsg(HttpServletResponse response) {
		try {
			excelForPartyUserInfoService.downloadValidataMsg(response);
		} catch (Exception e) {
			logout.error(e.getMessage());
		}
	}
	
	/**
	 * 导出党员信息
	 * @param response 条件
	 * @return
	 */
	@RequestMapping(value="/exportPartyUserInfosExcel", method=RequestMethod.GET)
	@LogPoint("导出党员信息")
	@RequiresPermissions(value = {"party:user:insert"})
	@ApiOperation(value = "导出党员信息")
	public void exportPartyUserInfosExcel(HttpServletResponse response, PartyUserInfo partyUserInfo) {
		try {
			excelForPartyUserInfoService.exportPartyUserInfosExcel(response, partyUserInfo);
		} catch (Exception e) {
			logout.error(e.getMessage());
		}
	}
	
	
	/**
	 * 下载党员导入excel格式示例
	 * @param baseUser 条件
	 * @return
	 */
	@RequestMapping(value="/exportPartyUserInfosExcelExample", method=RequestMethod.GET)
	@LogPoint("下载党员导入excel格式示例")
	@RequiresPermissions(value = {"party:user:insert"})
	@ApiOperation(value = "下载党员导入excel格式示例")
	public void exportPartyUserInfosExcelExample(HttpServletResponse response) {
		try {
			excelForPartyUserInfoService.exportPartyUserInfosExcelExample(response);
		} catch (Exception e) {
			logout.error(e.getMessage());
		} 
	}
}
