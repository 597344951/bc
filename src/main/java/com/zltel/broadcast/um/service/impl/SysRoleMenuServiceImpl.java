package com.zltel.broadcast.um.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.SysMenu;
import com.zltel.broadcast.um.bean.SysRoleMenu;
import com.zltel.broadcast.um.dao.SysMenuMapper;
import com.zltel.broadcast.um.dao.SysRoleMenuMapper;
import com.zltel.broadcast.um.service.SysRoleMenuService;

@Service
public class SysRoleMenuServiceImpl extends BaseDaoImpl<SysRoleMenu> implements SysRoleMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public BaseDao<SysRoleMenu> getInstince() {
        return this.sysRoleMenuMapper;
    }
    
    /**
     * 查询角色权限
     * @param sysRoleMenu 条件
     * @return	查询得到的角色权限
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R querySysRoleMenus(SysRoleMenu sysRoleMenu, int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<HashMap<String, Object>> sysRoleMenusMap = sysRoleMenuMapper.querySysRoleMenus(sysRoleMenu);	//开始查询，没有条件则查询所有角色权限
		PageInfo<HashMap<String, Object>> sysRoleMenusForPageInfo = new PageInfo<>(sysRoleMenusMap);
		if (sysRoleMenusForPageInfo != null && sysRoleMenusForPageInfo.getList() != null && sysRoleMenusForPageInfo.getList().size() > 0) {	//是否查询到数据
			return R.ok().setData(sysRoleMenusForPageInfo).setMsg("查询角色权限成功");
		} else {
			return R.ok().setMsg("没有查询到角色权限信息");
		}
    }
    
    /**
     * 查询角色权限
     * @param sysRoleMenu 条件
     * @return	查询得到的角色权限
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R querySysRoleMenusNotPage(SysRoleMenu sysRoleMenu) throws Exception {
		List<HashMap<String, Object>> sysRoleMenusMap = sysRoleMenuMapper.querySysRoleMenus(sysRoleMenu);	//开始查询，没有条件则查询所有角色权限
		if (sysRoleMenusMap != null && sysRoleMenusMap.size() > 0) {	//是否查询到数据
			return R.ok().setData(sysRoleMenusMap).setMsg("查询角色权限成功");
		} else {
			return R.ok().setMsg("没有查询到角色权限信息");
		}
    }
    
    /**
     * 删除角色权限
     * @param sysRoleMenu 要删除的角色权限
     * @return	
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteSysRoleMenu(SysRoleMenu sysRoleMenu) throws Exception {
		if(sysRoleMenu != null) {
			int count = 0;
			if (sysRoleMenu.getId() == null) {
				throw new Exception();	//删除角色权限一定需要uid，依据uid进行角色权限的删除
			}
			count += this.deleteByPrimaryKey(sysRoleMenu.getId());	//开始删除角色权限信息
			
			if (count == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除角色权限 " + count + "条。");
			} else {	//没有受影响行数或者受影响行数与要删除的角色权限数量不匹配表示删除失败
				throw new Exception();
			}
		} else {	//删除一定需要角色权限
			throw new Exception();
		}
    }
    
    /**
     * 根据角色id删除角色权限
     * @param sysRoleMenu 要删除的角色权限
     * @return	
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteSysRoleMenus(SysRoleMenu sysRoleMenu) throws Exception {
		if(sysRoleMenu != null) {
			int count = 0;
			if (sysRoleMenu.getRoleId() == null) {
				throw new Exception();	//删除角色权限一定需要角色id，依据角色id进行角色权限的删除
			}
			count += sysRoleMenuMapper.deleteSysRoleMenus(sysRoleMenu);	//开始删除角色权限信息
			
			if (count == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setData(count).setMsg("共计删除角色权限 " + count + "条。");
			} else {	
				return R.ok().setData(0).setMsg("没有需要删除的角色权限");
			}
		} else {	//删除一定需要角色权限
			throw new Exception();
		}
    }
    
    /**
     * 新增角色权限
     * @param sysRoleMenu 要新增的角色权限
     * @return
     * @throws Exception
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertSysRoleMenu(SysRoleMenu sysRoleMenu, List<Integer> menuIds) throws Exception {
		if (menuIds != null && menuIds.size() > 0) {
			//删除此角色之前的权限
			this.deleteSysRoleMenus(sysRoleMenu);
			
			int count = 0;
			
			for (Integer menuId : menuIds) {
				if(menuId == -1) {	//传入根节点（全部权限）：-1，表示赋予全部权限
					List<SysMenu> sysMenus = sysMenuMapper.queryForList();
					if (sysMenus != null && sysMenus.size() > 0) {
						menuIds.removeAll(menuIds);
						for (SysMenu sysMenu : sysMenus) {
							menuIds.add(Integer.parseInt(String.valueOf(sysMenu.getMenuId())));
						}
					}
					break;
				}
			}
			
			for (Integer menuId : menuIds) {
				sysRoleMenu.setId(null);	//自增，不需要设置值
				sysRoleMenu.setMenuId((long)menuId);
				count += this.insertSelective(sysRoleMenu);	//开始添加角色权限信息
			}
			
			if (count == menuIds.size()) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("角色权限添加成功。");
			} else {	//没有受影响行数表示添加失败
				throw new Exception();
			}
		} else {	//添加一定需要一个角色权限信息
			throw new Exception();
		}
    }
}
