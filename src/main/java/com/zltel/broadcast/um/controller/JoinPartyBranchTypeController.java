package com.zltel.broadcast.um.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.JoinPartyBranchType;
import com.zltel.broadcast.um.service.JoinPartyBranchTypeService;

import io.swagger.annotations.ApiOperation;

/**
 * 加入党支部方式
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.31
 */
@RequestMapping(value="/jpbt")
@RestController
public class JoinPartyBranchTypeController {
	@Autowired
	private JoinPartyBranchTypeService joinPartyBranchTypeService;
	
	/**
	 * 查询加入党支部方式
	 * @param joinPartyBranchType 条件
	 * @return
	 */
	@RequestMapping(value="/queryJoinPartyBranchTypes", method=RequestMethod.POST)
	@LogPoint("查询加入党支部方式")
	@ApiOperation(value = "查询加入党支部方式")
	public R queryJoinPartyBranchTypes(JoinPartyBranchType joinPartyBranchType) {
		try {
			return joinPartyBranchTypeService.queryJoinPartyBranchTypes(joinPartyBranchType);
		} catch (Exception e) {
			return R.error().setMsg("查询加入党支部方式信息失败");
		}
	}
}
