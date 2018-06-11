package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.BaseUserInfo;

public interface BaseUserInfoService {
	int deleteByPrimaryKey(Long uid);

    int insert(BaseUserInfo record);

    int insertSelective(BaseUserInfo record);

    BaseUserInfo selectByPrimaryKey(Long deptId);

    int updateByPrimaryKeySelective(BaseUserInfo record);

    int updateByPrimaryKey(BaseUserInfo record);
    
    /**
     * 查询基础用户信息
     * @param baseUserInfo 条件
     * @return	查询得到的基础用户
     */
    public R queryBaseUserInfos(BaseUserInfo baseUserInfo, int pageNum, int pageSize) throws Exception;
}
