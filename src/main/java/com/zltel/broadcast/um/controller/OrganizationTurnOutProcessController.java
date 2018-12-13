package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.service.OrganizationTurnOutProcessService;

import io.swagger.annotations.ApiOperation;

/**
 * 组织自定义转入流程
 * @author 张毅
 * @since jdk 1.8.0_192
 * data: 2018.12.3
 *
 */
@RequestMapping(value="org/turn_out")
@RestController
public class OrganizationTurnOutProcessController {
	@Autowired
	private OrganizationTurnOutProcessService organizationTurnOutProcessService;
	
	/**
	 * 查询组织转入流程
	 * @param condition 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgTurnOutProcess", method=RequestMethod.POST)
	@LogPoint("查询组织转入流程")
	@ApiOperation(value = "查询组织转入流程")
	public R queryOrgTurnOutProcess(@RequestParam Map<String, Object> condition) {
		try {
			return organizationTurnOutProcessService.queryOrgTurnOutProcess(condition);
		} catch (Exception e) {
			return R.error().setMsg("查询失败");
		}
	}
}
