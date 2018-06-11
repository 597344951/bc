package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.SysUser;

public interface SysUserMapper extends BaseDao<SysUser> {

    SysUser selectByUserName(String username);

    /** 查询指定用户权限信息 **/
    List<String> queryAllPerms(Integer userId);

    /** 查询指定用户角色信息 **/
    List<String> queryAllRoles(Integer userId);

    /** 更新指定用户密码信息 **/
    int updatePassword(SysUser record);
    
    /**
     * 根据条件查询系统用户
     * @param sysUser
     * @return
     */
    public List<SysUser> querySysUsers(SysUser sysUser);
}
