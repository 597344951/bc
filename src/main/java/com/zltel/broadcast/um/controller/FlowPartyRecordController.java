package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.service.FlowPartyRecordService;

import io.swagger.annotations.ApiOperation;

/**
 * 流动党员记录
 * @author 张毅
 * @since jdk 1.8.0_192
 * data: 2018.11.20
 *
 */
@RequestMapping(value="flow_party/record")
@RestController
public class FlowPartyRecordController {
	@Autowired
	private FlowPartyRecordService flowPartyRecordService;
	
	/**
	 * 查询流动党员记录
	 * @param condition 条件
	 * @return
	 */
	@RequestMapping(value="/queryFlowPartyRecords", method=RequestMethod.POST)
	@LogPoint("查询流动党员记录")
	@ApiOperation(value = "查询流动党员记录")
	public R queryFlowPartyRecords(@RequestParam Map<String, Object> condition) {
		try {
			return flowPartyRecordService.queryFlowPartyRecords(condition);
		} catch (Exception e) {
			return R.error().setMsg("查询失败");
		}
	}
}
