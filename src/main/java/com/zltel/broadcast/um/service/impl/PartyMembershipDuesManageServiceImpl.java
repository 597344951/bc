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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.common.util.AdminRoleUtil;
import com.zltel.broadcast.um.bean.PartyMembershipDuesManage;
import com.zltel.broadcast.um.bean.PartyMembershipDuesStatus;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.dao.PartyMembershipDuesManageMapper;
import com.zltel.broadcast.um.dao.PartyMembershipDuesStatusMapper;
import com.zltel.broadcast.um.service.PartyMembershipDuesManageService;
import com.zltel.broadcast.um.util.DateUtil;
import com.zltel.broadcast.um.util.ExcelForPartyMembersshipDuesManage;

@Service
public class PartyMembershipDuesManageServiceImpl extends BaseDaoImpl<PartyMembershipDuesManage> 
															implements PartyMembershipDuesManageService {
	
	@Resource
    private PartyMembershipDuesManageMapper partyMembershipDuesManageMapper;
	@Resource
    private PartyMembershipDuesStatusMapper partyMembershipDuesStatusMapper;
	@Resource
	private ExcelForPartyMembersshipDuesManage excelForPartyMembersshipDuesManage;
	@Override
    public BaseDao<PartyMembershipDuesManage> getInstince() {
        return this.partyMembershipDuesManageMapper;
    }
	
	/**
     * 查询党费缴纳记录
     * @param conditionMaps
     * @return
     */
    public R queryPartyMembershipDues(Map<String, Object> conditionMaps, int pageNum, int pageSize) {
    	Date startTime = null;
    	Date endTime = null;
    	if (conditionMaps == null) conditionMaps = new HashMap<>();
    	if (conditionMaps.get("paidDateStart") != null && conditionMaps.get("paidDateStart") != "") {
    		startTime = DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)conditionMaps.get("paidDateStart"));
    		startTime = DateUtil.getDateOfMonthStartDayTime(startTime);
    	}
    	if (conditionMaps.get("paidDateEnd") != null && conditionMaps.get("paidDateEnd") != "") {
    		endTime = DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)conditionMaps.get("paidDateEnd"));
			endTime = DateUtil.getDateOfMonthEndDayTime(endTime);
    	}
    	if (startTime != null && endTime != null && endTime.before(startTime)) {
			return R.error().setMsg("时间选择错误");
		}
    	conditionMaps.put("paidDateStart", startTime);
		conditionMaps.put("paidDateEnd", endTime);
		
		Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) subject.getPrincipal();
        if (AdminRoleUtil.isPlantAdmin()) {	//如果是平台管理员
        	//不做任何处理
        } else if (AdminRoleUtil.isOrgAdmin()) {	//如果是组织管理员
        	if (sysUser.getOrgId() == null) {
        		return R.ok().setCode(100).setMsg("组织管理员请设置所属的组织，如果是党员请加入组织");
        	}
        	conditionMaps.put("orgInfoId", sysUser.getOrgId());
        } else {	//个人用户，即党员
        	if (sysUser.getUserType() == 0) {
        		return R.ok().setCode(100).setMsg("非党员账户请先设置管理员类型");
        	} else if (sysUser.getUserType() == 1) {
        		conditionMaps.put("idCard", sysUser.getUsername());
            }
        }
    	
    	PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> partyMembershipDues = partyMembershipDuesManageMapper.queryPartyMembershipDues(conditionMaps);	//开始查询，没有条件则查询所有
		PageInfo<Map<String, Object>> partyMembershipDuesForPageInfo = new PageInfo<>(partyMembershipDues);
		if (partyMembershipDuesForPageInfo != null && partyMembershipDuesForPageInfo.getList() != null 
				&& !partyMembershipDuesForPageInfo.getList().isEmpty()) {	//是否查询到数据
			for (Map<String, Object> map : partyMembershipDuesForPageInfo.getList()) {
				map.put("shouldPayDateStart", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD, map.get("shouldPayDateStart") == null ||
					map.get("shouldPayDateStart") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD, map.get("shouldPayDateStart").toString())));
				map.put("shouldPayDateEnd", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD, map.get("shouldPayDateEnd") == null ||
					map.get("shouldPayDateEnd") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD, map.get("shouldPayDateEnd").toString())));
				map.put("paidDate", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD, map.get("paidDate") == null ||
					map.get("paidDate") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD, map.get("paidDate").toString())));
			}
			
			return R.ok().setData(partyMembershipDuesForPageInfo).setMsg("查询党费缴纳记录成功");
		} else {
			return R.ok().setMsg("没有查询到党费缴纳记录");
		}
    }
    
    /**
     * 查询党费缴纳记录里的党组织
     * @param conditionMaps
     * @return
     */
    public R queryOrgInfoOfPMDM(Map<String, Object> conditionMaps, int pageNum, int pageSize) {
    	Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) subject.getPrincipal();
        if (conditionMaps == null) conditionMaps = new HashMap<>();
        if (AdminRoleUtil.isPlantAdmin()) {	//如果是平台管理员
        	//不做任何处理
        } else if (AdminRoleUtil.isOrgAdmin()) {	//如果是组织管理员
        	if (sysUser.getOrgId() == null) {
        		return R.ok().setCode(100).setMsg("组织管理员请设置所属的组织，如果是党员请加入组织");
        	}
        	conditionMaps.put("orgInfoId", sysUser.getOrgId());
        } else {	//个人用户，即党员
        	return null;
        }
    	PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> orgInfos = partyMembershipDuesManageMapper.queryOrgInfoOfPMDM(conditionMaps);	//开始查询，没有条件则查询所有组织关系
		PageInfo<Map<String, Object>> orgInfosForPageInfo = new PageInfo<>(orgInfos);
		if (orgInfosForPageInfo.getList() != null && !orgInfosForPageInfo.getList().isEmpty()) {	//是否查询到数据
			return R.ok().setData(orgInfosForPageInfo).setMsg("查询党组织成功");
		} else {
			return R.ok().setMsg("没有查询到党组织");
		}
    }
    
    /**
     * 查询党费缴纳记录里的党组织
     * @param conditionMaps
     * @return
     */
    public R queryOrgInfoOfPMDMNotPage(Map<String, Object> conditionMaps) {
    	Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) subject.getPrincipal();
        if (conditionMaps == null) conditionMaps = new HashMap<>();
        if (AdminRoleUtil.isPlantAdmin()) {	//如果是平台管理员
        	//不做任何处理
        } else if (AdminRoleUtil.isOrgAdmin()) {	//如果是组织管理员
        	if (sysUser.getOrgId() == null) {
        		return R.ok().setCode(100).setMsg("组织管理员请设置所属的组织，如果是党员请加入组织");
        	}
        	conditionMaps.put("orgInfoId", sysUser.getOrgId());
        } else {	//个人用户，即党员
        	return null;
        }
    	List<Map<String, Object>> orgInfos = partyMembershipDuesManageMapper.queryOrgInfoOfPMDM(conditionMaps);
    	if (orgInfos != null && !orgInfos.isEmpty()) {	//是否查询到数据
			return R.ok().setData(orgInfos).setMsg("查询党组织成功");
		} else {
			return R.ok().setMsg("没有查询到党组织");
		}
    }
    
    /**
     * 查询此组织的缴费统计
     * @param conditionMaps
     * @return
     */
    public R queryPMDMChartForOrgInfo(Map<String, Object> conditionMaps) {
    	if (conditionMaps == null || conditionMaps.get("orgInfoId") == null || conditionMaps.get("orgInfoId") == "") 
    		return R.ok().setMsg("没有查询到数据");
    	Date startTime = null;
    	Date endTime = null;
    	if (conditionMaps.get("paidDateStart") != null && conditionMaps.get("paidDateStart") != "") {
    		startTime = DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)conditionMaps.get("paidDateStart"));
    		startTime = DateUtil.getDateOfMonthStartDayTime(startTime);
    		if (conditionMaps.get("paidDateEnd") != null && conditionMaps.get("paidDateEnd") != "") {	//两个都有值
    			endTime = DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)conditionMaps.get("paidDateEnd"));
    			endTime = DateUtil.getDateOfMonthEndDayTime(endTime);
    		} else {
    			endTime = DateUtil.getDateOfMonthEndDayTime(new Date());
    		}
    		if (endTime.before(startTime)) {
				return R.error().setMsg("时间选择错误");
			}
    	} else if (conditionMaps.get("paidDateEnd") != null && conditionMaps.get("paidDateEnd") != "") {
    		endTime = DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)conditionMaps.get("paidDateEnd"));
    		Calendar ca = Calendar.getInstance();
    		ca.setTime(endTime);
    		ca.add(Calendar.YEAR, -1);
			endTime = DateUtil.getDateOfMonthEndDayTime(endTime);
			startTime = DateUtil.getDateOfMonthStartDayTime(ca.getTime());
    	} else {
    		endTime = DateUtil.getDateOfMonthEndDayTime(new Date());
    		Calendar ca = Calendar.getInstance();
    		ca.setTime(endTime);
    		ca.add(Calendar.YEAR, -1);
    		startTime = DateUtil.getDateOfMonthStartDayTime(ca.getTime());
    	}
    	
    	List<PartyMembershipDuesStatus> paidStatus = partyMembershipDuesStatusMapper.queryPMDSs(null);	//查询全部状态
    	
    	List<String> legendDatas = new ArrayList<>();
    	List<String> dateLines = new ArrayList<>();
    	List<List<Integer>> datas = new ArrayList<>();
    	List<List<Double>> lineDatas = new ArrayList<>();
    	
    	for (PartyMembershipDuesStatus pmds : paidStatus) {	//提示文本
    		legendDatas.add(pmds.getName());
		}
    	
    	while (startTime.before(endTime)) {	//每个月循环
    		Date temporaryEndTime = DateUtil.getDateOfMonthEndDayTime(startTime);
    		Calendar ca = Calendar.getInstance();
    		ca.setTime(startTime);
    		dateLines.add(String.valueOf(ca.get(Calendar.YEAR)) + "-" + (ca.get(Calendar.MONTH) + 1) + "月");
    		int i = 0;
    		for (i = 0; i < paidStatus.size(); i++) {	//查询每个月的支付状态
    			if (datas.size() == i) {
    				List<Integer> data = new ArrayList<>();
    				datas.add(data);
    			}
    			PartyMembershipDuesStatus pmds = paidStatus.get(i);
				conditionMaps.put("payStatus", pmds.getId());
				if ("未缴".equals(pmds.getName())) {	//如果未缴，则没有缴费日期，按应缴日期查询
					conditionMaps.remove("paidDateStart");
					conditionMaps.remove("paidDateEnd");
					conditionMaps.put("shouldPayDateStart", startTime);
		    		conditionMaps.put("shouldPayDateEnd", temporaryEndTime);
				} else {
					conditionMaps.remove("shouldPayDateStart");
					conditionMaps.remove("shouldPayDateEnd");
		    		conditionMaps.put("paidDateStart", startTime);
		    		conditionMaps.put("paidDateEnd", temporaryEndTime);
				}
				List<Map<String, Object>> paidStatusCount = partyMembershipDuesManageMapper.queryPartyMembershipDues(conditionMaps);
				datas.get(i).add(paidStatusCount == null ? 0 : paidStatusCount.size());
			}
    		int count = 0;
    		for (int j = 0; j < paidStatus.size(); j++) {
    			if (lineDatas.size() == j) {
    				List<Double> data = new ArrayList<>();
    				lineDatas.add(data);
    			}
    			count += datas.get(j).get(datas.get(j).size() - 1);
			}
    		for (int j = 0; j < paidStatus.size(); j++) {  
    			double f = 0;
    			if (count != 0) {
    				BigDecimal b =  BigDecimal.valueOf(((double)datas.get(j).get(datas.get(j).size() - 1)/count)*100);
    				f = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();    				
    			}
    			lineDatas.get(j).add(count == 0 ? 0 : f);
			}
    		ca.add(Calendar.MONTH, +1);
    		ca.set(Calendar.DAY_OF_MONTH, 1);
    		startTime = DateUtil.getDateOfMonthStartDayTime(ca.getTime());
    	}
    	
    	Map<String, Object> results = new HashMap<>();
    	results.put("legendDatas", legendDatas);
    	results.put("dateLines", dateLines);
    	results.put("datas", datas);
    	results.put("lineDatas", lineDatas);
    	
    	return R.ok().setData(results);
    }
    
    /**
	 * 下载党费缴纳记录导入excel格式示例
	 * @param baseUser 条件
	 * @return
	 */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public void exportPMDMExcelExample(HttpServletResponse response) {
    	excelForPartyMembersshipDuesManage.exportPMDMExcelExample(response);
    }
    
    /**
	 * 批量导入党费缴纳记录
	 * @param file 文件
	 * @return
	 */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R importPMDMsExcel(HttpServletResponse response, MultipartFile file) {
    	return excelForPartyMembersshipDuesManage.importPMDMsExcel(response, file);
    }
    
    /**
	 * 批量导出党费缴纳记录
	 * @param response 条件
	 * @return
	 */
    public void exportPMDMExcel(HttpServletResponse response, @RequestParam Map<String, Object> conditionMaps) {
    	excelForPartyMembersshipDuesManage.exportPMDMExcel(response, conditionMaps);
    }
}
