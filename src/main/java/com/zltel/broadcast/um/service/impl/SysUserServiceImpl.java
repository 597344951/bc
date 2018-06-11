package com.zltel.broadcast.um.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.common.util.CacheUtil;
import com.zltel.broadcast.common.util.PasswordHelper;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.bean.SysUserRole;
import com.zltel.broadcast.um.dao.SysUserMapper;
import com.zltel.broadcast.um.service.SysUserRoleService;
import com.zltel.broadcast.um.service.SysUserService;

@Service
public class SysUserServiceImpl extends BaseDaoImpl<SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Autowired
	private SysUserRoleService sysUserRoleService;

    @Override
    public BaseDao<SysUser> getInstince() {
        return this.sysUserMapper;
    }

    @Override
    public SysUser selectByUserName(String username) {
        return this.sysUserMapper.selectByUserName(username);
    }

    @Override
    public List<String> queryAllPerms(Integer userId) {
        return this.sysUserMapper.queryAllPerms(userId);
    }

    @Override
    public List<String> queryAllRoles(Integer userId) {
        return this.sysUserMapper.queryAllRoles(userId);
    }

    @Override
    @Transactional
    public int updatePassword(Integer userId, String password, String newPassword) {
        SysUser record = new SysUser();
        SysUser su = this.selectByPrimaryKey(userId);
        if (su == null) throw new RuntimeException("用户不存在!");
        String salt = UUID.randomUUID().toString();
        String nps = PasswordHelper.encryptPassword(newPassword, salt);

        record.setUserId(userId);
        record.setPassword(nps);
        record.setSalt(salt);
        return this.sysUserMapper.updatePassword(record);
    }

    /**
     * 查询系统用户信息
     * @param sysUser 条件
     * @return	查询得到的系统用户
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public R querySysUsers(SysUser sysUser, int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<SysUser> sysUsers = sysUserMapper.querySysUsers(sysUser);	//开始查询，没有条件则查询所有系统用户
		PageInfo<SysUser> sysUsersForPageInfo = new PageInfo<>(sysUsers);
		if (sysUsersForPageInfo.getList() != null && sysUsersForPageInfo.getList().size() > 0) {	//是否查询到数据
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SysUser sysU : sysUsersForPageInfo.getList()) {
				sysU.setPassword("******");
				sysU.setSalt("******");
				sysU.setCreateTime(sdf.parse(sdf.format(sysU.getCreateTime())));
			}
			return R.ok().setData(sysUsersForPageInfo).setMsg("查询系统用户信息成功");
		} else {
			return R.ok().setMsg("没有查询到系统用户");
		}
	}
	
	/**
     * 查询系统用户信息
     * @param sysUser 条件
     * @return	查询得到的系统用户
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public R querySysUsersNotPage(SysUser sysUser) throws Exception {
		List<SysUser> sysUsers = sysUserMapper.querySysUsers(sysUser);	//开始查询，没有条件则查询所有系统用户
		if (sysUsers != null && sysUsers.size() > 0) {	//是否查询到数据
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (SysUser sysU : sysUsers) {
				sysU.setPassword("******");
				sysU.setSalt("******");
				sysU.setCreateTime(sdf.parse(sdf.format(sysU.getCreateTime())));
			}
			return R.ok().setData(sysUsers).setMsg("查询系统用户信息成功");
		} else {
			return R.ok().setMsg("没有查询到系统用户");
		}
	}
	
	/**
     * 修改系统用户信息
     * @param sysUser 要修改的系统用户
     * @return	
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R updateSysUser(SysUser sysUser) throws Exception {
		if (sysUser != null) {
			int count = this.updateByPrimaryKeySelective(sysUser);	//开始修改系统用户信息
			if (count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("系统用户信息修改成功。");
			} else {
				throw new Exception();	//没有受影响行数表示修改失败
			}
		} else {
			throw new Exception();	//修改一定需要一个用户信息
		}
    }
	
	/**
     * 修改系统用户密码
     * @param sysUser 要修改的系统用户
     * @return	
     */
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R updateSysUserPwd(SysUser sysUser) throws Exception {
		if (sysUser != null && StringUtil.isNotEmpty(sysUser.getPassword())) {
			String salt = UUID.randomUUID().toString();
			sysUser.setSalt(salt);	//保存盐
			sysUser.setPassword(PasswordHelper.encryptPassword(sysUser.getPassword(), salt));	//加密
			int count = this.updateByPrimaryKeySelective(sysUser);	//开始修改系统用户信息
			CacheUtil.clearAuthenticationCache(sysUser.getUsername());
			if (count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("系统用户信息修改成功。");
			} else {
				throw new Exception();	//没有受影响行数表示修改失败
			}
		} else {
			throw new Exception();	//修改一定需要一个用户信息
		}
    }
    
    /**
     * 删除系统用户（删除系统用户是也要将此用户拥有的角色删除）
     * @param sysUsers 要删除的系统用户
     * @return	
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteSysUser(SysUser sysUser) throws Exception {
		if(sysUser != null) {
			int count = 0;
			int countUserRole = 0;
			if (sysUser.getUserId() == null) {
				throw new Exception();	//删除用户一定需要uid，依据uid进行用户的删除
			}
			count += this.deleteByPrimaryKey(sysUser.getUserId());	//开始删除系统用户信息
			//删除此用户拥有的角色
			SysUserRole sur = new SysUserRole();
			sur.setUserId((long)sysUser.getUserId());
			countUserRole = (int)sysUserRoleService.deleteSysUserRoleByUserId(sur).get("data");
			
			if (count == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除系统用户 " + count + "条，此用户下的角色" + countUserRole + "条。");
			} else {	//没有受影响行数或者受影响行数与要删除的用户数量不匹配表示删除失败
				throw new Exception();
			}
		} else {	//删除一定需要用户
			throw new Exception();
		}
    }
    
    /**
     * 新增系统用户
     * @param sysUser 要新增的系统用户
     * @return
     * @throws Exception
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertSysUser(SysUser sysUser) throws Exception {
		if (sysUser != null) {
			sysUser.setUserId(null);	//自增，不需要设置值
			String salt = UUID.randomUUID().toString();
			sysUser.setSalt(salt);	//保存盐
			sysUser.setPassword(PasswordHelper.encryptPassword(sysUser.getPassword(), salt));	//加密
			sysUser.setStatus((byte)1);
			int count = this.insertSelective(sysUser);	//开始添加系统用户信息
			if (count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("系统用户添加成功。");
			} else {	//没有受影响行数表示添加失败
				throw new Exception();
			}
		} else {	//添加一定需要一个用户信息
			throw new Exception();
		}
    }
}
