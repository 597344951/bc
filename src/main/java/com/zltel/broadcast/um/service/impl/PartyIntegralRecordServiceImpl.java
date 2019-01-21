package com.zltel.broadcast.um.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
import com.zltel.broadcast.um.util.IntegralExcel;
import com.zltel.broadcast.um.util.RegexUtil;

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
	@Resource
	private IntegralExcel integralExcel;
	@Override
    public BaseDao<PartyIntegralRecord> getInstince() {
        return this.partyIntegralRecordMapper;
    }
	
	/**
     * 积分排行榜
     * @param condition
     * @return
     */
    public Map<String, Object> queryIntegralRanking(Map<String, Object> condition) {
    	return null;
    }
	
	/**
     * 查询用户积分变更轨迹统计图
     * @param condition
     * @return
     */
    public Map<String, Object> queryUserIntegralChangeTrajectory(Map<String, Object> condition) {
    	Date startTime = null;
    	Date endTime = null;
    	if (condition.get("startTime") != null && condition.get("startTime") != "") {
    		startTime = DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)condition.get("startTime"));
    		startTime = DateUtil.getDateOfMonthStartDayTime(startTime);
    		if (condition.get("endTime") != null && condition.get("endTime") != "") {	//两个都有值
    			endTime = DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)condition.get("endTime"));
    			if (endTime.after(new Date())) {
    				endTime = DateUtil.getDateOfEndTime(new Date());
    			} else {    				
    				endTime = DateUtil.getDateOfMonthEndDayTime(endTime);
    			}
    		} else {
    			endTime = DateUtil.getDateOfEndTime(new Date());
    		}
    		if (endTime.before(startTime)) {
				return R.error().setMsg("时间选择错误");
			}
    	} else if (condition.get("endTime") != null && condition.get("endTime") != "") {
    		endTime = DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)condition.get("endTime"));
			endTime = DateUtil.getDateOfMonthEndDayTime(endTime);
			startTime = DateUtil.getDateOfMonthStartDayTime(endTime);
    	} else {
    		endTime = DateUtil.getDateOfEndTime(new Date());
    		startTime = DateUtil.getDateOfMonthStartDayTime(endTime);
    	}
    	
    	List<String> datas = new ArrayList<>();
    	List<String> lines = new ArrayList<>();
    	
    	BigDecimal totalIntegral = new BigDecimal(String.valueOf(condition.get("totalIntegral")));
    	BigDecimal nowIntegral = totalIntegral;
    	Map<String, Object> queryCondition = new HashMap<>();
    	//计算要开始搜索年初到时间之前的积分变化后的情况
    	queryCondition.put("orgId", condition.get("orgId"));
		queryCondition.put("idCard", condition.get("idCard"));
		Calendar ca = Calendar.getInstance();
		ca.setTime(startTime);
		ca.add(Calendar.SECOND, -1);
		queryCondition.put("endTime", ca.getTime());
		ca.set(Calendar.MONTH, 0); //调到1月
		queryCondition.put("startTime", DateUtil.getDateOfMonthStartDayTime(ca.getTime()));
		List<Map<String, Object>> partyIntegralRecords = partyIntegralRecordMapper.queryPartyIntegralRecords(queryCondition);
		if (partyIntegralRecords != null && partyIntegralRecords.size() > 0) {
			for (Map<String, Object> priMap : partyIntegralRecords) {
				BigDecimal changeIntegral = new BigDecimal(String.valueOf(priMap.get("changeScore")));
				nowIntegral = nowIntegral.add(changeIntegral);
				nowIntegral = nowIntegral.compareTo(new BigDecimal(0)) == -1 ? new BigDecimal(0) : 
					nowIntegral.compareTo(totalIntegral) == 1 ? totalIntegral : nowIntegral;
			}
		}
		
    	while (startTime.before(endTime)) {	//每天循环
    		Date queryEndTime = DateUtil.getDateOfEndTime(startTime);
    		ca.setTime(startTime);
    		//避免横坐标日期太长，年第一天显示年份，后续日期不用显示年份，只用显示月份和日
    		if (ca.get(Calendar.MONTH) == 0 && ca.get(Calendar.DAY_OF_MONTH) == 1) {
    			lines.add(String.valueOf(ca.get(Calendar.YEAR)) + "年");
    			//一年第一天，重置积分值
    			nowIntegral = totalIntegral;
    		} else {
    			lines.add((ca.get(Calendar.MONTH)) + 1 + "月" + ca.get(Calendar.DAY_OF_MONTH) + "日");
    		}
    		
    		queryCondition.put("startTime", startTime);
    		queryCondition.put("endTime", queryEndTime);
    		partyIntegralRecords = partyIntegralRecordMapper.queryPartyIntegralRecords(queryCondition);
    		if (partyIntegralRecords != null && partyIntegralRecords.size() > 0) {
    			for (Map<String, Object> priMap : partyIntegralRecords) {
    				BigDecimal changeIntegral = new BigDecimal(String.valueOf(priMap.get("changeScore")));
    				nowIntegral = nowIntegral.add(changeIntegral);
    				nowIntegral = nowIntegral.compareTo(new BigDecimal(0)) == -1 ? new BigDecimal(0) : 
    					nowIntegral.compareTo(totalIntegral) == 1 ? totalIntegral : nowIntegral;
    			}
    		}
    		datas.add(String.valueOf(nowIntegral));
    		
    		ca.add(Calendar.DAY_OF_YEAR, +1);
    		startTime = DateUtil.getDateOfStartTime(ca.getTime());
    	}
    	
    	Map<String, Object> results = new HashMap<>();
    	results.put("lines", lines);
    	results.put("datas", datas);
    	
    	return results;
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
    	if (!RegexUtil.isIDCard(su.getUsername())) return false;
    	List<Map<String, Object>> partyUsers = partyUserInfoMapper.queryPartyUserInfos(conditions);	//查询出该用户的信息
    	if (partyUsers == null || partyUsers.size() != 1) 
    		return false;
    	
    	pir.setPartyId(Integer.parseInt(String.valueOf(partyUsers.get(0).get("id"))));
    	conditions.clear();
    	conditions.put("orgId", pir.getOrgId());
    	conditions.put("parentIcId", -1);
    	Map<String, Object> result = integralConstituteService.queryOrgIntegralInfo(conditions);
    	if ((boolean)(result.get("integralError"))) {	//没有设置分值
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
    
    /**
	 * 下载积分明细导入excel格式示例
	 * @param baseUser 条件
	 * @return
	 */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public void exportIntegralExcelExample(HttpServletResponse response) {
    	integralExcel.exportIntegralExcelExample(response);
    }
    
    /**
	 * 批量导入积分明细记录
	 * @param file 文件
	 * @return
	 */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R importIntegralExcel(HttpServletResponse response, MultipartFile file)  throws Exception {
    	return integralExcel.importIntegralExcel(response, file);
    }
}
