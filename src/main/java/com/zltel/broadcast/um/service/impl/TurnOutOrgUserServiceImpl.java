package com.zltel.broadcast.um.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.BaseUserInfo;
import com.zltel.broadcast.um.bean.OrganizationRelation;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.bean.TurnOutOrgFile;
import com.zltel.broadcast.um.bean.TurnOutOrgProcess;
import com.zltel.broadcast.um.bean.TurnOutOrgStep;
import com.zltel.broadcast.um.bean.TurnOutOrgUser;
import com.zltel.broadcast.um.dao.BaseUserInfoMapper;
import com.zltel.broadcast.um.dao.OrganizationRelationMapper;
import com.zltel.broadcast.um.dao.OrganizationTurnOutProcessMapper;
import com.zltel.broadcast.um.dao.SysUserMapper;
import com.zltel.broadcast.um.dao.TurnOutOrgFileMapper;
import com.zltel.broadcast.um.dao.TurnOutOrgProcessMapper;
import com.zltel.broadcast.um.dao.TurnOutOrgStepMapper;
import com.zltel.broadcast.um.dao.TurnOutOrgUserMapper;
import com.zltel.broadcast.um.service.TurnOutOrgUserService;
import com.zltel.broadcast.um.util.DateUtil;

@Service
public class TurnOutOrgUserServiceImpl extends BaseDaoImpl<TurnOutOrgUser> implements TurnOutOrgUserService {
	@Resource
    private TurnOutOrgUserMapper turnOutOrgUserMapper;
	@Resource
    private TurnOutOrgStepMapper turnOutOrgStepMapper;
	@Resource
    private TurnOutOrgFileMapper turnOutOrgFileMapper;
	@Resource
    private TurnOutOrgProcessMapper turnOutOrgProcessMapper;
	@Resource
    private OrganizationTurnOutProcessMapper organizationTurnOutProcessMapper;
	@Resource
    private OrganizationRelationMapper organizationRelationMapper;
	@Resource
    private SysUserMapper sysUserMapper;
	@Resource
    private BaseUserInfoMapper baseUserInfoMapper;
	@Override
    public BaseDao<TurnOutOrgUser> getInstince() {
        return this.turnOutOrgUserMapper;
    }
	
	
	
