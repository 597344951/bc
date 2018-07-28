package com.zltel.broadcast.planjoin.dao;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.planjoin.bean.ActivitySign;

public interface ActivitySignMapper extends BaseDao<ActivitySign>{
    int deleteByPrimaryKey(Integer id);

    int insert(ActivitySign record);

    int insertSelective(ActivitySign record);

    ActivitySign selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivitySign record);

    int updateByPrimaryKey(ActivitySign record);
}