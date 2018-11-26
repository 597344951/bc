package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OffLinePayRecord;
import com.zltel.broadcast.um.service.OffLinePayRecordService;

import io.swagger.annotations.ApiOperation;

/**
 * 离线缴纳党费填报记录
 * @author 张毅
 * @since jdk1.8.0_191_b12
 * date：2018.11.9
 */
@RequestMapping(value="/offlpr")
@RestController
public class OffLinePayRecordControl {
	@Autowired
	private OffLinePayRecordService offLinePayRecordService;
	
	/**
	 * 添加记录
	 * @param offLinePayRecord 条件
	 * @return
	 */
	@RequestMapping(value="/insertOffLinePayRecord", method=RequestMethod.POST)
	@LogPoint("添加离线缴纳党费填报记录")
	@ApiOperation(value = "添加离线缴纳党费填报记录")
	public R insertOffLinePayRecord(OffLinePayRecord offLinePayRecord) {
		try {
			return offLinePayRecordService.insertOffLinePayRecord(offLinePayRecord);
		} catch (Exception e) {
			return R.error().setMsg("添加失败");
		}
	}
	
	/**
	 * 查询记录
	 * @param offLinePayRecord 条件
	 * @return
	 */
	@RequestMapping(value="/queryOffLinePayRecord", method=RequestMethod.POST)
	@LogPoint("查询离线缴纳党费填报记录")
	@ApiOperation(value = "查询离线缴纳党费填报记录")
	public R queryOffLinePayRecord(Map<String, Object> condition) {
		try {
			return offLinePayRecordService.queryOffLinePayRecord(condition);
		} catch (Exception e) {
			return R.error().setMsg("查询失败");
		}
	}
}
