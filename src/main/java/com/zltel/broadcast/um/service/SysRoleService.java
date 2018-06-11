package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.SysRole;

public interface SysRoleService {
    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    /**
     * 查询系统角色
     * @param sysRole 条件
     * @return	查询得到的系统角色
     */
    public R querySysRoles(SysRole sysRole, int pageNum, int pageSize) throws Exception;
    
    /**
     * 查询系统角色
     * @param sysRole 条件
     * @return	查询得到的系统角色
     */
    public R querySysRolesNotPage(SysRole sysRole) throws Exception;
    
    /**
     * 修改系统角色信息
     * @param sysRole 要修改的系统角色
     * @return	
     */
    public R updateSysRole(SysRole sysRole) throws Exception;
    
    /**
     * 删除系统角色
     * @param sysRole 要删除的系统角色
     * @return	
     */
    public R deleteSysRole(SysRole sysRole) throws Exception;
    
    /**
     * 新增系统角色
     * @param sysRole 要新增的系统角色
     * @return
     * @throws Exception
     */
    public R insertSysRole(SysRole sysRole) throws Exception;
}