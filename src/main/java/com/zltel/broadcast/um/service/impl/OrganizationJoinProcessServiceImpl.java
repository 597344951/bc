package com.zltel.broadcast.um.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.JoinPartyOrgUser;
import com.zltel.broadcast.um.bean.OrganizationJoinProcess;
import com.zltel.broadcast.um.dao.JoinPartyOrgProcessMapper;
import com.zltel.broadcast.um.dao.JoinPartyOrgUserMapper;
import com.zltel.broadcast.um.dao.OrganizationJoinProcessMapper;
import com.zltel.broadcast.um.service.OrganizationJoinProcessService;

@Service
public class OrganizationJoinProcessServiceImpl extends BaseDaoImpl<OrganizationJoinProcess> implements OrganizationJoinProcessService {
	@Resource
    private OrganizationJoinProcessMapper organizationJoinProcessMapper;
	@Resource
    private JoinPartyOrgUserMapper joinPartyOrgUserMapper;
	@Resource
    private JoinPartyOrgProcessMapper joinPartyOrgProcessMapper;
	
	@Override
    public BaseDao<OrganizationJoinProcess> getInstince() {
        return this.organizationJoinProcessMapper;
    }
	
	/**
     * 添加组织流程
     * @param ojp
     * @return
     */
	@Override
    @Transactional
    public R insertOrganizationJoinProcess(Map<String, Object> condition) throws Exception {
		Integer orgId = Integer.parseInt(String.valueOf(condition.get("orgId")));
		String process = String.valueOf(condition.get("processId"));
		String[] processs = process.split(",");
		
		//检测是否含有必要步骤
		boolean haveJiji = false;
		boolean haveFazhan = false;
		boolean haveYubei = false;
		boolean haveZhuanzheng = false;
		int haveJijiIndex = 0;
		int haveFazhanIndex = 0;
		int haveYubeiIndex = 0;
		int haveZhuanzhengIndex = 0;
		for (int i = 0; i < processs.length; i++) {
			if ("3".equals(processs[i])) {
				haveJiji = true; haveJijiIndex = i;
			}
			if ("9".equals(processs[i])) {
				haveFazhan = true; haveFazhanIndex = i;
			}
			if ("14".equals(processs[i])) {
				haveYubei = true; haveYubeiIndex = i;
			}
			if ("16".equals(processs[i])) {
				haveZhuanzheng = true; haveZhuanzhengIndex = i;
			}
		}
		if (!haveJiji) return R.error().setMsg("步骤未含有“确认入党积极份子”步骤");
		if (!haveFazhan) return R.error().setMsg("步骤未含有“会议确认发展对象”步骤");
		if (!haveYubei) return R.error().setMsg("步骤未含有“确定预备党员”步骤");
		if (!haveZhuanzheng) return R.error().setMsg("步骤未含有“提交转正申请”步骤");
		if (!(haveJijiIndex < haveFazhanIndex && haveFazhanIndex < haveYubeiIndex
				&& haveYubeiIndex < haveZhuanzhengIndex)) return R.error().setMsg("步骤不正确");
		
		JoinPartyOrgUser jpou = new JoinPartyOrgUser();
		jpou.setJoinOrgId(orgId);
		jpou.setIsHistory(0);
		List<Map<String, Object>> joinPartyUsers = joinPartyOrgUserMapper.queryJpouIsJoin(jpou);
		if (joinPartyUsers != null && joinPartyUsers.size() > 0) {
			return R.error().setMsg("当前组织有人正在申请中，不能变更");
		}
		
		organizationJoinProcessMapper.deleteByOrgId(orgId);
		
		for (int i = 0; i < processs.length; i++) {
			OrganizationJoinProcess ojp = new OrganizationJoinProcess();
			ojp.setOrgId(orgId);
			ojp.setProcessId(Integer.parseInt(processs[i]));
			ojp.setIndexNum(i);
			int count = organizationJoinProcessMapper.insertSelective(ojp);
			if (count != 1) {
				throw new Exception();
			}
		}
    	return R.ok().setMsg("变更成功");
    }
	
	/**
     * 查询
     * @param ojp
     * @return
     */
    public R queryOjp(OrganizationJoinProcess ojp) {
    	List<OrganizationJoinProcess> ojps = organizationJoinProcessMapper.queryOjp(ojp);
    	return R.ok().setData(ojps);
    }
    
    /**
     * 查询
     * @param orgId
     * @return
     */
    public R queryOrgOjp(Map<String, Object> condition) {
    	List<Map<String, Object>> ojps = organizationJoinProcessMapper.queryOrgOjp(condition);
    	if (ojps != null && ojps.size() > 0) {
    		return R.ok().setData(ojps);
    	}
    	return R.error().setMsg("该组织还没有指定入党流程");
    }
}
