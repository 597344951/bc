package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.IntegralChangeType;

public interface IntegralChangeTypeMapper extends BaseDao<IntegralChangeType> {
    int deleteByPrimaryKey(Integer ictId);

    int insert(IntegralChangeType record);

    int insertSelective(IntegralChangeType record);

    IntegralChangeType selectByPrimaryKey(Integer ictId);

    int updateByPrimaryKeySelective(IntegralChangeType record);

    int updateByPrimaryKey(IntegralChangeType record);
    
    public List<Map<String, Object>> queryAllIntegralChangeScene(Map<String, Object> condition);
    
    /**
     * 查询分值改变类型
     * @param conditions
     * @return
     */
    public List<IntegralChangeType> queryICT_ChangeType(IntegralChangeType ict);
    
    /**
     * 查询分值变更分类
     * @param conditions
     * @return
     */
    public List<IntegralChangeType> queryICT(IntegralChangeType ict);
    
    /**
     * 删除积分项
     * @param conditions
     * @return
     */
    public int deleteChangeIntegralIsNull(IntegralChangeType ict);
}