package com.zltel.broadcast.um.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyMembershipDuesPaidWay;
import com.zltel.broadcast.um.service.PartyMembershipDuesPaidWayService;

import io.swagger.annotations.ApiOperation;

/**
 * 党费缴纳方式
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.7.7
 */
@RequestMapping(value="party/pmdw")
@RestController
public class PartyMembershipDuesPaidWayController {
	@Autowired
	private PartyMembershipDuesPaidWayService partyMembershipDuesPaidWayService;
	
	/**
	 * 查询党费缴纳方式
	 * @param PartyMembershipDuesPaidWay 条件
	 * @return
	 */
	@RequestMapping(value="/queryPMDWs", method=RequestMethod.POST)
	@ApiOperation(value = "查询党费缴纳方式")
	public R queryPMDWs(PartyMembershipDuesPaidWay PartyMembershipDuesPaidWay) {
		try {
			return partyMembershipDuesPaidWayService.queryPMDWs(PartyMembershipDuesPaidWay);
		} catch (Exception e) {
			return R.error().setMsg("查询党费缴纳方式失败");
		}
	}
}
