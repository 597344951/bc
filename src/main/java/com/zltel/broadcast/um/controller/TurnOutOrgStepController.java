package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.TurnOutOrgStep;
import com.zltel.broadcast.um.service.TurnOutOrgStepService;

import io.swagger.annotations.ApiOperation;

/**
 * 党员组织关系转移执行的相关步骤
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.12.3
 */
@RequestMapping(value="/turn_out/step")
@RestController
public class TurnOutOrgStepController {
	@Autowired
	private TurnOutOrgStepService turnOutOrgStepService;
	
	/**
	 * 查询申请组织关系转移人员进行步骤
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryUserTurnOutOrgSteps", method=RequestMethod.POST)
	@ApiOperation(value = "查询申请组织关系转移人员进行步骤")
	public R queryUserTurnOutOrgSteps(@RequestParam Map<String, Object> conditions) {
		try {
			return turnOutOrgStepService.queryUserTurnOutOrgSteps(conditions);
		} catch (Exception e) {
			return R.error().setMsg("查询申请组织关系转移人员进行步骤失败");
		}
	}
	
	/**
     * 变更此步骤的信息
     * @param jpos
     * @return
     */
	@RequestMapping(value="/updateTurnOutOrgPartySteps", method=RequestMethod.POST)
	@ApiOperation(value = "变更此步骤的信息")
	public R updateTurnOutOrgPartySteps(@RequestParam boolean haveThisOrg, TurnOutOrgStep toos) {
		try {
			return turnOutOrgStepService.updateTurnOutOrgPartySteps(haveThisOrg, toos);
		} catch (Exception e) {
			return R.error().setMsg("变更此步骤的信息失败");
		}
	}
	
	/**
	 * 补充材料
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/supplementFiles", method=RequestMethod.POST)
	@ApiOperation(value = "补充材料")
	public R supplementFiles(@RequestParam("condition") String condition) {
		try {
			return turnOutOrgStepService.supplementFiles(condition);
		} catch (Exception e) {
			return R.error().setMsg("操作失败");
		}
	}
}
