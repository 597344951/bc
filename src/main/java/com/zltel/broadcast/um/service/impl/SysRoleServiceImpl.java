package com.zltel.broadcast.um.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.SysRole;
import com.zltel.broadcast.um.bean.SysRoleMenu;
import com.zltel.broadcast.um.dao.SysRoleMapper;
import com.zltel.broadcast.um.service.SysRoleMenuService;
import com.zltel.broadcast.um.service.SysRoleService;

@Service
public class SysRoleServiceImpl extends BaseDaoImpl<SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Autowired
	private SysRoleMenuService sysRoleMenuService;

    @Override
    public BaseDao<SysRole> getInstince() {
        return this.sysRoleMapper;
    }
    
    /**
     * 查询系统角色
     * @param sysRole 条件
     * @return	查询得到的系统角色
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R querySysRoles(SysRole sysRole, int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<SysRole> sysRoles = sysRoleMapper.querySysRoles(sysRole);	//开始查询，没有条件则查询所有系统角色
		PageInfo<SysRole> sysUsersForPageInfo = new PageInfo<>(sysRoles);
		if (sysUsersForPageInfo != null && sysUsersForPageInfo.getList() != null 
				&& sysUsersForPageInfo.getList().size() > 0) {	//是否查询到数据
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SysRole sysR : sysRoles) {
				sysR.setCreateTime(sdf.parse(sdf.format(sysR.getCreateTime())));
			}
			return R.ok().setData(sysUsersForPageInfo).setMsg("查询系统角色信息成功");
		} else {
			return R.ok().setMsg("没有查询到系统角色");
		}
    }
    
    /**
     * 查询系统角色
     * @param sysRole 条件
     * @return	查询得到的系统角色
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R querySysRolesNotPage(SysRole sysRole) throws Exception {
		List<SysRole> sysRoles = sysRoleMapper.querySysRoles(sysRole);	//开始查询，没有条件则查询所有系统角色
		if (sysRoles != null && sysRoles.size() > 0) {	//是否查询到数据
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SysRole sysR : sysRoles) {
				sysR.setCreateTime(sdf.parse(sdf.format(sysR.getCreateTime())));
			}
			return R.ok().setData(sysRoles).setMsg("查询系统角色信息成功");
		} else {
			return R.ok().setMsg("没有查询到系统角色");
		}
    }
    
    /**
     * 修改系统角色信息
     * @param sysRole 要修改的系统角色
     * @return	
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R updateSysRole(SysRole sysRole) throws Exception {
		if (sysRole != null) {
			int count = this.updateByPrimaryKeySelective(sysRole);	//开始修改系统角色信息
			if (count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("系统角色信息修改成功。");
			} else {
				throw new Exception();	//没有受影响行数表示修改失败
			}
		} else {
			throw new Exception();	//修改一定需要一个角色信息
		}
    }
    
    /**
     * 删除系统角色（删除时也要删除此角色拥有的权限）
     * @param sysRole 要删除的系统角色
     * @return	
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteSysRole(SysRole sysRole) throws Exception {
		if(sysRole != null) {
			int count = 0;
			int countRoleMenu = 0;
			if (sysRole.getRoleId() == null) {
				throw new Exception();	//删除用户一定需要uid，依据uid进行用户的删除
			}
			count += this.deleteByPrimaryKey(sysRole.getRoleId());	//开始删除系统用户信息
			//删除此角色拥有的权限
			SysRoleMenu srm = new SysRoleMenu();
			srm.setRoleId(sysRole.getRoleId());
			countRoleMenu = (int)sysRoleMenuService.deleteSysRoleMenus(srm).get("data");
			
			if (count == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除系统用户 " + count + "条，删除相关角色权限" + countRoleMenu + "条。");
			} else {	//没有受影响行数或者受影响行数与要删除的用户数量不匹配表示删除失败
				throw new Exception();
			}
		} else {	//删除一定需要用户
			throw new Exception();
		}
    }
    
    /**
     * 新增系统角色
     * @param sysRole 要新增的系统角色
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R insertSysRole(SysRole sysRole) throws Exception {
		if (sysRole != null) {
			sysRole.setRoleId(null);	//自增，不需要设置值
			int count = this.insertSelective(sysRole);	//开始添加系统角色信息
			if (count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("系统角色添加成功。");
			} else {	//没有受影响行数表示添加失败
				throw new Exception();
			}
		} else {	//添加一定需要一个角色信息
			throw new Exception();
		}
    }
}
