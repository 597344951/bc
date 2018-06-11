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
import com.zltel.broadcast.um.bean.OrganizationRelation;
import com.zltel.broadcast.um.dao.BaseUserMapper;
import com.zltel.broadcast.um.service.BaseUserService;
import com.zltel.broadcast.um.service.OrganizationRelationService;
import com.zltel.broadcast.um.service.PartyUserService;

@Service
public class BaseUserServiceImpl extends BaseDaoImpl<BaseUser> implements BaseUserService {
	@Resource
    private BaseUserMapper baseUserMapper;
	
	@Autowired
	private PartyUserService partyUserService;
	
	@Autowired
	private OrganizationRelationService organizationRelationService;

    @Override
    public BaseDao<BaseUser> getInstince() {
        return this.baseUserMapper;
    }
    
    
    /**
     * 查询基础用户信息
     * @param baseUser 条件
     * @return	查询得到的基础用户
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryBaseUsers(BaseUser baseUser, int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<BaseUser> baseUsers = baseUserMapper.queryBaseUsers(baseUser);	//开始查询，没有条件则查询所有基础用户信息
		PageInfo<BaseUser> baseUsersForPageInfo = new PageInfo<>(baseUsers);
		if (baseUsersForPageInfo != null && baseUsersForPageInfo.getList().size() > 0) {	//是否查询到数据
			/**
			 * 男女性别这些信息目前用0或1表示，在前台转换，后台不处理
			 */
			return R.ok().setData(baseUsersForPageInfo).setMsg("查询基础用户信息成功");
		} else {
			return R.ok().setMsg("没有查询到基础用户信息");
		}
    }
    
    /**
     * 查询基础用户信息
     * @param baseUser 条件
     * @return	查询得到的基础用户
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryBaseUsersNotPage(BaseUser baseUser) throws Exception {
		List<BaseUser> baseUsers = baseUserMapper.queryBaseUsers(baseUser);	//开始查询，没有条件则查询所有基础用户信息
		if (baseUsers != null && baseUsers.size() > 0) {	//是否查询到数据
			/**
			 * 男女性别这些信息目前用0或1表示，在前台转换，后台不处理
			 */
			return R.ok().setData(baseUsers).setMsg("查询基础用户信息成功");
		} else {
			return R.ok().setMsg("没有查询到基础用户信息");
		}
    }
    
    /**
     * 修改基础用户信息
     * @param baseUser 要修改的基础用户
     * @return	
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R updateBaseUser(BaseUser baseUser) throws Exception {
		if (baseUser != null) {
			int count = this.updateByPrimaryKeySelective(baseUser);	//开始修改基础用户信息
			if (count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("基础用户信息修改成功。");
			} else {
				throw new Exception();	//没有受影响行数表示修改失败
			}
		} else {
			throw new Exception();	//修改一定需要一个用户信息
		}
    }
    
    /**
     * 删除基础用户
     * @param baseUser 要删除的基础用户
     * @return	
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteBaseUser(BaseUser baseUser) throws Exception {
		if(baseUser != null) {
			int count = 0;
			int partyCount = 0;
			int countOrgRelation = 0;
			if (baseUser.getUid() == null) {
				throw new Exception();	//删除用户一定需要uid，依据uid进行用户的删除
			}
			partyCount = partyUserService.deleteByPrimaryKey(baseUser.getUid());	//同步删除党员
			count = this.deleteByPrimaryKey(baseUser.getUid());	//开始删除基础用户信息
			//对应的组织关系也要删除
			OrganizationRelation or = new OrganizationRelation();
			or.setOrgRltUserId(baseUser.getUid());
			countOrgRelation = (int)organizationRelationService.deleteOrgRelationByUserId(or).get("data");
			
			if (count == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除基础用户 " + count + "条，党员" + partyCount + "条，组织关系" + countOrgRelation + "条。");
			} else {	//没有受影响行数或者受影响行数与要删除的用户数量不匹配表示删除失败
				throw new Exception();
			}
		} else {	//删除一定需要用户
			throw new Exception();
		}
    }
    
    /**
     * 新增基础用户
     * @param baseUser 要新增的基础用户
     * @return
     * @throws Exception
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertBaseUser(BaseUser baseUser) throws Exception {
		if (baseUser != null) {
			baseUser.setUid(null);	//自增，不需要设置值
			int count = this.insertSelective(baseUser);	//开始添加基础用户信息
			if (count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("基础用户添加成功。");
			} else {	//没有受影响行数表示添加失败
				throw new Exception();
			}
		} else {	//添加一定需要一个用户信息
			throw new Exception();
		}
    }


    
    
    
    
	public PartyUserService getPartyUserService() {
		return partyUserService;
	}


	public void setPartyUserService(PartyUserService partyUserService) {
		this.partyUserService = partyUserService;
	}
}
