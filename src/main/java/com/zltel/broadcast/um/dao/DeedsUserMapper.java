package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.DeedsUser;

public interface DeedsUserMapper extends BaseDao<DeedsUser> {
    int deleteByPrimaryKey(Integer id);

    int insert(DeedsUser record);

    int insertSelective(DeedsUser record);

    DeedsUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeedsUser record);

    int updateByPrimaryKeyWithBLOBs(DeedsUser record);

    int updateByPrimaryKey(DeedsUser record);
    
    public List<Map<String, Object>> queryDeedsUsers(Map<String, Object> conditions);
}