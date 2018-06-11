package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.SysMenu;

public interface SysMenuMapper extends BaseDao<SysMenu> {
	/**
     * 根据条件查询菜单
     * @param sysMenu
     * @return
     */
    public List<SysMenu> querySysMenus(SysMenu sysMenu);
    
    /**
     * 根据条件查询菜单
     * @param sysMenu
     * @return
     */
    public List<SysMenu> queryForList();
}