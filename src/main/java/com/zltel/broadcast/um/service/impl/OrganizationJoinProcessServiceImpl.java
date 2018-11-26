package com.zltel.broadcast.um.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.OrganizationJoinProcess;
import com.zltel.broadcast.um.dao.OrganizationJoinProcessMapper;
import com.zltel.broadcast.um.service.OrganizationJoinProcessService;

@Service
public class OrganizationJoinProcessServiceImpl extends BaseDaoImpl<OrganizationJoinProcess> implements OrganizationJoinProcessService {
	@Resource
    private OrganizationJoinProcessMapper organizationJoinProcessMapper;
	
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
}
