package com.zltel.broadcast.um.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyMembershipDuesStatus;
import com.zltel.broadcast.um.service.PartyMembershipDuesStatusService;

import io.swagger.annotations.ApiOperation;

/**
 * 党费缴纳状态
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.7.7
 */
@RequestMapping(value="party/pmds")
@RestController
public class PartyMembershipDuesStatusController {
	@Autowired
	private PartyMembershipDuesStatusService partyMembershipDuesStatusService;
	
	/**
	 * 查询党费缴纳状态
	 * @param partyMembershipDuesStatus 条件
	 * @return
	 */
	@RequestMapping(value="/queryPMDSs", method=RequestMethod.POST)
	@ApiOperation(value = "查询党费缴纳状态")
	public R queryPMDSs(PartyMembershipDuesStatus partyMembershipDuesStatus) {
		try {
			return partyMembershipDuesStatusService.queryPMDSs(partyMembershipDuesStatus);
		} catch (Exception e) {
			return R.error().setMsg("查询党费缴纳状态失败");
		}
	}
}
