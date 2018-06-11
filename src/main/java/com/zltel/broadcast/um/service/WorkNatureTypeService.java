package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.WorkNatureType;

public interface WorkNatureTypeService {
	int deleteByPrimaryKey(Integer uid);

    int insert(WorkNatureType record);

    int insertSelective(WorkNatureType record);

    WorkNatureType selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(WorkNatureType record);

    int updateByPrimaryKey(WorkNatureType record);
    
    /**
     * 查询工作性质
     * @param workNatureType
     * @return
     */
    public R queryWorkNatureTypes(WorkNatureType workNatureType) throws Exception;
}
