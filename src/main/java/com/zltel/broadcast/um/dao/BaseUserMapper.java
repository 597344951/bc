package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.BaseUser;

public interface BaseUserMapper extends BaseDao<BaseUser> {
	/**
     * 根据条件查询基础用户
     * @param baseUser 条件
     * @return
     */
    public List<BaseUser> queryBaseUsers(BaseUser baseUser);
}