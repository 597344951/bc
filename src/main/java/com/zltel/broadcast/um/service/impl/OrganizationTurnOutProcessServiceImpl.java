package com.zltel.broadcast.um.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.OrganizationTurnOutProcess;
import com.zltel.broadcast.um.dao.OrganizationTurnOutProcessMapper;
import com.zltel.broadcast.um.service.OrganizationTurnOutProcessService;

@Service
public class OrganizationTurnOutProcessServiceImpl extends BaseDaoImpl<OrganizationTurnOutProcess> implements OrganizationTurnOutProcessService {
	@Resource
    private OrganizationTurnOutProcessMapper organizationTurnOutProcessMapper;
	
	@Override
    public BaseDao<OrganizationTurnOutProcess> getInstince() {
        return this.organizationTurnOutProcessMapper;
    }
	
	/**
     * 创建组织时默认创建此组织的步骤
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R establishOrgInsertProcess(int orgId) throws Exception {
    	int[] defaultProcess = {1, 2};
    	
    	OrganizationTurnOutProcess otop = null;
    	for (int i = 0; i < defaultProcess.length; i++) {
    		otop = new OrganizationTurnOutProcess();
    		otop.setOrgId(orgId);
    		otop.setProcessId(defaultProcess[i]);
    		otop.setIndexNum(i);
    		int count = organizationTurnOutProcessMapper.insertSelective(otop);
    		if (count != 1) throw new Exception();
		}
    	return R.ok();
    }
	
	/**
     * 查询
     * @param orgId
     * @return
     */
    public R queryOrgTurnOutProcess(Map<String, Object> condition) {
    	List<Map<String, Object>> otops = organizationTurnOutProcessMapper.queryOrgTurnOutProcess(condition);
    	if (otops != null && otops.size() > 0) {
    		return R.ok().setData(otops);
    	}
    	return R.error().setMsg("为方便测试，你可能会看到此消息，正式使用不会出现");
    }
}
