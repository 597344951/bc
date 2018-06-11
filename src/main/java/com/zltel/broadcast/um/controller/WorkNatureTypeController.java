package com.zltel.broadcast.um.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.WorkNatureType;
import com.zltel.broadcast.um.service.WorkNatureTypeService;

import io.swagger.annotations.ApiOperation;

/**
 * 工作性质
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.31
 */
@RequestMapping(value="/wnt")
@RestController
public class WorkNatureTypeController {
	@Autowired
	private WorkNatureTypeService workNatureTypeService;
	
	/**
	 * 查询工作性质
	 * @param workNatureType 条件
	 * @return
	 */
	@RequestMapping(value="/queryWorkNatureTypes", method=RequestMethod.POST)
	@LogPoint("查询工作性质")
	@ApiOperation(value = "查询工作性质")
	public R queryWorkNatureTypes(WorkNatureType workNatureType) {
		try {
			return workNatureTypeService.queryWorkNatureTypes(workNatureType);
		} catch (Exception e) {
			return R.error().setMsg("查询查询工作性质信息失败");
		}
	}
}
