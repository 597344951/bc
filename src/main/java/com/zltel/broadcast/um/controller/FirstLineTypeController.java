package com.zltel.broadcast.um.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.FirstLineType;
import com.zltel.broadcast.um.service.FirstLineTypeService;

import io.swagger.annotations.ApiOperation;

/**
 * 一线情况
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.31
 */
@RequestMapping(value="/firty")
@RestController
public class FirstLineTypeController {
	@Autowired
	private FirstLineTypeService firstLineTypeService;
	
	/**
	 * 查询一线情况
	 * @param firstLineType 条件
	 * @return
	 */
	@RequestMapping(value="/queryFirstLineTypes", method=RequestMethod.POST)
	@LogPoint("查询一线情况")
	@ApiOperation(value = "查询一线情况")
	public R queryFirstLineTypes(FirstLineType firstLineType) {
		try {
			return firstLineTypeService.queryFirstLineTypes(firstLineType);
		} catch (Exception e) {
			return R.error().setMsg("查询一线情况信息失败");
		}
	}
}
