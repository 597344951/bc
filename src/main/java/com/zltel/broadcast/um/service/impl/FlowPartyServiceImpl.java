package com.zltel.broadcast.um.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.common.util.PasswordHelper;
import com.zltel.broadcast.um.bean.BaseUserInfo;
import com.zltel.broadcast.um.bean.FlowParty;
import com.zltel.broadcast.um.bean.OrganizationRelation;
import com.zltel.broadcast.um.bean.PartyUserInfo;
import com.zltel.broadcast.um.bean.SysRole;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.bean.SysUserRole;
import com.zltel.broadcast.um.dao.BaseUserInfoMapper;
import com.zltel.broadcast.um.dao.FlowPartyMapper;
import com.zltel.broadcast.um.dao.OrganizationRelationMapper;
import com.zltel.broadcast.um.dao.PartyUserInfoMapper;
import com.zltel.broadcast.um.dao.SysRoleMapper;
import com.zltel.broadcast.um.dao.SysUserMapper;
import com.zltel.broadcast.um.dao.SysUserRoleMapper;
import com.zltel.broadcast.um.service.FlowPartyService;

@Service
public class FlowPartyServiceImpl extends BaseDaoImpl<FlowParty> implements FlowPartyService {
	@Resource
    private FlowPartyMapper flowPartyMapper;
	@Resource
    private BaseUserInfoMapper baseUserInfoMapper;
	@Resource
    private PartyUserInfoMapper partyUserInfoMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private OrganizationRelationMapper organizationRelationMapper;
	
	@Override
    public BaseDao<FlowParty> getInstince() {
        return this.flowPartyMapper;
    }
	
	/**
     * 查询流动党员记录
     * @param condition
     * @return
     */
    public R queryFlowPartys(Map<String, Object> condition, int pageNum, int pageSize) {
    	PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> flowPartys = flowPartyMapper.queryFlowPartys(condition);
		PageInfo<Map<String, Object>> flowPartysPageInfo = new PageInfo<>(flowPartys);
		if (flowPartysPageInfo != null && flowPartysPageInfo.getList() != null
				&& flowPartysPageInfo.getList().size() > 0) {
			return R.ok().setData(flowPartysPageInfo);
		} 
    	return R.ok().setMsg("没有查询到记录");
    }
    
