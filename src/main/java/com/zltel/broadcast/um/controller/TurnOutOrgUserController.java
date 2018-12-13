package com.zltel.broadcast.um.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationRelation;
import com.zltel.broadcast.um.bean.TurnOutOrgUser;
import com.zltel.broadcast.um.service.TurnOutOrgUserService;

import io.swagger.annotations.ApiOperation;

/**
 * 申请组织关系转移党员
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.12.4
 */
@RequestMapping(value="/toou/user")
@RestController
public class TurnOutOrgUserController {
	@Autowired
	private TurnOutOrgUserService turnOutOrgUserService;
	
	/**
	 * 申请组织关系转移-选择组织
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/insertToou", method=RequestMethod.POST)
	@ApiOperation(value = "申请组织关系转移-选择组织")
	public R insertToou(TurnOutOrgUser toou) {
		try {
			return turnOutOrgUserService.insertToou(toou);
		} catch (Exception e) {
			return R.error().setMsg("系统出错");
		}
	}
	
	/**
	 * 查询组织关系转移的人员
	 * @param jpop 条件
	 * @return
	 */
	@RequestMapping(value="/queryTurnOutOrgPartyUsers", method=RequestMethod.POST)
	@ApiOperation(value = "查询组织关系转移的人员")
	public R queryTurnOutOrgPartyUsers(@RequestParam Map<String, Object> conditions, int pageNum, int pageSize) {
		try {
			return turnOutOrgUserService.queryTurnOutOrgPartyUsers(conditions, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询组织关系转移的人员");
		}
	}
	
	
	/**
	 * 增加步骤
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/insertTurnOutOrgPartyStep", method=RequestMethod.POST)
	@RequiresPermissions(value = {"party:user:insert"})
	@ApiOperation(value = "增加步骤")
	public R insertTurnOutOrgPartyStep(@RequestParam("submitDate") String submitDate) {
		try {
			return turnOutOrgUserService.insertTurnOutOrgPartyStep(submitDate);
		} catch (Exception e) {
			return R.error().setMsg("操作失败");
		}
	}
	
	/**
	 * 组织关系审核通过，确认加入此组织
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/insertOrgRelation", method=RequestMethod.POST)
	@ApiOperation(value = "组织关系审核通过，确认加入此组织")
	public R insertOrgRelation(Integer turnOutId, 
			OrganizationRelation organizationRelation, 
			@RequestParam(required = false, name = "orgRltDutys[]") List<Integer> orgRltDutys) {
		try {
			return turnOutOrgUserService.insertOrgRelation(turnOutId, organizationRelation, orgRltDutys);
		} catch (Exception e) {
			return R.error().setMsg("操作失败");
		}
	}
	
	/**
	 * 查询转移步骤
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryToopOtherOrg", method=RequestMethod.POST)
	@ApiOperation(value = "查询转移步骤")
	public R queryToopOtherOrg(@RequestParam Map<String, Object> conditions) {
		try {
			return turnOutOrgUserService.queryToopOtherOrg(conditions);
		} catch (Exception e) {
			return R.error().setMsg("操作失败");
		}
	}
}
