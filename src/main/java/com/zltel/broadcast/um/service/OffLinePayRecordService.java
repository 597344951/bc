package com.zltel.broadcast.um.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OffLinePayRecord;

public interface OffLinePayRecordService {
	int deleteByPrimaryKey(Integer id);

    int insert(OffLinePayRecord record);

    int insertSelective(OffLinePayRecord record);

    OffLinePayRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OffLinePayRecord record);

    int updateByPrimaryKey(OffLinePayRecord record);
    
    /**
     * 添加记录
     * @param offlpr
     * @return
     */
    public R insertOffLinePayRecord(OffLinePayRecord offlpr);
    
    /**
     * 查询
     * @param condition
     * @return
     */
    public R queryOffLinePayRecord(Map<String, Object> condition);
}
