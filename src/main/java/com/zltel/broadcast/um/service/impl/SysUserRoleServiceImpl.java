package com.zltel.broadcast.um.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.zltel.broadcast.common.exception.RRException;
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
    @Transactional
    public R querySysUserRoles(Map<String, Object> conditions, int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<HashMap<String, Object>> sysUserRolesMap = sysUserRoleMapper.querySysUserRoles(conditions);	//开始查询，没有条件则查询所有用户角色
		PageInfo<HashMap<String, Object>> sysUserRolesForPageInfo = new PageInfo<>(sysUserRolesMap);
		if (sysUserRolesForPageInfo.getList() != null && !sysUserRolesForPageInfo.getList().isEmpty()) {	//是否查询到数据
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
    @Transactional
    public R querySysUserRolesNotPage(Map<String, Object> conditions) throws Exception {
		List<HashMap<String, Object>> sysUserRolesMap = sysUserRoleMapper.querySysUserRoles(conditions);	//开始查询，没有条件则查询所有用户角色
		if (sysUserRolesMap != null && !sysUserRolesMap.isEmpty()) {	//是否查询到数据
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
    @Transactional
    public R deleteSysUserRole(SysUserRole sysUserRole) throws Exception {
		if(sysUserRole != null) {
			int count = 0;
			if (sysUserRole.getId() == null) {
				//删除用户角色一定需要uid，依据uid进行用户角色的删除
				throw new RRException("删除用户角色一定需要uid，依据uid进行用户角色的删除");
			}
			count += this.deleteByPrimaryKey(sysUserRole.getId());	//开始删除用户角色信息
			
			if (count == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除用户角色 " + count + "条。");
			} else {	//没有受影响行数或者受影响行数与要删除的用户角色数量不匹配表示删除失败
			    throw new RRException("没有受影响行数或者受影响行数与要删除的用户角色数量不匹配表示删除失败");
			}
		} else {	//删除一定需要用户角色
		    throw new RRException("删除一定需要用户角色");
		}
    }
    
    /**
     * 根据用户id删除用户角色
     * @param sysUserRole 要删除的用户角色
     * @return	
     */
    @Override
    @Transactional
    public R deleteSysUserRoleByUserId(SysUserRole sysUserRole) {
		if(sysUserRole != null) {
			int count = 0;
			if (sysUserRole.getUserId() == null) {
				throw new RRException("删除用户角色一定需要角色id，依据角色id进行用户角色的删除");	//删除用户角色一定需要角色id，依据角色id进行用户角色的删除
			}
			count = sysUserRoleMapper.deleteSysUserRolesByUserId(sysUserRole.getUserId());	//开始删除用户角色信息
			return R.ok().setData(count).setMsg("共计删除用户角色 " + count + "条。");
		} else {	//删除一定需要用户角色
		    throw new RRException("删除一定需要用户角色");
		}
    }
    
    /**
     * 新增用户角色
     * @param sysUserRole 要新增的用户角色
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public R insertSysUserRole(SysUserRole sysUserRole) {
		if (sysUserRole != null && sysUserRole.getUserId() != null) {
			Map<String, Object> conditions = new HashMap<>();
			conditions.put("userId", sysUserRole.getUserId());
			conditions.put("isShow", 1);
			List<HashMap<String, Object>> sysUserRolesMap = sysUserRoleMapper.querySysUserRoles(conditions);
			List<String> haveRoles = new ArrayList<>();	//已经存在的角色
			if (sysUserRolesMap != null && sysUserRolesMap.size() > 0) {
				for (HashMap<String, Object> surMap : sysUserRolesMap) {
					haveRoles.add(String.valueOf(surMap.get("roleId")));
				}
			}
			
			int count = 0;
			String[] roles = sysUserRole.getRoles().split(",");	//要添加的角色
			
			List<String> deleteRoles = new ArrayList<>();
			List<String> insertRoles = new ArrayList<>();
			Map<String, Integer> bj = new HashMap<>();
			//不能删除全部角色在添加，因为涉及到添加内置角色，会删除内置角色，这不是想要的结果，内置角色不会被查询出
			if (haveRoles != null && haveRoles.size() > 0) {
				for (String string : haveRoles) {
					bj.put(string, 1);
				}
			}
			if (roles != null && roles.length > 0) {
				for (int i = 0; i < roles.length; i++) {
					if (StringUtil.isEmpty(roles[i])) continue;
					Integer c = bj.get(roles[i]);
					if (c == null) {	//在bj没找到则是要添加的角色
						insertRoles.add(roles[i]);
					} else {	//找到就是已经存在的，++c
						bj.put(roles[i], ++c);
					}
				}
			}
			if (bj != null && bj.entrySet().size() > 0) {
				for (Map.Entry<String, Integer> entry : bj.entrySet()) {
					if (entry.getValue() == 1) {	//bj里不变的说明没有roles光顾，则为要删除的角色
						deleteRoles.add(entry.getKey());
					}
				}
			}
			
			if (deleteRoles != null && deleteRoles.size() > 0) {
				for (String string : deleteRoles) {
					SysUserRole bean = new SysUserRole();
					bean.setUserId(sysUserRole.getUserId());
					bean.setRoleId(Long.parseLong(string));
					sysUserRoleMapper.deleteSysUserRolesByUpdateSysUserRole(bean);
				}
			}
			if (insertRoles != null && insertRoles.size() > 0) {
				for (String string : insertRoles) {
					SysUserRole bean = new SysUserRole();
					bean.setUserId(sysUserRole.getUserId());
					bean.setRoleId(Long.parseLong(string));
					count += this.insertSelective(bean); //开始添加用户角色信息
				}
			}
			if (count == (insertRoles == null ? -1 : insertRoles.size())) { //受影响的行数，判断是否修改成功
				return R.ok().setMsg("用户角色变更成功。");
			} else { //没有受影响行数表示添加失败
				throw new RRException("没有受影响行数表示添加失败");
			} 
		} else {	//添加一定需要一个用户角色信息
			throw new RRException("添加一定需要一个用户角色信息");
		}
    }
    
    /**
     * 变更内置角色
     * @param conditions
     * @return
     */
    public R updateInnerManageRols(Map<String, Object> conditions) {
    	if (conditions == null) 
    		return R.error().setMsg("发生错误");
    	Map<String, Object> queryConditions = new HashMap<>();
    	if (conditions.get("userId") == null || conditions.get("userId") == "") {
    		throw new RuntimeException();
    	}
    	if (conditions.get("isShow") == null || conditions.get("isShow") == "") {
    		throw new RuntimeException();
    	}
    	queryConditions.put("userId", conditions.get("userId"));
    	queryConditions.put("isShow", conditions.get("isShow"));
    	List<HashMap<String, Object>> haveInnerManageRoles = sysUserRoleMapper.querySysUserRoles(queryConditions);
    	if (haveInnerManageRoles != null && haveInnerManageRoles.size() > 0) {
    		for (HashMap<String, Object> haveInnerManageRole : haveInnerManageRoles) {
    			sysUserRoleMapper.deleteByPrimaryKey(Long.parseLong(String.valueOf(haveInnerManageRole.get("id"))));
			}
    	}
    	if (conditions.get("roleId") != null && conditions.get("roleId") != "") {
    		SysUserRole sur = new SysUserRole();
    		sur.setRoleId(Long.parseLong(String.valueOf(conditions.get("roleId"))));
    		sur.setUserId(Long.parseLong(String.valueOf(conditions.get("userId"))));
    		sysUserRoleMapper.insertSelective(sur);
    	}
    	return R.ok().setMsg("变更成功");
    }

}
