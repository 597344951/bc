package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationJoinProcess;
import com.zltel.broadcast.um.service.OrganizationJoinProcessService;

import io.swagger.annotations.ApiOperation;

/**
 * 组织自定义流程
 * @author 张毅
 * @since jdk 1.8.0_192
 * data: 2018.11.22
 *
 */
@RequestMapping(value="org/process")
@RestController
public class OrganizationJoinProcessController {
	@Autowired
	private OrganizationJoinProcessService organizationJoinProcessService;
	
	/**
	 * 增加组织流程
	 * @param condition 条件
	 * @return
	 */
	@RequestMapping(value="/insertOrganizationJoinProcess", method=RequestMethod.POST)
	@LogPoint("增加组织流程")
	@ApiOperation(value = "增加组织流程")
	public R insertOrganizationJoinProcess(@RequestParam Map<String, Object> condition) {
		try {
			return organizationJoinProcessService.insertOrganizationJoinProcess(condition);
		} catch (Exception e) {
			return R.error().setMsg("添加失败");
		}
	}
	
	/**
	 * 查询组织流程
	 * @param condition 条件
	 * @return
	 */
	@RequestMapping(value="/queryOjp", method=RequestMethod.POST)
	@LogPoint("查询组织流程")
	@ApiOperation(value = "查询组织流程")
	public R queryOjp(OrganizationJoinProcess ojp) {
		try {
			return organizationJoinProcessService.queryOjp(ojp);
		} catch (Exception e) {
			return R.error().setMsg("查询失败");
		}
	}
}
