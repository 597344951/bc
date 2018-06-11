package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.NationType;

public interface NationTypeService {
	int deleteByPrimaryKey(Integer uid);

    int insert(NationType record);

    int insertSelective(NationType record);

    NationType selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(NationType record);

    int updateByPrimaryKey(NationType record);

    /**
     * 查询民族
     * @param nationType
     * @return
     */
    public R queryNationType(NationType nationType) throws Exception;
}
