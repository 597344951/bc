package com.zltel.broadcast.um.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.PartyMembershipDuesManage;
import com.zltel.broadcast.um.dao.PartyMembershipDuesManageMapper;
import com.zltel.broadcast.um.service.PartyMembershipDuesManageService;
import com.zltel.broadcast.um.util.DateUtil;

@Service
public class PartyMembershipDuesManageServiceImpl extends BaseDaoImpl<PartyMembershipDuesManage> 
															implements PartyMembershipDuesManageService {
	
	@Resource
    private PartyMembershipDuesManageMapper partyMembershipDuesManageMapper;
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
    	PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> partyMembershipDues = partyMembershipDuesManageMapper.queryPartyMembershipDues(conditionMaps);	//开始查询，没有条件则查询所有
		PageInfo<Map<String, Object>> partyMembershipDuesForPageInfo = new PageInfo<>(partyMembershipDues);
		if (partyMembershipDuesForPageInfo != null && partyMembershipDuesForPageInfo.getList() != null 
				&& partyMembershipDuesForPageInfo.getList().size() > 0) {	//是否查询到数据
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
}
