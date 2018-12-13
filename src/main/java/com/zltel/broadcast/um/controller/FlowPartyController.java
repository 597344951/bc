package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.service.FlowPartyService;

import io.swagger.annotations.ApiOperation;

/**
 * 流动党员
 * @author 张毅
 * @since jdk 1.8.0_192
 * data: 2018.11.19
 *
 */
@RequestMapping(value="flow/party")
@RestController
public class FlowPartyController {
	@Autowired
	private FlowPartyService flowPartyService;
	
	/**
	 * 查询流动党员
	 * @param condition 条件
	 * @return
	 */
	@RequestMapping(value="/queryFlowPartys", method=RequestMethod.POST)
	@LogPoint("查询流动党员")
	@ApiOperation(value = "查询流动党员")
	public R queryFlowPartys(@RequestParam Map<String, Object> condition, int pageNum, int pageSize) {
		try {
			return flowPartyService.queryFlowPartys(condition, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询失败");
		}
	}
	
	/**
	 * 添加流动党员-非本平台
	 * @param condition 条件
	 * @return
	 */
	@RequestMapping(value="/insertFlowPartyUserInfo", method=RequestMethod.POST)
	@LogPoint("添加流动党员-非本平台")
	@ApiOperation(value = "添加流动党员-非本平台")
	public R insertFlowPartyUserInfo(@RequestParam Map<String, Object> flowPartyUser) {
		try {
			return flowPartyService.insertFlowPartyUserInfo(flowPartyUser);
		} catch (Exception e) {
			return R.error().setMsg("添加失败");
		}
	}
	
	/**
	 * 添加流动党员-本平台党员
	 * @param condition 条件
	 * @return
	 */
	@RequestMapping(value="/insertFlowPartyUserInfoThisOrg", method=RequestMethod.POST)
	@LogPoint("添加流动党员-本平台党员")
	@ApiOperation(value = "添加流动党员-本平台党员")
	public R insertFlowPartyUserInfoThisOrg(@RequestParam Map<String, Object> flowPartyUser) {
		try {
			return flowPartyService.insertFlowPartyUserInfoThisOrg(flowPartyUser);
		} catch (Exception e) {
			return R.error().setMsg("添加失败");
		}
	}
}
