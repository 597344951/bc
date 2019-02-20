package com.zltel.broadcast.area_manage.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.area_manage.service.MapAreaInfoService;
import com.zltel.broadcast.common.json.R;

import io.swagger.annotations.ApiOperation;

/**
 * 区域类型管理
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2019.1.28
 */
@RequestMapping(value="/area/info")
@RestController
public class MapAreaInfoController {
	@Autowired
	private MapAreaInfoService mapAreaInfoService;
	
	/**
	 * 添加区域类型
	 * @param mat 条件
	 * @return
	 */
	@RequestMapping(value="/insertMapAreaInfo", method=RequestMethod.POST)
	@ApiOperation(value = "添加区域类型")
	public R insertMapAreaInfo(@RequestParam("condition") String condition) {
		try {
			if (mapAreaInfoService.insertMapAreaInfo(condition)) {
				return R.ok().setMsg("添加成功");
			}
			return R.ok().setMsg("添加失败");
		} catch (Exception e) {
			return R.ok().setMsg("添加失败");
		}
	}
	
	/**
	 * 查询区域
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryMapAreaInfos", method=RequestMethod.POST)
	@ApiOperation(value = "查询区域")
	public R queryMapAreaInfos(@RequestParam Map<String, Object> condition, int pageNum, int pageSize) {
		try {
			return R.ok().setData(mapAreaInfoService.queryMapAreaInfos(condition, pageNum, pageSize));
		} catch (Exception e) {
			return R.error().setMsg("查询失败");
		}
	}
	
	/**
	 * 查询区域坐标信息
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryMapAreaLatLngs", method=RequestMethod.POST)
	@ApiOperation(value = "查询区域坐标信息")
	public R queryMapAreaLatLngs(@RequestParam Map<String, Object> condition) {
		try {
			return R.ok().setData(mapAreaInfoService.queryMapAreaLatLngs(condition));
		} catch (Exception e) {
			return R.error().setMsg("查询失败坐标信息");
		}
	}
}
