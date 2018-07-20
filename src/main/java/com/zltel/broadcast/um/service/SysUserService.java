package com.zltel.broadcast.um.service;

import java.util.List;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.SysUser;

public interface SysUserService {
    int deleteByPrimaryKey(Integer userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    // ---------------------------
    /** 根据用户名查询 用户信息 **/
    SysUser selectByUserName(String username);

    /** 查询指定用户权限信息 **/
    List<String> queryAllPerms(Integer userId);

    /** 查询指定用户角色信息 **/
    List<String> queryAllRoles(Integer userId);

    /** 更新指定用户密码信息 **/
    int updatePassword(Integer userId, String password, String newPassword);


    /**
     * 查询系统用户信息
     * 
     * @param sysUser 条件
     * @return 查询得到的系统用户
     */
    public R querySysUsers(SysUser sysUser, int pageNum, int pageSize) throws Exception;

    /**
     * 查询系统用户信息
     * 
     * @param sysUser 条件
     * @return 查询得到的系统用户
     */
    public R querySysUsersNotPage(SysUser sysUser) throws Exception;

    /**
     * 修改系统用户信息
     * 
     * @param sysUser 要修改的系统用户
     * @return
     */
    public R updateSysUser(SysUser sysUser) throws Exception;

    /**
     * 修改系统用户密码
     * 
     * @param sysUser 要修改的系统用户
     * @return
     */
    public R updateSysUserPwd(SysUser sysUser) throws Exception;

    /**
     * 删除系统用户
     * 
     * @param sysUsers 要删除的系统用户
     * @return
     */
    public R deleteSysUser(SysUser sysUser) throws Exception;

    /**
     * 新增系统用户
     * 
     * @param sysUser 要新增的系统用户
     * @return
     * @throws Exception
     */
    public R insertSysUser(SysUser sysUser) throws Exception;

    /** 禁用指定账户 **/
    void disableUser(String username);

}
