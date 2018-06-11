package com.zltel.broadcast.um.dao;

import java.util.HashMap;
import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.SysRoleMenu;

public interface SysRoleMenuMapper extends BaseDao<SysRoleMenu> {
	/**
     * 根据条件查询角色权限
     * @param sysRoleMenu 条件
     * @return
     */
    public List<HashMap<String, Object>> querySysRoleMenus(SysRoleMenu sysRoleMenu);
    
    /**
     * 根据角色id删除角色权限
     * @param sysRoleMenu 条件
     * @return
     */
    public int deleteSysRoleMenus(SysRoleMenu sysRoleMenu);
}
