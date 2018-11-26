package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.JoinPartyOrgProcess;
import com.zltel.broadcast.um.dao.JoinPartyOrgProcessMapper;
import com.zltel.broadcast.um.service.JoinPartyOrgProcessService;

@Service
public class JoinPartyOrgProcessServiceImpl extends BaseDaoImpl<JoinPartyOrgProcess> implements JoinPartyOrgProcessService {
	@Resource
    private JoinPartyOrgProcessMapper joinPartyOrgProcessMapper;
	@Override
    public BaseDao<JoinPartyOrgProcess> getInstince() {
        return this.joinPartyOrgProcessMapper;
    }
	
	
	/**
     * 查询加入党的步骤
     * @param jpop
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryJoinPartyOrgProcess(JoinPartyOrgProcess jpop) {
		List<JoinPartyOrgProcess> jpops = joinPartyOrgProcessMapper.queryJoinPartyOrgProcess(jpop);
		if (jpops != null && jpops.size() > 0) {
			return R.ok().setData(jpops).setMsg("查询入党步骤成功");
		} else {
			return R.ok().setMsg("没有查询到入党步骤");
		}
    }
}
