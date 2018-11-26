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
import com.zltel.broadcast.um.bean.FlowParty;
import com.zltel.broadcast.um.dao.FlowPartyMapper;
import com.zltel.broadcast.um.service.FlowPartyService;

@Service
public class FlowPartyServiceImpl extends BaseDaoImpl<FlowParty> implements FlowPartyService {
	@Resource
    private FlowPartyMapper flowPartyMapper;
	
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
	
	
	

	@Override
	public int updateByPrimaryKeyWithBLOBs(FlowParty record) {
		return 0;
	}
}
