package com.zltel.broadcast.um.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.common.util.AdminRoleUtil;
import com.zltel.broadcast.um.bean.IntegralChangeType;
import com.zltel.broadcast.um.bean.PartyIntegralRecord;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.dao.IntegralChangeTypeMapper;
import com.zltel.broadcast.um.dao.IntegralConstituteMapper;
import com.zltel.broadcast.um.dao.PartyIntegralRecordMapper;
import com.zltel.broadcast.um.dao.PartyUserInfoMapper;
import com.zltel.broadcast.um.dao.SysUserMapper;
import com.zltel.broadcast.um.service.IntegralConstituteService;
import com.zltel.broadcast.um.service.PartyIntegralRecordService;
import com.zltel.broadcast.um.util.DateUtil;

@Service
public class PartyIntegralRecordServiceImpl extends BaseDaoImpl<PartyIntegralRecord> implements PartyIntegralRecordService {
	@Resource
    private PartyIntegralRecordMapper partyIntegralRecordMapper;
	@Autowired
	private IntegralConstituteService integralConstituteService;
	@Resource
    private IntegralConstituteMapper integralConstituteMapper;
	@Resource
    private IntegralChangeTypeMapper integralChangeTypeMapper;
	@Resource
    private SysUserMapper sysUserMapper;
	@Resource
    private PartyUserInfoMapper partyUserInfoMapper;
	@Override
    public BaseDao<PartyIntegralRecord> getInstince() {
        return this.partyIntegralRecordMapper;
    }
	
	/**
     * 查询积分记录
     * @param conditions
     * @return
     */
    public R queryPartyIntegralRecords(Map<String, Object> conditions, int pageNum, int pageSize) {
    	Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) subject.getPrincipal();
        if (conditions == null) conditions = new HashMap<>();
    	if (AdminRoleUtil.isPlantAdmin()) {	//如果是平台管理员
        	//不做任何处理
        } else if (AdminRoleUtil.isOrgAdmin()) {	//如果是组织管理员
        	if (sysUser.getOrgId() == null) {
        		return R.ok().setCode(100).setMsg("组织管理员请设置所属的组织，如果是党员请加入组织");
        	}
        	conditions.put("orgId", sysUser.getOrgId());
        } else {	//个人用户，即党员，没有此功能的权限，前台此模块不显示
        	if (sysUser.getUserType() == 0) {
        		return R.ok().setCode(100).setMsg("非党员账户请先设置管理员类型");
        	} else if (sysUser.getUserType() == 1) {
        		conditions.put("idCard", sysUser.getUsername());
            }
        }
    	
    	PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> partyIntegralRecords = partyIntegralRecordMapper.queryPartyIntegralRecords(conditions);
		PageInfo<Map<String, Object>> partyIntegralRecordsForPageInfo = new PageInfo<>(partyIntegralRecords);
		if (partyIntegralRecordsForPageInfo != null && partyIntegralRecordsForPageInfo.getList() != null 
				&& partyIntegralRecordsForPageInfo.getList().size() > 0) {
			for (Map<String, Object> map : partyIntegralRecordsForPageInfo.getList()) {
				map.put("changeTime", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD_HH_MM_SS, map.get("changeTime") == null ||
					map.get("changeTime") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD_HH_MM_SS, map.get("changeTime").toString())));
			}
			return R.ok().setData(partyIntegralRecordsForPageInfo).setMsg("查询积分记录成功");
		} else {
			return R.ok().setMsg("没有查询到积分记录信息");
		}
    }
    
    /**
     * 添加积分变更记录
     * @param ict
     * @return
     */
    public R insertPartyUserIntegralRecord(PartyIntegralRecord pir) {
    	if (pir == null) return R.error().setMsg("添加失败");
    	pir.setChangeTime(pir.getChangeTime() == null ? new Date() : pir.getChangeTime());
    	int count = partyIntegralRecordMapper.insertSelective(pir);
    	if(count == 1) {
    		return R.ok().setMsg("添加成功");
    	} else {
    		return R.error().setMsg("添加失败");
    	}
    }
    
    /**
     * 活动变更积分值
     * @param pir  积分变更信息
     * @param icType  积分变更项
     * @param changeIcType  变更方法（加分，扣分）
     * @return
     */
    @SuppressWarnings("unchecked")
	public boolean automaticIntegralRecord(PartyIntegralRecord pir, IcType icType, IcChangeType icChangeType) {
    	if (icType == null || icChangeType == null || pir.getOrgId() == null 
    			|| pir.getPartyId() == null || (icType == IcType.ACTIVE && pir.getActivityId() == null)) {
    		return false;
    	}
    	SysUser su = sysUserMapper.selectByPrimaryKey(pir.getPartyId());
    	if (su == null) 
    		return false;
    	Map<String, Object> conditions = new HashMap<>();
    	conditions.put("orgInfoId", pir.getOrgId());
    	conditions.put("idCard", su.getUsername());
    	List<Map<String, Object>> partyUsers = partyUserInfoMapper.queryPartyUserInfos(conditions);
    	if (partyUsers == null || partyUsers.size() != 1) 
    		return false;
    	
    	pir.setPartyId(Integer.parseInt(String.valueOf(partyUsers.get(0).get("id"))));
    	conditions.clear();
    	conditions.put("orgId", pir.getOrgId());
    	conditions.put("parentIcId", -1);
    	Map<String, Object> result = integralConstituteService.queryOrgIntegralInfo(conditions);
    	if ((boolean)((Map<String, Object>)result.get("data")).get("integralError")) {	//没有设置分值
    		return false;
    	}
    	
    	conditions.remove("parentIcId");
    	conditions.put("isInnerIntegral", 1);
    	conditions.put("type", icType.getName());
    	List<Map<String, Object>> ics = integralConstituteMapper.queryOrgIntegralConstitute(conditions);
    	if (ics != null && ics.size() == 1) {	//有该类型的积分项
    		Map<String, Object> ic = ics.get(0);
    		IntegralChangeType ict = new IntegralChangeType();
    		ict.setIcId(Integer.parseInt(String.valueOf(ic.get("icId"))));
    		if ("加分".equals(icChangeType.getName())) {
    			ict.setOperation(1);
    		} else if ("扣分".equals(icChangeType.getName())) {
    			ict.setOperation(0);
    		} else {
    			return false;
    		}
    		List<IntegralChangeType> icts = integralChangeTypeMapper.queryICT(ict);
    		if (icts != null && icts.size() == 1) {
    			ict = icts.get(0);
    			pir.setChangeTypeId(Integer.parseInt(String.valueOf(ic.get("icId"))));
    			pir.setChangeIntegralType(ict.getIctId());
    			pir.setChangeTime(new Date());
    			pir.setChangeScore(ict.getChangeProposalIntegral());
    			pir.setIsMerge(1);
    			int count = partyIntegralRecordMapper.insertSelective(pir);
    			if(count == 1) {
    	    		return true;
    	    	} else {
    	    		return false;
    	    	}
    		} else {
    			return false;
    		}
    	} else {
    		return false;
    	}
    }
}
