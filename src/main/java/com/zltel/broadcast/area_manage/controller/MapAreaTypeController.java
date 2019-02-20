package com.zltel.broadcast.area_manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.area_manage.bean.MapAreaType;
import com.zltel.broadcast.area_manage.service.MapAreaTypeService;
import com.zltel.broadcast.common.json.R;

import io.swagger.annotations.ApiOperation;

/**
 * 区域类型管理
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2019.1.25
 */
@RequestMapping(value="/area/type")
@RestController
public class MapAreaTypeController {
	@Autowired
	private MapAreaTypeService mapAreaTypeService;
	
	/**
	 * 查询区域类型
	 * @param mat 条件
	 * @return
	 */
	@RequestMapping(value="/queryMapAreaTypes", method=RequestMethod.POST)
	@ApiOperation(value = "查询区域类型")
	public R queryMapAreaTypes(MapAreaType mat) {
		try {
			return R.ok().setData(mapAreaTypeService.queryMapAreaTypes(mat));
		} catch (Exception e) {
			return R.error().setMsg("查询区域类型失败");
		}
	}
}