    /**
     * 添加流动党员
     * @param request
     * @param partyUser
     * @throws Exception
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertFlowPartyUserInfo(Map<String, Object> flowPartyUser) throws Exception {
    	String name = String.valueOf(flowPartyUser.get("name"));
    	String sex = String.valueOf(flowPartyUser.get("sex"));
    	String idCard = String.valueOf(flowPartyUser.get("idCard"));
    	String mobilePhone = String.valueOf(flowPartyUser.get("mobilePhone"));
    	Integer type = Integer.parseInt(String.valueOf(flowPartyUser.get("type")));
    	Integer orgInfoId = Integer.parseInt(String.valueOf(flowPartyUser.get("orgInfoId")));
    	String flowAddressProvince = String.valueOf(flowPartyUser.get("flowAddressProvince"));
    	String flowAddressCity = String.valueOf(flowPartyUser.get("flowAddressCity"));
    	String flowAddressArea = String.valueOf(flowPartyUser.get("flowAddressArea"));
    	String flowAddressDetail = String.valueOf(flowPartyUser.get("flowAddressDetail"));
    	String reason = String.valueOf(flowPartyUser.get("reason"));
    	Integer orgRltDutyId = Integer.parseInt(String.valueOf(flowPartyUser.get("orgRltDutyId")));
    	
    	BaseUserInfo bui = new BaseUserInfo();
    	PartyUserInfo pui = new PartyUserInfo();
    	
    	bui.setName(name);
    	bui.setSex(sex);
    	bui.setIdCard(idCard);
    	bui.setMobilePhone(mobilePhone);
    	bui.setIsParty(1);
    	baseUserInfoMapper.insertSelective(bui);	//保存基础信息
    	
    	pui.setPartyUserId(bui.getBaseUserId());
    	pui.setType(type);
    	pui.setStatus(-1);	//表示流动党员
    	partyUserInfoMapper.insertSelective(pui);
    	
    	//添加登录用户
    	SysUser su = new SysUser();
    	su.setUsername(bui.getIdCard());
    	su.setPassword(bui.getIdCard().substring(bui.getIdCard().length() - 6));
    	String salt = UUID.randomUUID().toString();
    	su.setSalt(salt);	//保存盐
    	su.setPassword(PasswordHelper.encryptPassword(su.getPassword(), salt));	//加密
    	su.setEmail(bui.getEmail());
    	su.setMobile(bui.getMobilePhone());
    	su.setStatus(true);
    	su.setUserType(1);
    	su.setOrgId(orgInfoId);
    	su.setCreateTime(new Date());
		sysUserMapper.insertSelective(su);
		
		//赋予默认角色
		SysRole sysRole = new SysRole();
		sysRole.setRoleName("party_role");
		List<SysRole> srs = sysRoleMapper.querySysRoles(sysRole);
		if (srs != null && srs.size() == 1) {
			SysUserRole sur = new SysUserRole();
			sur.setUserId((long)su.getUserId());
			sur.setRoleId(srs.get(0).getRoleId());
			sysUserRoleMapper.insertSelective(sur);
		} else {
			throw new Exception();
		}
		
		FlowParty fp = new FlowParty();
		fp.setUserId(bui.getBaseUserId());
		fp.setReason(reason);
		fp.setFlowTime(null);
		fp.setFlowAddressProvince(flowAddressProvince);
		fp.setFlowAddressCity(flowAddressCity);
		fp.setFlowAddressArea(flowAddressArea);
		fp.setFlowAddressDetail(flowAddressDetail);
		flowPartyMapper.insertSelective(fp);
		
		
		//删除所有组织关系，确保只能加入一个组织
		OrganizationRelation _or = new OrganizationRelation();
		organizationRelationMapper.deleteOrgRelationByUserId(bui.getBaseUserId());
		_or.setOrgRltJoinTime(new Date());
		_or.setOrgRltDutyId(orgRltDutyId);
		_or.setOrgRltInfoId(orgInfoId);
		_or.setOrgRltUserId(bui.getBaseUserId());
		_or.setThisOrgFlow(false);
			
		organizationRelationMapper.insertSelective(_or);	//开始添加组织关系
    	
    	
    	return R.ok().setMsg("添加成功");
    }
    
    /**
     * 添加流动党员
     * @param request
     * @param partyUser
     * @throws Exception
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertFlowPartyUserInfoThisOrg(Map<String, Object> flowPartyUser) throws Exception {
    	Integer userId = Integer.parseInt(String.valueOf(flowPartyUser.get("userId")));
    	String flowAddressProvince = String.valueOf(flowPartyUser.get("flowAddressProvince"));
    	String flowAddressCity = String.valueOf(flowPartyUser.get("flowAddressCity"));
    	String flowAddressArea = String.valueOf(flowPartyUser.get("flowAddressArea"));
    	String flowAddressDetail = String.valueOf(flowPartyUser.get("flowAddressDetail"));
    	String reason = String.valueOf(flowPartyUser.get("reason"));
    	
    	PartyUserInfo pui = new PartyUserInfo();
    	pui.setPartyUserId(userId);
    	pui.setStatus(-1);	//表示流动党员
    	partyUserInfoMapper.updateByPrimaryKeySelective(pui);
    	
    	FlowParty fp = new FlowParty();
		fp.setUserId(userId);
		fp.setReason(reason);
		fp.setFlowTime(null);
		fp.setFlowAddressProvince(flowAddressProvince);
		fp.setFlowAddressCity(flowAddressCity);
		fp.setFlowAddressArea(flowAddressArea);
		fp.setFlowAddressDetail(flowAddressDetail);
		flowPartyMapper.insertSelective(fp);
    	
    	return R.ok().setMsg("添加成功");
    }
    
    
    /**
     * 删除流动党员
     * @param request
     * @param partyUser
     * @throws Exception
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteFlowParty(Integer flowId) throws Exception {
    	Map<String, Object> condition = new HashMap<>();
    	condition.put("id", flowId);
    	List<Map<String, Object>> flowPartys = flowPartyMapper.queryFlowPartys(condition);
    	if (flowPartys != null && flowPartys.size() == 1) {
    		Map<String, Object> flowParty = flowPartys.get(0);	//流动信息
    		Integer userId = Integer.parseInt(String.valueOf(flowParty.get("userId")));	//用户id
    		
    		
    		return R.ok().setMsg("删除成功");
    	} else {
    		return R.error().setMsg("删除出错");
    	}
    }
	
	
	

	@Override
	public int updateByPrimaryKeyWithBLOBs(FlowParty record) {
		return 0;
	}
}
