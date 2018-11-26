package com.zltel.broadcast.um.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.JoinPartyOrgFile;
import com.zltel.broadcast.um.bean.JoinPartyOrgStep;
import com.zltel.broadcast.um.bean.JoinPartyOrgUser;
import com.zltel.broadcast.um.dao.JoinPartyOrgFileMapper;
import com.zltel.broadcast.um.dao.JoinPartyOrgStepMapper;
import com.zltel.broadcast.um.dao.JoinPartyOrgUserMapper;
import com.zltel.broadcast.um.service.JoinPartyOrgUserService;
import com.zltel.broadcast.um.util.DateUtil;

import io.netty.util.internal.StringUtil;

@Service
public class JoinPartyOrgUserServiceImpl extends BaseDaoImpl<JoinPartyOrgUser> implements JoinPartyOrgUserService {
	@Resource
    private JoinPartyOrgUserMapper joinPartyOrgUserMapper;
	@Resource
    private JoinPartyOrgStepMapper joinPartyOrgStepMapper;
	@Resource
    private JoinPartyOrgFileMapper joinPartyOrgFileMapper;
	@Override
    public BaseDao<JoinPartyOrgUser> getInstince() {
        return this.joinPartyOrgUserMapper;
    }
	
	/**
     * 查询申请入党人员的信息
     * @param conditions
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryJoinPartyOrgUsers(Map<String, Object> conditions, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> joinPartyOrgUsers = joinPartyOrgUserMapper.queryJoinPartyOrgUsers(conditions);
        PageInfo<Map<String, Object>> joinPartyOrgUsersForPageInfo = new PageInfo<>(joinPartyOrgUsers);
        if (joinPartyOrgUsersForPageInfo != null && joinPartyOrgUsersForPageInfo.getList() != null
                && joinPartyOrgUsersForPageInfo.getList().size() > 0) {
        	for (Map<String, Object> joinPartyOrgUser : joinPartyOrgUsers) {
        		joinPartyOrgUser.put("applyTime", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD_HH_MM_SS, joinPartyOrgUser.get("applyTime") == null ||
					joinPartyOrgUser.get("applyTime") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD_HH_MM_SS, joinPartyOrgUser.get("applyTime").toString())));
			}
            return R.ok().setData(joinPartyOrgUsersForPageInfo).setMsg("查询信息成功");
        } else {
            return R.ok().setMsg("没有查询到信息");
        }
    }
	
	/**
     * 申请入党
     * @param conditions
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertJoinPartyOrgUsers(Map<String, Object> conditions) {
    	Integer joinId = null;
    	Integer stepNum = Integer.parseInt(String.valueOf(conditions.get("stepNum")));
    	Integer userId = Integer.parseInt(String.valueOf(conditions.get("userId")));
    	String filePaths = String.valueOf(conditions.get("joinFileUploadUrl"));
    	if (stepNum == null || userId == null) {
    		return R.error().setMsg("申请遇到错误");
    	}
    	
    	if (stepNum == 1) {	//步骤为1，添加join_party_org_step表信息
    		Integer joinOrgId = Integer.parseInt(String.valueOf(conditions.get("orgInfoId")));
    		Integer joinOrgType = Integer.parseInt(String.valueOf(conditions.get("joinOrgType")));	//入党类型
    		JoinPartyOrgUser jpou = new JoinPartyOrgUser();
    		jpou.setUserId(userId);
    		jpou.setJoinOrgId(joinOrgId);
    		jpou.setJoinPartyType(joinOrgType);
    		jpou.setJoinStatus("wait");
    		jpou.setTime(new Date());
    		jpou.setNowStep(stepNum);
    		jpou.setIsHistory(0);
    		joinPartyOrgUserMapper.insertSelective(jpou);
    		
    		joinId = jpou.getId();
    	} else {
    		joinId = Integer.parseInt(String.valueOf(conditions.get("joinId")));
    		JoinPartyOrgUser jpou = new JoinPartyOrgUser();
    		jpou.setId(joinId);
    		jpou.setNowStep(stepNum);
    		joinPartyOrgUserMapper.updateByPrimaryKeySelective(jpou);
    	}
    	
    	JoinPartyOrgStep jpos = new JoinPartyOrgStep();
    	jpos.setJoinId(joinId);
    	jpos.setProcessId(stepNum);
    	jpos.setStepStatus("wait");
    	jpos.setStepStatusReason("等待审核...");
    	jpos.setTime(new Date());
    	joinPartyOrgStepMapper.insertSelective(jpos);
    	
    	if (!StringUtil.isNullOrEmpty(filePaths)) {	//有上传文件
    		String[] filePath = filePaths.split(",");
    		for (int i = 0; i < filePath.length; i = i + 2) {
    			String path = filePath[i];
    			String name = filePath[i + 1];
    			if (!StringUtil.isNullOrEmpty(path) && !StringUtil.isNullOrEmpty(name)) {
					JoinPartyOrgFile jpof = new JoinPartyOrgFile();
					jpof.setStepId(jpos.getId());
					jpof.setFileTitle(name);
					String[] suffixs = name.split("\\.");
					if (suffixs.length > 0) {
						String fileType = suffixs[suffixs.length - 1];
						jpof.setFileType(fileType);
					}
					jpof.setFilePath(path);
					jpof.setTime(new Date());
					joinPartyOrgFileMapper.insertSelective(jpof);
				}
			}
    	}
    	
    	
    	return R.ok().setMsg("成功" + stepNum + "," + userId);
    }
}
