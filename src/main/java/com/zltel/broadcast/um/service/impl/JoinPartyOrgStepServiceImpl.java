package com.zltel.broadcast.um.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.BaseUserInfo;
import com.zltel.broadcast.um.bean.JoinPartyOrgFile;
import com.zltel.broadcast.um.bean.JoinPartyOrgStep;
import com.zltel.broadcast.um.bean.JoinPartyOrgUser;
import com.zltel.broadcast.um.bean.PartyUserInfo;
import com.zltel.broadcast.um.dao.BaseUserInfoMapper;
import com.zltel.broadcast.um.dao.JoinPartyOrgFileMapper;
import com.zltel.broadcast.um.dao.JoinPartyOrgStepMapper;
import com.zltel.broadcast.um.dao.JoinPartyOrgUserMapper;
import com.zltel.broadcast.um.dao.PartyUserInfoMapper;
import com.zltel.broadcast.um.service.JoinPartyOrgStepService;
import com.zltel.broadcast.um.util.DateUtil;

import io.netty.util.internal.StringUtil;

@Service
public class JoinPartyOrgStepServiceImpl extends BaseDaoImpl<JoinPartyOrgStep> implements JoinPartyOrgStepService {
	@Resource
    private JoinPartyOrgStepMapper joinPartyOrgStepMapper;
	@Resource
    private JoinPartyOrgFileMapper joinPartyOrgFileMapper;
	@Resource
    private JoinPartyOrgUserMapper joinPartyOrgUserMapper;
	@Resource
    private BaseUserInfoMapper baseUserInfoMapper;
	@Resource
    private PartyUserInfoMapper partyUserInfoMapper;
	@Override
    public BaseDao<JoinPartyOrgStep> getInstince() {
        return this.joinPartyOrgStepMapper;
    }
	
