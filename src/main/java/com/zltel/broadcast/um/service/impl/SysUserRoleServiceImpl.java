package com.zltel.broadcast.um.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.SysUserRole;
import com.zltel.broadcast.um.dao.SysUserRoleMapper;
import com.zltel.broadcast.um.service.SysUserRoleService;

@Service
public class SysUserRoleServiceImpl extends BaseDaoImpl<SysUserRole> implements SysUserRoleService {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public BaseDao<SysUserRole> getInstince() {
        return this.sysUserRoleMapper;
    }
    
    /**
     * 查询用户角色
     * @param sysUserRole 条件
     * @return	查询得到的用户角色
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R querySysUserRoles(SysUserRole sysUserRole, int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<HashMap<String, Object>> sysUserRolesMap = sysUserRoleMapper.querySysUserRoles(sysUserRole);	//开始查询，没有条件则查询所有用户角色
		PageInfo<HashMap<String, Object>> sysUserRolesForPageInfo = new PageInfo<>(sysUserRolesMap);
		if (sysUserRolesForPageInfo != null && sysUserRolesForPageInfo.getList() != null && sysUserRolesForPageInfo.getList().size() > 0) {	//是否查询到数据
			return R.ok().setData(sysUserRolesForPageInfo).setMsg("查询用户角色成功");
		} else {
			return R.ok().setMsg("没有查询到用户角色信息");
		}
    }
    
    /**
     * 查询用户角色
     * @param sysUserRole 条件
     * @return	查询得到的用户角色
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R querySysUserRolesNotPage(SysUserRole sysUserRole) throws Exception {
		List<HashMap<String, Object>> sysUserRolesMap = sysUserRoleMapper.querySysUserRoles(sysUserRole);	//开始查询，没有条件则查询所有用户角色
		if (sysUserRolesMap != null && sysUserRolesMap.size() > 0) {	//是否查询到数据
			return R.ok().setData(sysUserRolesMap).setMsg("查询用户角色成功");
		} else {
			return R.ok().setMsg("没有查询到用户角色信息");
		}
    }
    
    /**
     * 删除用户角色
     * @param sysUserRole 要删除的用户角色
     * @return	
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteSysUserRole(SysUserRole sysUserRole) throws Exception {
		if(sysUserRole != null) {
			int count = 0;
			if (sysUserRole.getId() == null) {
				throw new Exception();	//删除用户角色一定需要uid，依据uid进行用户角色的删除
			}
			count += this.deleteByPrimaryKey(sysUserRole.getId());	//开始删除用户角色信息
			
			if (count == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除用户角色 " + count + "条。");
			} else {	//没有受影响行数或者受影响行数与要删除的用户角色数量不匹配表示删除失败
				throw new Exception();
			}
		} else {	//删除一定需要用户角色
			throw new Exception();
		}
    }
    
    /**
     * 根据用户id删除用户角色
     * @param sysUserRole 要删除的用户角色
     * @return	
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteSysUserRoleByUserId(SysUserRole sysUserRole) throws Exception {
		if(sysUserRole != null) {
			int count = 0;
			if (sysUserRole.getUserId() == null) {
				throw new Exception();	//删除用户角色一定需要角色id，依据角色id进行用户角色的删除
			}
			count = sysUserRoleMapper.deleteSysUserRolesByUserId(sysUserRole.getUserId());	//开始删除用户角色信息
			return R.ok().setData(count).setMsg("共计删除用户角色 " + count + "条。");
		} else {	//删除一定需要用户角色
			throw new Exception();
		}
    }
    
    /**
     * 新增用户角色
     * @param sysUserRole 要新增的用户角色
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R insertSysUserRole(SysUserRole sysUserRole) throws Exception {
		if (sysUserRole != null && sysUserRole.getUserId() != null) {
			//新增用户角色前先删除此用户的所有角色
			this.deleteSysUserRoleByUserId(sysUserRole);
			if (StringUtil.isNotEmpty(sysUserRole.getRoles()) && !sysUserRole.getRoles().equals(",")) {
				int count = 0;
				String[] roles = sysUserRole.getRoles().split(",");
				if (roles != null && roles.length > 0) {
					for (String roleId : roles) {
						sysUserRole.setRoleId(Long.parseLong(roleId));
						count += this.insertSelective(sysUserRole); //开始添加用户角色信息
					}
				}
				if (count == roles.length) { //受影响的行数，判断是否修改成功
					return R.ok().setMsg("用户角色变更成功。");
				} else { //没有受影响行数表示添加失败
					throw new Exception();
				} 
			}
			return R.ok().setMsg("用户角色变更成功。");
		} else {	//添加一定需要一个用户角色信息
			throw new Exception();
		}
    }

}
