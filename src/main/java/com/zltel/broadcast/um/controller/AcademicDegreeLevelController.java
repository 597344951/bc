package com.zltel.broadcast.um.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.AcademicDegreeLevel;
import com.zltel.broadcast.um.service.AcademicDegreeLevelService;

import io.swagger.annotations.ApiOperation;

/**
 * 学位水平
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.30
 */
@RequestMapping(value="/acaDeLe")
@RestController
public class AcademicDegreeLevelController {
	@Autowired
	private AcademicDegreeLevelService academicDegreeLevelService;
	
	/**
	 * 查询学位水平
	 * @param academicDegreeLevel 条件
	 * @return
	 */
	@RequestMapping(value="/queryAcademicDegreeLevels", method=RequestMethod.POST)
	@LogPoint("查询学位水平")
	@ApiOperation(value = "查询学位水平")
	public R queryAcademicDegreeLevels(AcademicDegreeLevel academicDegreeLevel) {
		try {
			return academicDegreeLevelService.queryAcademicDegreeLevels(academicDegreeLevel);
		} catch (Exception e) {
			return R.error().setMsg("查询学位水平信息失败");
		}
	}
}
