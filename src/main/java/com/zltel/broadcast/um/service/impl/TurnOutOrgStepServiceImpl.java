package com.zltel.broadcast.um.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.JoinPartyOrgFile;
import com.zltel.broadcast.um.bean.PartyUserInfo;
import com.zltel.broadcast.um.bean.TurnOutOrgFile;
import com.zltel.broadcast.um.bean.TurnOutOrgStep;
import com.zltel.broadcast.um.bean.TurnOutOrgUser;
import com.zltel.broadcast.um.dao.OrganizationTurnOutProcessMapper;
import com.zltel.broadcast.um.dao.PartyUserInfoMapper;
import com.zltel.broadcast.um.dao.TurnOutOrgFileMapper;
import com.zltel.broadcast.um.dao.TurnOutOrgProcessMapper;
import com.zltel.broadcast.um.dao.TurnOutOrgStepMapper;
import com.zltel.broadcast.um.dao.TurnOutOrgUserMapper;
import com.zltel.broadcast.um.service.TurnOutOrgStepService;
import com.zltel.broadcast.um.util.DateUtil;

@Service
public class TurnOutOrgStepServiceImpl extends BaseDaoImpl<TurnOutOrgStep> implements TurnOutOrgStepService {
	@Resource
    private TurnOutOrgStepMapper turnOutOrgStepMapper;
    @Resource
    private TurnOutOrgFileMapper turnOutOrgFileMapper;
    @Resource
    private TurnOutOrgUserMapper turnOutOrgUserMapper;
    @Resource
    private TurnOutOrgProcessMapper turnOutOrgProcessMapper;
    @Resource
    private PartyUserInfoMapper partyUserInfoMapper;
    @Resource
    private OrganizationTurnOutProcessMapper organizationTurnOutProcessMapper;
	@Override
    public BaseDao<TurnOutOrgStep> getInstince() {
        return this.turnOutOrgStepMapper;
    }
	
	
	@Override
	public R queryUserTurnOutOrgSteps(Map<String, Object> conditions) {
		List<Map<String, Object>> turnOutOrgSteps = turnOutOrgStepMapper.queryUserTurnOutOrgSteps(conditions);
		if (turnOutOrgSteps != null && turnOutOrgSteps.size() > 0) {
			for (Map<String, Object> turnOutOrgStep : turnOutOrgSteps) {
				turnOutOrgStep.put("stepTime", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD_HH_MM_SS, turnOutOrgStep.get("stepTime") == null ||
					turnOutOrgStep.get("stepTime") == "" ? null : 
					DateUtil.toDate(DateUtil.YYYY_MM_DD_HH_MM_SS, turnOutOrgStep.get("stepTime").toString())));
				
				TurnOutOrgFile toof = new TurnOutOrgFile();
				toof.setStepId(Integer.parseInt(String.valueOf(turnOutOrgStep.get("id"))));
				List<JoinPartyOrgFile> stepFiles = turnOutOrgFileMapper.queryTurnOutOrgFiles(toof);
				turnOutOrgStep.put("stepFiles", stepFiles);
			}
			return R.ok().setData(turnOutOrgSteps).setMsg("查询成功");
		}
		return R.ok().setMsg("没有查询到信息");
	}
	
	
	/**
     * 变更此步骤信息
     * @param toos
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R updateTurnOutOrgPartySteps(boolean haveThisOrg, TurnOutOrgStep toos) throws Exception {
		turnOutOrgStepMapper.updateByPrimaryKeySelective(toos);	//更新步骤信息
		
		//得到用户组织关系转移信息
		TurnOutOrgUser toou = turnOutOrgUserMapper.selectByPrimaryKey(toos.getTurnOutId());
		if ("error".equals(toos.getStepStatus())) {
			toou.setJoinStatus(toos.getStepStatus());
			toou.setIsHistory(true);
    	} else if ("success".equals(toos.getStepStatus())) {
    		if (!haveThisOrg) {	//转移非本平台组织流程走完时标注此用户为历史党员
    			PartyUserInfo pui = new PartyUserInfo();
    			pui.setPartyUserId(toou.getUserId());
    			pui.setStatus(2);
    			partyUserInfoMapper.updateByPrimaryKeySelective(pui);
    		}
    		/**
    		 * 状态设置为空，并且进入到下一步骤
    		 */
    		Map<String, Object> condition = new HashMap<>();
    		List<Map<String, Object>> turnOutOrgProcess = null;
    		if (toou.getTurnOutOrgId() != null) {
    			condition.put("orgId", toou.getTurnOutOrgId());
        		condition.put("processId", toos.getProcessId());
        		//步骤id取到步骤数
        		turnOutOrgProcess = organizationTurnOutProcessMapper.queryOrgTurnOutProcess(condition);
        		if (turnOutOrgProcess == null || turnOutOrgProcess.size() != 1) {
        			throw new Exception();
        		}
        		condition.remove("processId");
        		condition.put("indexNum", Integer.parseInt(String.valueOf(turnOutOrgProcess.get(0).get("indexNum"))) + 1);
        		//得到下个步骤数的步骤id
        		turnOutOrgProcess = organizationTurnOutProcessMapper.queryOrgTurnOutProcess(condition);
    		} else if (StringUtil.isNotEmpty(toou.getOtherOrgName())) {
    			condition.put("id", toos.getProcessId());
    			turnOutOrgProcess = turnOutOrgProcessMapper.queryToopOtherOrg(condition);
    			if (turnOutOrgProcess == null || turnOutOrgProcess.size() != 1) {
        			throw new Exception();
        		}
    			condition.remove("id");
        		condition.put("indexNum", Integer.parseInt(String.valueOf(turnOutOrgProcess.get(0).get("indexNum"))) + 1);
        		//得到下个步骤数的步骤id
        		turnOutOrgProcess = turnOutOrgProcessMapper.queryToopOtherOrg(condition);
    		} else {
    			throw new Exception();
    		}

    		
    		if (turnOutOrgProcess == null || turnOutOrgProcess.size() == 0) {	//进行到最后一个步骤
    			toou.setJoinStatus(toos.getStepStatus());
    		} else if (turnOutOrgProcess != null && turnOutOrgProcess.size() == 1) {
    			toou.setNowStep(Integer.parseInt(String.valueOf(turnOutOrgProcess.get(0).get("processId"))));
        		if ((boolean) turnOutOrgProcess.get(0).get("isFile")) {
        			toou.setJoinStatus(null);
        		} else {
        			toou.setJoinStatus("wait");
        			//如果不需要提交资料，直接进入下一步
        			TurnOutOrgStep newToos = new TurnOutOrgStep();
        			newToos.setTurnOutId(toos.getTurnOutId());
        			newToos.setProcessId(toou.getNowStep());
        			newToos.setStepStatus("wait");
        			newToos.setTime(new Date());
        			turnOutOrgStepMapper.insertSelective(newToos);
        		}
    		} else {
    			return R.error().setMsg("变更失败");
    		}
    	}
		
		turnOutOrgUserMapper.updateByPrimaryKey(toou);
    	
    	return R.ok().setMsg("更新成功");
    }
	
	/**
     * 补充材料
     * @param condition
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R supplementFiles(String condition) {
		Map<String, Object> data = JSON.parseObject(condition);
		List<Map<String, String>> uploadFileInfo = (List<Map<String, String>>) data.get("uploadFiles");
		Integer stepId = Integer.parseInt(String.valueOf(data.get("stepId")));
		for (Map<String, String> map : uploadFileInfo) {
			TurnOutOrgFile toof = new TurnOutOrgFile();
			toof.setStepId(stepId);
			toof.setFileTitle(map.get("fileName"));
			toof.setFileDescribes(map.get("fileName"));
			toof.setFilePath(map.get("fileUrl"));
			toof.setFileType(map.get("suffix"));
			toof.setTime(new Date());
			turnOutOrgFileMapper.insertSelective(toof);
		}
		return R.ok().setMsg("补充成功");
    }
}
