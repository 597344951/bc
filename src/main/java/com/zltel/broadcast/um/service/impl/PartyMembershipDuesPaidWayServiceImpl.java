package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.PartyMembershipDuesPaidWay;
import com.zltel.broadcast.um.dao.PartyMembershipDuesPaidWayMapper;
import com.zltel.broadcast.um.service.PartyMembershipDuesPaidWayService;

@Service
public class PartyMembershipDuesPaidWayServiceImpl extends BaseDaoImpl<PartyMembershipDuesPaidWay> implements PartyMembershipDuesPaidWayService {
	@Resource
    private PartyMembershipDuesPaidWayMapper partyMembershipDuesPaidWayMapper;
	
	@Override
    public BaseDao<PartyMembershipDuesPaidWay> getInstince() {
        return this.partyMembershipDuesPaidWayMapper;
    }
	
	/**
     * 查询党费缴纳方式
     * @param partyMembershipDuesPaidWay
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryPMDWs(PartyMembershipDuesPaidWay partyMembershipDuesPaidWay) {
		List<PartyMembershipDuesPaidWay> partyMembershipDuesPaidWays = partyMembershipDuesPaidWayMapper.queryPMDWs(partyMembershipDuesPaidWay);	//开始查询，没有条件则查询所有基础用户信息
		if (partyMembershipDuesPaidWays != null && partyMembershipDuesPaidWays.size() > 0) {	//是否查询到数据
			return R.ok().setData(partyMembershipDuesPaidWays).setMsg("查询党费缴纳方式成功");
		} else {
			return R.ok().setMsg("没有查询到党费缴纳方式");
		}
    }
}
