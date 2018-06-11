package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.SysUserRole;

public interface SysUserRoleService {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
    
    /**
     * 查询用户角色
     * @param sysUserRole 条件
     * @return	查询得到的用户角色
     */
    public R querySysUserRoles(SysUserRole sysUserRole, int pageNum, int pageSize) throws Exception;
    
    /**
     * 查询用户角色
     * @param sysUserRole 条件
     * @return	查询得到的用户角色
     */
    public R querySysUserRolesNotPage(SysUserRole sysUserRole) throws Exception;
    
    /**
     * 删除用户角色
     * @param sysUserRole 要删除的用户角色
     * @return	
     */
    public R deleteSysUserRole(SysUserRole sysUserRole) throws Exception;
    
    /**
     * 根据用户id删除用户角色
     * @param sysUserRole 要删除的用户角色
     * @return	
     */
    public R deleteSysUserRoleByUserId(SysUserRole sysUserRole) throws Exception;
    
    /**
     * 新增用户角色
     * @param sysUserRole 要新增的用户角色
     * @return
     * @throws Exception
     */
    public R insertSysUserRole(SysUserRole sysUserRole) throws Exception;
}