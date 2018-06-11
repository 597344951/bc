package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.BaseUserInfo;

public interface BaseUserInfoMapper extends BaseDao<BaseUserInfo> {
    int deleteByPrimaryKey(Integer baseUserId);

    int insert(BaseUserInfo record);

    int insertSelective(BaseUserInfo record);

    BaseUserInfo selectByPrimaryKey(Integer baseUserId);

    int updateByPrimaryKeySelective(BaseUserInfo record);

    int updateByPrimaryKey(BaseUserInfo record);
    
    /**
     * 查询基础用户信息
     * @param baseUserInfo
     * @return
     */
    public List<BaseUserInfo> queryBaseUserInfos(BaseUserInfo baseUserInfo);
}