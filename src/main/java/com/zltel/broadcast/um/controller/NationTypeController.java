package com.zltel.broadcast.um.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.NationType;
import com.zltel.broadcast.um.service.NationTypeService;

import io.swagger.annotations.ApiOperation;

/**
 * 民族
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.30
 */
@RequestMapping(value="/nation")
@RestController
public class NationTypeController {
	@Autowired
	private NationTypeService nationTypeService;
	
	/**
	 * 查询民族
	 * @param nationType 条件
	 * @return
	 */
	@RequestMapping(value="/queryNationType", method=RequestMethod.POST)
	@LogPoint("查询民族")
	@ApiOperation(value = "查询民族")
	public R queryNationType(NationType nationType) {
		try {
			return nationTypeService.queryNationType(nationType);
		} catch (Exception e) {
			return R.error().setMsg("查询民族信息失败");
		}
	}
}
