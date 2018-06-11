package com.zltel.broadcast.um.service;

import java.util.List;

import com.github.pagehelper.PageRowBounds;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.um.bean.SysMenu;
import com.zltel.broadcast.um.bean.SysMenuTreeNode;

public interface SysMenuService {
    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);
    
    List<SysMenu> queryForList(PageRowBounds prb);
    
    List<SysMenu> queryListParentId(Long menuId);
    
    /**
     * 查询菜单
     * @param sysMenu 条件
     * @return	查询得到的菜单
     */
    public R querySysMenus(SysMenu sysMenu, int pageNum, int pageSize) throws Exception;
    
    /**
     * 查询菜单
     * @param sysMenu 条件
     * @return	查询得到的菜单
     */
    public R querySysMenusNotPage(SysMenu sysMenu) throws Exception;
    /**根据登陆用户权限,查询菜单树信息**/
    List<SysMenuTreeNode> queryMenuForTreeByPerms();
    /**查询整个权限树**/
    List<SysMenuTreeNode> queryAllMenuForTree();
    
    /**查询权限树信息**/
    List<TreeNode<SysMenu>> queryTreeMenu();
    
}