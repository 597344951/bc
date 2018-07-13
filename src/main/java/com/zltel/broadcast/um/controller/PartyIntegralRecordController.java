package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.service.PartyIntegralRecordService;

import io.swagger.annotations.ApiOperation;

/**
 * 党员积分记录
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.7.12
 */
@RequestMapping(value="/party/integral")
@RestController
public class PartyIntegralRecordController {
	@Autowired
	private PartyIntegralRecordService partyIntegralRecordService;
	
	/**
	 * 查询积分记录
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryPartyIntegralRecords", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:insert"})
	@ApiOperation(value = "查询积分记录")
	public R queryPartyIntegralRecords(@RequestParam Map<String, Object> conditions, int pageNum, int pageSize) {
		try {
			return partyIntegralRecordService.queryPartyIntegralRecords(conditions, pageNum, pageSize);
		} catch (Exception e) {
			return R.error().setMsg("查询积分记录失败");
		}
	}
}
