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
import com.zltel.broadcast.um.bean.IntegralConstitute;
import com.zltel.broadcast.um.dao.IntegralConstituteMapper;
import com.zltel.broadcast.um.service.IntegralConstituteService;

@Service
public class IntegralConstituteServiceImpl extends BaseDaoImpl<IntegralConstitute> implements IntegralConstituteService  {
	@Resource
    private IntegralConstituteMapper integralConstituteMapper;
	@Override
    public BaseDao<IntegralConstitute> getInstince() {
        return this.integralConstituteMapper;
    }
	
	/**
     * 添加组织积分结构
     * @param conditions
     * @return
     */
	public R insertIntegralConstitute(IntegralConstitute ic) {
		if (ic.getOrgId() == null) return R.error().setMsg("添加发生错误");
		int count  = integralConstituteMapper.insertSelective(ic);
		if (count == 1) {
			return R.ok().setMsg("添加成功");
		} else {
			return R.error().setMsg("添加发生错误");
		}
	}
	
	/**
     * 查询拥有积分结构的组织
     * @param conditions
     * @return
     */
    public R queryOrgInfoForIc(Map<String, Object> conditions, int pageNum, int pageSize) {
    	PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> orgInfoForIcs = integralConstituteMapper.queryOrgInfoForIc(conditions);
		PageInfo<Map<String, Object>> orgInfoForIcsForPageInfo = new PageInfo<>(orgInfoForIcs);
		if (orgInfoForIcsForPageInfo != null && orgInfoForIcsForPageInfo.getList() != null 
				&& orgInfoForIcsForPageInfo.getList().size() > 0) {
			return R.ok().setData(orgInfoForIcsForPageInfo).setMsg("查询组织信息成功");
		} else {
			return R.ok().setMsg("没有查询到组织信息");
		}
    }
    
    /**
     * 查询该组织拥有的党员，仅为党员积分功能服务
     * @param conditions
     * @return
     */
    public R queryPartyUserInfoAndIcInfo(Map<String, Object> conditions, int pageNum, int pageSize) {
    	PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> partyUserInfoAndIcInfos = integralConstituteMapper.queryPartyUserInfoAndIcInfo(conditions);
		PageInfo<Map<String, Object>> partyUserInfoAndIcInfosPageInfo = new PageInfo<>(partyUserInfoAndIcInfos);
		if (partyUserInfoAndIcInfosPageInfo != null && partyUserInfoAndIcInfosPageInfo.getList() != null 
				&& partyUserInfoAndIcInfosPageInfo.getList().size() > 0) {
			return R.ok().setData(partyUserInfoAndIcInfosPageInfo).setMsg("查询组织信息成功");
		} else {
			return R.ok().setMsg("没有查询到组织信息");
		}
    }
}