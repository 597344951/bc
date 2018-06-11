package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.JoinPartyBranchType;
import com.zltel.broadcast.um.dao.JoinPartyBranchTypeMapper;
import com.zltel.broadcast.um.service.JoinPartyBranchTypeService;

@Service
public class JoinPartyBranchTypeServiceImpl extends BaseDaoImpl<JoinPartyBranchType> implements JoinPartyBranchTypeService {
	@Resource
    private JoinPartyBranchTypeMapper joinPartyBranchTypeMapper;
	
	@Override
    public BaseDao<JoinPartyBranchType> getInstince() {
        return this.joinPartyBranchTypeMapper;
    }
	
	/**
     * 查询加入党支部方式
     * @param joinPartyBranchType
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryJoinPartyBranchTypes(JoinPartyBranchType joinPartyBranchType) throws Exception {
		List<JoinPartyBranchType> joinPartyBranchTypes = joinPartyBranchTypeMapper.queryJoinPartyBranchTypes(joinPartyBranchType);	//开始查询，没有条件则查询所有基础用户信息
		if (joinPartyBranchTypes != null && joinPartyBranchTypes.size() > 0) {	//是否查询到数据
			return R.ok().setData(joinPartyBranchTypes).setMsg("查询加入党支部方式成功");
		} else {
			return R.ok().setMsg("没有查询到加入党支部方式");
		}
    }
}