	/**
     * 查询申请入党人员进行步骤
     * @param conditions
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryJoinPartyOrgSteps(Map<String, Object> conditions) {
		List<Map<String, Object>> joinPartyOrgSteps = joinPartyOrgStepMapper.queryJoinPartyOrgSteps(conditions);
		if (joinPartyOrgSteps != null && joinPartyOrgSteps.size() == 1) {
			for (Map<String, Object> joinPartyOrgStep : joinPartyOrgSteps) {
				joinPartyOrgStep.put("stepTime", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD_HH_MM_SS, joinPartyOrgSteps.get(0).get("stepTime") == null ||
					joinPartyOrgSteps.get(0).get("stepTime") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD_HH_MM_SS, joinPartyOrgSteps.get(0).get("stepTime").toString())));
				JoinPartyOrgFile jpof = new JoinPartyOrgFile();
				jpof.setStepId(Integer.parseInt(String.valueOf(joinPartyOrgStep.get("id"))));
				List<JoinPartyOrgFile> stepFiles = joinPartyOrgFileMapper.queryJoinPartyOrgFiles(jpof);
				joinPartyOrgStep.put("stepFiles", stepFiles);
			}
			return R.ok().setData(joinPartyOrgSteps.get(0)).setMsg("查询成功");
		} else {
			return R.ok().setMsg("没有查询到信息");
		}
    }
	
	/**
     * 变更此步骤的信息
     * @param jpos
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R updateJoinPartyOrgSteps(JoinPartyOrgStep jpos) {
    	joinPartyOrgStepMapper.updateByPrimaryKeySelective(jpos);
    	if (!StringUtil.isNullOrEmpty(jpos.getStepStatus())) {
    		if ("error".equals(jpos.getStepStatus())) {	//其中一步失败，整个申请过程都失败
    			JoinPartyOrgUser jpou = new JoinPartyOrgUser();
    			jpou.setId(jpos.getJoinId());
    			jpou.setJoinStatus("error");
    			joinPartyOrgUserMapper.updateByPrimaryKeySelective(jpou);
    		} else if ("success".equals(jpos.getStepStatus())) {
    			if (jpos.getProcessId() == 2 || jpos.getProcessId() == 3 || jpos.getProcessId() == 1
    					|| jpos.getProcessId() == 4 || jpos.getProcessId() == 6 || jpos.getProcessId() == 5) {
    				//遇到这些步骤通过之后自动进入下一步骤，不需要申请人在提交资料
    				JoinPartyOrgStep newJpos = new JoinPartyOrgStep();
    				newJpos.setJoinId(jpos.getJoinId());
    				newJpos.setProcessId(jpos.getProcessId() + 1);
    				newJpos.setStepStatus("wait");
    				newJpos.setStepStatusReason("审核中...");
    				newJpos.setTime(new Date());
    				joinPartyOrgStepMapper.insertSelective(newJpos);
    				
    				JoinPartyOrgUser jpou = new JoinPartyOrgUser();
        			jpou.setId(jpos.getJoinId());
        			jpou.setNowStep(newJpos.getProcessId());
        			joinPartyOrgUserMapper.updateByPrimaryKeySelective(jpou);
    			} else if (jpos.getProcessId() == 8) {
    				JoinPartyOrgUser jpou = new JoinPartyOrgUser();
        			jpou.setId(jpos.getJoinId());
        			jpou.setJoinStatus(jpos.getStepStatus());
        			joinPartyOrgUserMapper.updateByPrimaryKeySelective(jpou);
    			}
    			
    			//此时成为积极党员
    			if (jpos.getProcessId() == 3) {
    				JoinPartyOrgUser jpou = joinPartyOrgUserMapper.selectByPrimaryKey(jpos.getJoinId());
    				BaseUserInfo bui = new BaseUserInfo();
    				bui.setBaseUserId(jpou.getUserId());
    				bui.setPositiveUser(1);
    				baseUserInfoMapper.updateByPrimaryKeySelective(bui);
    			} else if(jpos.getProcessId() == 5) {	//发展对象
    				JoinPartyOrgUser jpou = joinPartyOrgUserMapper.selectByPrimaryKey(jpos.getJoinId());
    				BaseUserInfo bui = new BaseUserInfo();
    				bui.setBaseUserId(jpou.getUserId());
    				bui.setDevPeople(1);
    				baseUserInfoMapper.updateByPrimaryKeySelective(bui);
    			} else if (jpos.getProcessId() == 6) {	//预备党员
    				JoinPartyOrgUser jpou = joinPartyOrgUserMapper.selectByPrimaryKey(jpos.getJoinId());
    				BaseUserInfo bui = new BaseUserInfo();
    				bui.setBaseUserId(jpou.getUserId());
    				bui.setIsParty(1);
    				baseUserInfoMapper.updateByPrimaryKeySelective(bui);
    				
    				PartyUserInfo pui = new PartyUserInfo();
    				pui.setPartyUserId(jpou.getUserId());
    				pui.setType(0);	//0：预备党员
    				pui.setStatus(1);	//1：状态-正常
    				pui.setJoinDateReserve(new Date());
    				pui.setJoinPartyBranchTypeId(jpou.getJoinPartyType());
    				partyUserInfoMapper.insertSelective(pui);
    			} else if (jpos.getProcessId() == 8) {	//正式党员
    				JoinPartyOrgUser jpou = new JoinPartyOrgUser();
    				jpou.setId(jpos.getJoinId());
    				jpou.setIsHistory(1);
    				joinPartyOrgUserMapper.updateByPrimaryKeySelective(jpou);
    				
    				jpou = joinPartyOrgUserMapper.selectByPrimaryKey(jpos.getJoinId());
    				
    				PartyUserInfo pui = new PartyUserInfo();
    				pui.setPartyUserId(jpou.getUserId());
    				pui.setType(1);	//1：正式党员
    				pui.setJoinDateFormal(new Date());
    				partyUserInfoMapper.updateByPrimaryKeySelective(pui);
    			}
    		}
    	}
    	
    	return R.ok().setMsg("更新成功");
    }
}
