package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.OffLinePayRecord;

public interface OffLinePayRecordMapper extends BaseDao<OffLinePayRecord> {
    int deleteByPrimaryKey(Integer id);

    int insert(OffLinePayRecord record);

    int insertSelective(OffLinePayRecord record);

    OffLinePayRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OffLinePayRecord record);

    int updateByPrimaryKey(OffLinePayRecord record);
    
    /**
     * 查询
     * @param condition
     * @return
     */
    public List<Map<String, Object>> queryOffLinePayRecord(Map<String, Object> condition);
}