package com.zltel.broadcast.um.service.impl;

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
import com.zltel.broadcast.um.bean.BaseUser;
import com.zltel.broadcast.um.bean.PartyUser;
import com.zltel.broadcast.um.dao.PartyUserMapper;
import com.zltel.broadcast.um.service.BaseUserService;
import com.zltel.broadcast.um.service.PartyUserService;

@Service
public class PartyUserServiceImpl extends BaseDaoImpl<PartyUser> implements PartyUserService {
	@Resource
    private PartyUserMapper partyUserMapper;
	
	@Autowired
	private BaseUserService baseUserService;

    @Override
    public BaseDao<PartyUser> getInstince() {
        return this.partyUserMapper;
    }
    
    /**
     * 查询党员用户信息
     * @param partyUser 条件
     * @return	查询得到的党员用户
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R queryPartyUsers(PartyUser partyUser, int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<PartyUser> partyUsers = partyUserMapper.queryPartyUsers(partyUser);	//开始查询，没有条件则查询所有党员用户
		PageInfo<PartyUser> partyUsersForPageInfo = new PageInfo<>(partyUsers);
		if (partyUsersForPageInfo != null && partyUsersForPageInfo.getList() != null && partyUsersForPageInfo.getList().size() > 0) {	//是否查询到数据
			/**
			 * 是否是优秀党员这些信息目前用0或1表示，在前台转换，后台不处理
			 */
			return R.ok().setData(partyUsersForPageInfo).setMsg("查询党员用户信息成功");
		} else {
			return R.ok().setMsg("没有查询到党员用户");
		}
    }
    
    /**
     * 查询党员用户信息
     * @param partyUser 条件
     * @return	查询得到的党员用户
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R queryPartyUsersNotPage(PartyUser partyUser) throws Exception {
		List<PartyUser> partyUsers = partyUserMapper.queryPartyUsers(partyUser);	//开始查询，没有条件则查询所有党员用户
		if (partyUsers != null &&  partyUsers.size() > 0) {	//是否查询到数据
			/**
			 * 是否是优秀党员这些信息目前用0或1表示，在前台转换，后台不处理
			 */
			return R.ok().setData(partyUsers).setMsg("查询党员用户信息成功");
		} else {
			return R.ok().setMsg("没有查询到党员用户");
		}
    }
    
    /**
     * 修改党员用户信息
     * @param partyUser 要修改的党员用户
     * @return	
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R updatePartyUser(PartyUser partyUser) throws Exception {
		if (partyUser != null) {
			int count = this.updateByPrimaryKeySelective(partyUser);	//开始修改党员用户信息
			if (count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("党员用户信息修改成功。");
			} else {	//没有受影响行数表示修改失败
				throw new Exception();
			}
		} else {	//修改用户一定需要一个用户信息
			throw new Exception();
		}
    }
    
    /**
     * 删除党员用户
     * @param partyUser 要删除的党员用户
     * @return	
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R deletePartyUser(PartyUser partyUser) throws Exception {
		if(partyUser != null) {
			int count = this.deleteByPrimaryKey(partyUser.getUid());	//开始删除党员用户信息
			BaseUser bu = new BaseUser();
			bu.setUid(partyUser.getUid());
			bu.setIsParty(0);
			int countBu = baseUserService.updateByPrimaryKeySelective(bu); //修改基础信息表中党员标识为0，代表该成员已经不是党员
			
			if (countBu == 1 && count == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除党员用户 " + count + "条。");
			} else {	//没有受影响行数或者受影响行数与要删除的用户数量不匹配表示删除失败
				throw new Exception();
			}
		} else {	//删除用户一定需要一个用户信息
			throw new Exception();
		}
    }
    
    /**
     * 新增党员用户
     * @param partyUser 要新增的党员用户
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R insertPartyUser(PartyUser partyUser) throws Exception {
		if (partyUser != null) {
			int count = this.insertSelective(partyUser);	//开始添加党员用户信息
			BaseUser bu = new BaseUser();
			bu.setUid(partyUser.getUid());
			bu.setIsParty(1);
			int countBu = baseUserService.updateByPrimaryKeySelective(bu); //修改基础信息表中党员标识为1，代表该成员为党员
			if (countBu == 1 && count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("党员用户添加成功。");
			} else {	//没有受影响行数表示添加失败
				throw new Exception();
			}
		} else {	//添加用户一定需要一个用户信息
			throw new Exception();
		}
    }

    
    
    
    
    
    
    
    
	public BaseUserService getBaseUserService() {
		return baseUserService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}
}
