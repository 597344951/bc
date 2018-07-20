package com.zltel.broadcast.um.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.IntegralChangeType;
import com.zltel.broadcast.um.service.IntegralChangeTypeService;

import io.swagger.annotations.ApiOperation;

/**
 * 党员积分记录
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.7.12
 */
@RequestMapping(value="/org/ict")
@RestController
public class IntegralChangeTypeController {
	@Autowired
	private IntegralChangeTypeService integralChangeTypeService;
	
	/**
	 * 查询积分变更类型
	 * @param ict 条件
	 * @return
	 */
	@RequestMapping(value="/queryICT_ChangeType", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:insert"})
	@ApiOperation(value = "查询积分变更类型")
	public R queryICT_ChangeType(IntegralChangeType ict) {
		try {
			return integralChangeTypeService.queryICT_ChangeType(ict);
		} catch (Exception e) {
			return R.error().setMsg("查询查询积分变更类型失败");
		}
	}
	
	/**
	 * 查询分值变更分类
	 * @param ict 条件
	 * @return
	 */
	@RequestMapping(value="/queryICT", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:ic:insert"})
	@ApiOperation(value = "查询分值变更分类")
	public R queryICT(IntegralChangeType ict) {
		try {
			return integralChangeTypeService.queryICT(ict);
		} catch (Exception e) {
			return R.error().setMsg("查询分值变更分类失败");
		}
	}
}
