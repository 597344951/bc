package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.DeedsType;

public interface DeedsTypeService {
	int deleteByPrimaryKey(Integer id);

    int insert(DeedsType record);

    int insertSelective(DeedsType record);

    DeedsType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeedsType record);

    int updateByPrimaryKey(DeedsType record);
    
    /**
     * 查询用户事迹类型
     * @param dts
     * @return
     */
    public R queryDeedsTypes(DeedsType dt);
}
