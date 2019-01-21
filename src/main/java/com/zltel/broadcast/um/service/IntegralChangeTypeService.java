package com.zltel.broadcast.um.service;

import java.util.List;
import java.util.Map;

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
     * 删除分值变更场景
     * @param condition
     * @return
     */
    public boolean deleteIntegralChangeScene(Map<String, Object> condition) throws Exception;
    
    /**
     * 查询所有分值改变的场景和变更的分值（用于观察内置积分加扣分的设置情况）
     * @param condition
     * @return
     */
    public List<Map<String, Object>> queryAllIntegralChangeScene(Map<String, Object> condition);
    
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
