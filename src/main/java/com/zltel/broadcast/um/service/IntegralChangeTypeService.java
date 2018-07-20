package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.IntegralChangeType;

public interface IntegralChangeTypeService {
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
    public R queryICT_ChangeType(IntegralChangeType ict);
    
    /**
     * 查询分值变更分类
     * @param conditions
     * @return
     */
    public R queryICT(IntegralChangeType ict);
}
