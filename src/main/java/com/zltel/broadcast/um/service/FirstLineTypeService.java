package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.FirstLineType;

public interface FirstLineTypeService {
	int deleteByPrimaryKey(Integer uid);

    int insert(FirstLineType record);

    int insertSelective(FirstLineType record);

    FirstLineType selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(FirstLineType record);

    int updateByPrimaryKey(FirstLineType record);
    
    /**
     * 查询一线情况
     * @param firstLineType
     * @return
     */
    public R queryFirstLineTypes(FirstLineType firstLineType) throws Exception;
}