	/**
     * 查询申请组织关系转移人员的信息
     * @param conditions
     * @return
     */
	@Override
    public R queryTurnOutOrgPartyUsers(Map<String, Object> conditions, int pageNum, int pageSize) {
    	PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> turnOutOrgPartyUsers = turnOutOrgUserMapper.queryTurnOutOrgPartyUsers(conditions);
        PageInfo<Map<String, Object>> turnOutOrgPartyUsersForPageInfo = new PageInfo<>(turnOutOrgPartyUsers);
        if (turnOutOrgPartyUsersForPageInfo != null && turnOutOrgPartyUsersForPageInfo.getList() != null
                && turnOutOrgPartyUsersForPageInfo.getList().size() > 0) {
        	Map<String, Object> _condition = null;
        	for (Map<String, Object> turnOutOrgPartyUser : turnOutOrgPartyUsers) {
        		turnOutOrgPartyUser.put("applyTime", 
				DateUtil.formatDate(DateUtil.YYYY_MM_DD_HH_MM_SS, turnOutOrgPartyUser.get("applyTime") == null ||
				turnOutOrgPartyUser.get("applyTime") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD_HH_MM_SS, turnOutOrgPartyUser.get("applyTime").toString())));
        		
        		if (StringUtil.isNotEmpty(String.valueOf(turnOutOrgPartyUser.get("orgInfoName")))) {
        			//查询该组织的最后一个流程
            		_condition = new HashMap<>();
            		_condition.put("orgId", turnOutOrgPartyUser.get("turnOutOrgId"));
            		Map<String, Object> maxOrgProcess = organizationTurnOutProcessMapper.queryOrgTurnOutProcessMaxProcess(_condition).get(0);
            		turnOutOrgPartyUser.put("maxOrgProcess", maxOrgProcess);
        		} else if (StringUtil.isNotEmpty(String.valueOf(turnOutOrgPartyUser.get("otherOrgName")))) {
        			Map<String, Object> maxOrgProcess = turnOutOrgProcessMapper.queryToopMaxProcess(_condition).get(0);
            		turnOutOrgPartyUser.put("maxOrgProcess", maxOrgProcess);
        		}
        		
			}
            return R.ok().setData(turnOutOrgPartyUsersForPageInfo).setMsg("查询信息成功");
        } else {
            return R.ok().setMsg("没有查询到信息");
        }
    }
	
	
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public R insertToou(TurnOutOrgUser toou) {
		toou.setTime(new Date());
		boolean isFile = false;
		if (toou.getTurnOutOrgId() == null && StringUtil.isNotEmpty(toou.getOtherOrgName())) {
			//转入平台以外的组织
			TurnOutOrgProcess toop = new TurnOutOrgProcess();
			toop.setIndexNum(0);
			toop = turnOutOrgProcessMapper.queryToop(toop).get(0);
			toou.setNowStep(toop.getId());
			isFile = toop.getFile();
		} else if (toou.getTurnOutOrgId() != null && StringUtil.isEmpty(toou.getOtherOrgName())) {
			//转入组织内的平台
			Map<String, Object> condition = new HashMap<>();
			condition.put("orgId", toou.getTurnOutOrgId());
			condition.put("indexNum", 0);
			//一定能查寻到此条信息，不用判断
			Map<String, Object> otops = organizationTurnOutProcessMapper.queryOrgTurnOutProcess(condition).get(0);
			
			isFile = (boolean) otops.get("isFile");
			toou.setNowStep(Integer.parseInt(String.valueOf(otops.get("processId"))));
		} else {
			return R.error().setMsg("转移出现错误");
		}
		
		
		TurnOutOrgStep toos = null;
		if (!isFile) {	//第一步不用上传，直接进入等待审核阶段
			toou.setJoinStatus("wait");
    		toos = new TurnOutOrgStep();
    	}
		
		int count = turnOutOrgUserMapper.insertSelective(toou);
		if (toos != null) {
			toos.setTurnOutId(toou.getId());
			toos.setProcessId(toou.getNowStep());
			toos.setStepStatus("wait");
			toos.setTime(new Date());
			turnOutOrgStepMapper.insertSelective(toos);
    	}
    	if (count == 1) {
    		return R.ok().setMsg("选择成功");
    	}
    	return R.error().setMsg("选择失败");
	}
	
	
	/**
     * 增加步骤
     * @param submitDate
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertTurnOutOrgPartyStep(String submitDate) {
		Map<String, Object> data = JSON.parseObject(submitDate);
		List<Map<String, String>> uploadFileInfo = null;
		if (data.get("uploadFiles") != null) {
			uploadFileInfo = (List<Map<String, String>>) data.get("uploadFiles");
		}
		Integer processId = Integer.parseInt(String.valueOf(data.get("processId")));
		Integer turnOutId = Integer.parseInt(String.valueOf(data.get("turnOutId")));
		
		//1、更新状态为wait
		TurnOutOrgUser toou = new TurnOutOrgUser();
		toou.setId(turnOutId);
		toou.setJoinStatus("wait");
		turnOutOrgUserMapper.updateByPrimaryKeySelective(toou);
		//2、增加当前步骤信息
		TurnOutOrgStep toos = new TurnOutOrgStep();
		toos.setTurnOutId(turnOutId);
		toos.setProcessId(processId);
		toos.setStepStatus("wait");
		toos.setTime(new Date());
		turnOutOrgStepMapper.insertSelective(toos);	//插入的主键值会设置到jpos对象里
		//3、增加上传文件信息
		if (uploadFileInfo != null && uploadFileInfo.size() > 0) {
			for (Map<String, String> map : uploadFileInfo) {
				TurnOutOrgFile toof = new TurnOutOrgFile();
				toof.setStepId(toos.getId());
				toof.setFileTitle(map.get("fileName"));
				toof.setFileDescribes(map.get("fileName"));
				toof.setFilePath(map.get("fileUrl"));
				toof.setFileType(map.get("suffix"));
				toof.setTime(new Date());
				turnOutOrgFileMapper.insertSelective(toof);
			}
		}
		return R.ok().setMsg("操作成功");
    }
	
	/**
     * 组织关系审核通过，确认加入此组织
     * @param organizationRelation
     * @param orgRltDutys
     * @return
     * @throws Exception
     */
	@Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R insertOrgRelation(Integer turnOutId, OrganizationRelation organizationRelation, List<Integer> orgRltDutys) throws Exception {
		if (organizationRelation != null && orgRltDutys != null && orgRltDutys.size() > 0) {
			//删除所有组织关系，确保只能加入一个组织
			organizationRelationMapper.deleteOrgRelationByUserId(organizationRelation.getOrgRltUserId());
			
			organizationRelation.setOrgRltJoinTime(new Date());
			for (Integer orgDuty : orgRltDutys) {
				organizationRelation.setOrgRltDutyId(orgDuty);
				organizationRelationMapper.insertSelective(organizationRelation);	//开始添加组织关系
			}
			//在党员账号里更新加入组织id
			BaseUserInfo bui = baseUserInfoMapper.selectByPrimaryKey(organizationRelation.getOrgRltUserId());
			SysUser su = sysUserMapper.selectByUserName(bui.getIdCard());
			if (su != null) {
				su.setOrgId(organizationRelation.getOrgRltInfoId());
				sysUserMapper.updateByPrimaryKeySelective(su);
			} else {
				throw new Exception();
			}
			
			TurnOutOrgUser toou = new TurnOutOrgUser();
			toou.setId(turnOutId);
			toou.setIsHistory(true);
			turnOutOrgUserMapper.updateByPrimaryKeySelective(toou);
			
			
			
			return R.ok().setMsg("组织关系添加成功。");
		} else {	//添加一定需要一个组织关系
			throw new Exception();
		}
    }
	
	/**
     * 查询转移步骤
     * @param toop
     * @return
     */
    public R queryToopOtherOrg(Map<String, Object> condition) {
    	List<Map<String, Object>> toops = turnOutOrgProcessMapper.queryToopOtherOrg(condition);
    	if (toops != null && toops.size() > 0) {
    		return R.ok().setData(toops);
    	}
    	return R.error().setMsg("为方便测试，你可能会看到此消息，正式使用不会出现");
    }
}
