package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.DeedsType;

public interface DeedsTypeMapper extends BaseDao<DeedsType> {
    int deleteByPrimaryKey(Integer id);

    int insert(DeedsType record);

    int insertSelective(DeedsType record);

    DeedsType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeedsType record);

    int updateByPrimaryKey(DeedsType record);
    
    public List<DeedsType> queryDeedsTypes(DeedsType dt);
}