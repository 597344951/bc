package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * 删除分值变更场景
	 * @param ict 条件
	 * @return
	 */
	@RequestMapping(value="/deleteIntegralChangeScene", method=RequestMethod.POST)
	@ApiOperation(value = "删除分值变更场景")
	public R deleteIntegralChangeScene(@RequestParam Map<String, Object> condition) {
		try {
			if (integralChangeTypeService.deleteIntegralChangeScene(condition)) {
				return R.ok().setMsg("删除成功");
			} else {
				return R.error().setMsg("删除失败");
			}
		} catch (Exception e) {
			return R.error().setMsg("删除失败");
		}
	}
	
	/**
	 * 查询所有分值改变的场景和变更的分值（用于观察内置积分加扣分的设置情况）
	 * @param ict 条件
	 * @return
	 */
	@RequestMapping(value="/queryAllIntegralChangeScene", method=RequestMethod.POST)
	@ApiOperation(value = "查询所有分值改变的场景和变更的分值（用于观察内置积分加扣分的设置情况）")
	public R queryAllIntegralChangeScene(@RequestParam Map<String, Object> condition) {
		try {
			return R.ok().setData(integralChangeTypeService.queryAllIntegralChangeScene(condition));
		} catch (Exception e) {
			return R.error().setMsg("查询失败");
		}
	}
	
	/**
	 * 查询积分变更类型
	 * @param ict 条件
	 * @return
	 */
	@RequestMapping(value="/queryICT_ChangeType", method=RequestMethod.POST)
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
	@ApiOperation(value = "查询分值变更分类")
	public R queryICT(IntegralChangeType ict) {
		try {
			return integralChangeTypeService.queryICT(ict);
		} catch (Exception e) {
			return R.error().setMsg("查询分值变更分类失败");
		}
	}
}
