package com.zltel.broadcast.um.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.EducationLevel;
import com.zltel.broadcast.um.service.EducationLevelService;

import io.swagger.annotations.ApiOperation;

/**
 * 教育水平
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.30
 */
@RequestMapping(value="/eduLe")
@RestController
public class EducationLevelController {
	@Autowired
	private EducationLevelService educationLevelService;
	
	/**
	 * 查询教育水平
	 * @param educationLevel 条件
	 * @return
	 */
	@RequestMapping(value="/queryEducationLevels", method=RequestMethod.POST)
	@LogPoint("查询教育水平")
	@ApiOperation(value = "查询教育水平")
	public R queryEducationLevels(EducationLevel educationLevel) {
		try {
			return educationLevelService.queryEducationLevels(educationLevel);
		} catch (Exception e) {
			return R.error().setMsg("查询教育水平信息失败");
		}
	}
}
