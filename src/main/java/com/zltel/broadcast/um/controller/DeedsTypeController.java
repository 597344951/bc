package com.zltel.broadcast.um.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.DeedsType;
import com.zltel.broadcast.um.service.DeedsTypeService;

import io.swagger.annotations.ApiOperation;

/**
 * 用户事迹类型
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.8.3
 */
@RequestMapping(value="/user/dtc")
@RestController
public class DeedsTypeController {
	@Autowired
	private DeedsTypeService deedsTypeService;
	
	/**
	 * 查询用户事迹类型
	 * @param dt 条件
	 * @return
	 */
	@RequestMapping(value="/queryDeedsTypes", method=RequestMethod.POST)
	@ApiOperation(value = "查询用户事迹类型")
	public R queryDeedsTypes(DeedsType dt) {
		try {
			return deedsTypeService.queryDeedsTypes(dt);
		} catch (Exception e) {
			return R.error().setMsg("查询用户事迹类型失败");
		}
	}
}
