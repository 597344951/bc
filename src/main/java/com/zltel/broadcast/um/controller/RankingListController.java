package com.zltel.broadcast.um.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.service.RankingListService;

import io.swagger.annotations.ApiOperation;

/**
 * 排行榜
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2019.1.16
 */
@RequestMapping(value="/ranking/list")
@RestController
public class RankingListController {
	@Autowired
	private RankingListService rankingListService;
	
	/**
	 * 查询积分排行榜
	 * @param condition 条件
	 * @return
	 */
	@RequestMapping(value="/queryIntegralRanking", method=RequestMethod.POST)
	@ApiOperation(value = "查询积分排行榜")
	public R queryIntegralRanking(@RequestParam Map<String, Object> condition) {
		try {
			return R.ok().setData(rankingListService.queryIntegralRanking(condition));
		} catch (Exception e) {
			return R.error().setMsg("查询失败");
		}
	}
}
