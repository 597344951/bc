package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.JoinPartyOrgStep;
import com.zltel.broadcast.um.service.JoinPartyOrgStepService;

import io.swagger.annotations.ApiOperation;

/**
 * 入党执行的相关步骤
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.8.21
 */
@RequestMapping(value="/join/step")
@RestController
public class JoinPartyOrgStepController extends BaseController {
	@Autowired
	private JoinPartyOrgStepService joinPartyOrgStepService;
	
	/**
	 * 查询申请入党人员进行步骤
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryJoinPartyOrgSteps", method=RequestMethod.POST)
	@ApiOperation(value = "查询申请入党人员进行步骤")
	public R queryJoinPartyOrgSteps(@RequestParam Map<String, Object> conditions) {
		try {
			return joinPartyOrgStepService.queryJoinPartyOrgSteps(conditions);
		} catch (Exception e) {
			return R.error().setMsg("查询申请入党人员进行步骤失败");
		}
	}
	
	/**
	 * 查询申请入党人员进行步骤
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryUserJoinPartyOrgSteps", method=RequestMethod.POST)
	@ApiOperation(value = "查询申请入党人员进行步骤")
	public R queryUserJoinPartyOrgSteps(@RequestParam Map<String, Object> conditions) {
		try {
			return joinPartyOrgStepService.queryUserJoinPartyOrgSteps(conditions);
		} catch (Exception e) {
			return R.error().setMsg("查询申请入党人员进行步骤失败");
		}
	}
	
	/**
     * 变更此步骤的信息
     * @param jpos
     * @return
     */
	@RequestMapping(value="/updateJoinPartyOrgSteps", method=RequestMethod.POST)
	@ApiOperation(value = "变更此步骤的信息")
	public R updateJoinPartyOrgSteps(JoinPartyOrgStep jpos, @RequestParam("orgRltDuty") String orgRltDuty, @RequestParam("orgId") String orgId) {
		try {
			return joinPartyOrgStepService.updateJoinPartyOrgSteps(jpos, orgRltDuty, orgId);
		} catch (Exception e) {
			return R.error().setMsg("变更此步骤的信息失败");
		}
	}
}
