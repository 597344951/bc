package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.PartyMembershipDuesStatus;
import com.zltel.broadcast.um.dao.PartyMembershipDuesStatusMapper;
import com.zltel.broadcast.um.service.PartyMembershipDuesStatusService;

@Service
public class PartyMembershipDuesStatusServiceImpl extends BaseDaoImpl<PartyMembershipDuesStatus> implements PartyMembershipDuesStatusService {
	@Resource
    private PartyMembershipDuesStatusMapper partyMembershipDuesStatusMapper;
	
	@Override
    public BaseDao<PartyMembershipDuesStatus> getInstince() {
        return this.partyMembershipDuesStatusMapper;
    }
	
	/**
     * 查询党费缴纳状态
     * @param partyMembershipDuesStatus
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryPMDSs(PartyMembershipDuesStatus partyMembershipDuesStatus) {
		List<PartyMembershipDuesStatus> partyMembershipDuesStatuss = partyMembershipDuesStatusMapper.queryPMDSs(partyMembershipDuesStatus);	//开始查询，没有条件则查询所有基础用户信息
		if (partyMembershipDuesStatuss != null && partyMembershipDuesStatuss.size() > 0) {	//是否查询到数据
			return R.ok().setData(partyMembershipDuesStatuss).setMsg("查询党费缴纳状态成功");
		} else {
			return R.ok().setMsg("没有查询到党费缴纳状态");
		}
    }
}
