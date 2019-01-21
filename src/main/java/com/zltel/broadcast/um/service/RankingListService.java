package com.zltel.broadcast.um.service;

import java.util.Map;

/**
 * 排行榜
 * @author 张毅
 * @since jdk 1.8.0_172
 * Date: 2019.1.16
 */
public interface RankingListService {
	/**
	 * 查询积分排行榜
	 * @param condition
	 * @return
	 */
	public Map<String, Object> queryIntegralRanking(Map<String, Object> condition);
}
