package com.zltel.broadcast.um.service.impl;

import java.util.Date;
import java.util.HashMap;
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
import com.zltel.broadcast.um.dao.OrganizationJoinProcessMapper;
import com.zltel.broadcast.um.dao.PartyUserInfoMapper;
import com.zltel.broadcast.um.service.JoinPartyOrgStepService;
import com.zltel.broadcast.um.util.DateUtil;

@Service
public class JoinPartyOrgStepServiceImpl extends BaseDaoImpl<JoinPartyOrgStep> implements JoinPartyOrgStepService {
	@Resource
    private JoinPartyOrgStepMapper joinPartyOrgStepMapper;
	@Resource
    private JoinPartyOrgFileMapper joinPartyOrgFileMapper;
	@Resource
    private JoinPartyOrgUserMapper joinPartyOrgUserMapper;
	@Resource
    private OrganizationJoinProcessMapper organizationJoinProcessMapper;
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
     * 查询申请入党人员的信息
     * @param conditions
     * @return
     */
    public R queryUserJoinPartyOrgSteps(Map<String, Object> conditions) {
    	List<Map<String, Object>> joinPartyOrgSteps = joinPartyOrgStepMapper.queryUserJoinPartyOrgSteps(conditions);
		if (joinPartyOrgSteps != null && joinPartyOrgSteps.size() > 0) {
			for (Map<String, Object> joinPartyOrgStep : joinPartyOrgSteps) {
				joinPartyOrgStep.put("stepTime", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD_HH_MM_SS, joinPartyOrgStep.get("stepTime") == null ||
					joinPartyOrgStep.get("stepTime") == "" ? null : 
					DateUtil.toDate(DateUtil.YYYY_MM_DD_HH_MM_SS, joinPartyOrgStep.get("stepTime").toString())));
				JoinPartyOrgFile jpof = new JoinPartyOrgFile();
				jpof.setStepId(Integer.parseInt(String.valueOf(joinPartyOrgStep.get("id"))));
				List<JoinPartyOrgFile> stepFiles = joinPartyOrgFileMapper.queryJoinPartyOrgFiles(jpof);
				joinPartyOrgStep.put("stepFiles", stepFiles);
			}
			return R.ok().setData(joinPartyOrgSteps).setMsg("查询成功");
		}
		return R.ok().setMsg("没有查询到信息");
    }
	
	/**
     * 变更此步骤的信息
     * @param jpos
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R updateJoinPartyOrgSteps(JoinPartyOrgStep jpos) {
		//当预备党员，积极份子等同意时，要更新用户信息
    	joinPartyOrgStepMapper.updateByPrimaryKeySelective(jpos);	//更新步骤信息
    	
    	//得到用户入党信息
    	JoinPartyOrgUser jpou = joinPartyOrgUserMapper.selectByPrimaryKey(jpos.getJoinId());
    	if ("error".equals(jpos.getStepStatus())) {
    		jpou.setJoinStatus(jpos.getStepStatus());
    		BaseUserInfo bui = new BaseUserInfo();
			bui.setBaseUserId(jpou.getUserId());
			bui.setPositiveUser(0);	//取消积极分子
			bui.setDevPeople(0);	//取消发展对象
			bui.setIsParty(0);		//变成非党员
			baseUserInfoMapper.updateByPrimaryKeySelective(bui);
			partyUserInfoMapper.deleteByPrimaryKey(jpou.getUserId());	//删除党员信息
    	} else if ("success".equals(jpos.getStepStatus())) {
    		/**
    		 * 状态设置为空，并且进入到下一步骤
    		 */
    		
    		Map<String, Object> condition = new HashMap<>();
    		condition.put("orgId", jpou.getJoinOrgId());
    		condition.put("processId", jpos.getProcessId());
    		//步骤id取到步骤数
    		List<Map<String, Object>> orgJoinProcess = organizationJoinProcessMapper.queryOrgOjp(condition);
    		if (orgJoinProcess == null || orgJoinProcess.size() != 1) {
    			return R.error().setMsg("变更失败");
    		}
    		condition.remove("processId");
    		condition.put("indexNum", Integer.parseInt(String.valueOf(orgJoinProcess.get(0).get("indexNum"))) + 1);
    		//得到下个步骤数的步骤id
    		orgJoinProcess = organizationJoinProcessMapper.queryOrgOjp(condition);
    		if (orgJoinProcess == null || orgJoinProcess.size() != 1) {
    			return R.error().setMsg("变更失败");
    		}
    		jpou.setNowStep(Integer.parseInt(String.valueOf(orgJoinProcess.get(0).get("processId"))));
    		if ((boolean) orgJoinProcess.get(0).get("isFile")) {
    			jpou.setJoinStatus(null);
    		} else {
    			jpou.setJoinStatus("wait");
    			//如果不需要提交资料，直接进入下一步
    			JoinPartyOrgStep newJpos = new JoinPartyOrgStep();
    			newJpos.setJoinId(jpos.getJoinId());
    			newJpos.setProcessId(jpou.getNowStep());
    			newJpos.setStepStatus("wait");
    			newJpos.setTime(new Date());
    			joinPartyOrgStepMapper.insertSelective(newJpos);
    		}
    		
    		/**
    		 * 当进行到某些特定步骤时，更新人员状态
    		 */
    		BaseUserInfo bui = null;
    		PartyUserInfo pui = null;
    		switch (jpos.getProcessId()) {
				case 3:	//积极分子
					bui = new BaseUserInfo();
    				bui.setBaseUserId(jpou.getUserId());
    				bui.setPositiveUser(1);
    				baseUserInfoMapper.updateByPrimaryKeySelective(bui);
					break;
				case 9:	//发展对象
					bui = new BaseUserInfo();
    				bui.setBaseUserId(jpou.getUserId());
    				bui.setDevPeople(1);
    				baseUserInfoMapper.updateByPrimaryKeySelective(bui);
					break;
				case 14:	//预备党员
					bui = new BaseUserInfo();
    				bui.setBaseUserId(jpou.getUserId());
    				bui.setIsParty(1);
    				baseUserInfoMapper.updateByPrimaryKeySelective(bui);
    				
    				pui = new PartyUserInfo();
    				pui.setPartyUserId(jpou.getUserId());
    				pui.setType(0);	//0：预备党员
    				pui.setStatus(1);	//1：状态-正常
    				pui.setJoinDateReserve(new Date());
    				pui.setJoinPartyBranchTypeId(jpou.getJoinPartyType());
    				partyUserInfoMapper.insertSelective(pui);
					break;
				case 16:	//确认入党
    				jpou.setIsHistory(1);	//成为历史
    				
    				pui = new PartyUserInfo();
    				pui.setPartyUserId(jpou.getUserId());
    				pui.setType(1);	//1：正式党员
    				pui.setJoinDateFormal(new Date());
    				partyUserInfoMapper.updateByPrimaryKeySelective(pui);
					break;
			}
    	}
    	joinPartyOrgUserMapper.updateByPrimaryKey(jpou);
    	
    	return R.ok().setMsg("更新成功");
    }
}
