package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.BaseUser;

public interface BaseUserService {
	int deleteByPrimaryKey(Long uid);

    int insert(BaseUser record);

    int insertSelective(BaseUser record);

    BaseUser selectByPrimaryKey(Long deptId);

    int updateByPrimaryKeySelective(BaseUser record);

    int updateByPrimaryKey(BaseUser record);
    
    /**
     * 查询基础用户信息
     * @param baseUser 条件
     * @return	查询得到的基础用户
     */
    public R queryBaseUsers(BaseUser baseUser, int pageNum, int pageSize) throws Exception;
    
    /**
     * 查询基础用户信息
     * @param baseUser 条件
     * @return	查询得到的基础用户
     */
    public R queryBaseUsersNotPage(BaseUser baseUser) throws Exception;
    
    /**
     * 修改基础用户信息
     * @param baseUser 要修改的基础用户
     * @return	
     */
    public R updateBaseUser(BaseUser baseUser) throws Exception;
    
    /**
     * 删除基础用户
     * @param baseUser 要删除的基础用户
     * @return	
     */
    public R deleteBaseUser(BaseUser baseUser) throws Exception;
    
    /**
     * 新增基础用户
     * @param baseUser 要新增的基础用户
     * @return
     * @throws Exception
     */
    public R insertBaseUser(BaseUser baseUser) throws Exception;
}
