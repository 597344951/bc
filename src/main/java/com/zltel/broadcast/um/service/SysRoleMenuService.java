package com.zltel.broadcast.um.service;

import java.util.List;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.SysRoleMenu;

public interface SysRoleMenuService {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleMenu record);

    int updateByPrimaryKey(SysRoleMenu record);
    
    /**
     * 查询角色权限
     * @param sysRoleMenu 条件
     * @return	查询得到的角色权限
     */
    public R querySysRoleMenus(SysRoleMenu sysRoleMenu, int pageNum, int pageSize) throws Exception;
    
    /**
     * 查询角色权限
     * @param sysRoleMenu 条件
     * @return	查询得到的角色权限
     */
    public R querySysRoleMenusNotPage(SysRoleMenu sysRoleMenu) throws Exception;
    
    /**
     * 删除角色权限
     * @param sysRoleMenu 要删除的角色权限
     * @return	
     */
    public R deleteSysRoleMenu(SysRoleMenu sysRoleMenu) throws Exception;
    
    /**
     * 根据角色id删除角色权限
     * @param sysRoleMenu 要删除的角色权限
     * @return	
     */
    public R deleteSysRoleMenus(SysRoleMenu sysRoleMenu) throws Exception;
    
    /**
     * 新增角色权限
     * @param sysRoleMenu 要新增的角色权限
     * @return
     * @throws Exception
     */
    public R insertSysRoleMenu(SysRoleMenu sysRoleMenu, List<Integer> menuIds) throws Exception;
}