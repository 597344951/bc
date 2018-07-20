package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.IntegralChangeType;

public interface IntegralChangeTypeMapper extends BaseDao<IntegralChangeType> {
    int deleteByPrimaryKey(Integer ictId);

    int insert(IntegralChangeType record);

    int insertSelective(IntegralChangeType record);

    IntegralChangeType selectByPrimaryKey(Integer ictId);

    int updateByPrimaryKeySelective(IntegralChangeType record);

    int updateByPrimaryKey(IntegralChangeType record);
    
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
}