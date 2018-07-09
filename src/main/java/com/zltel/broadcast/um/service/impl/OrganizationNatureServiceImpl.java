package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.OrganizationNature;
import com.zltel.broadcast.um.dao.OrganizationNatureMapper;
import com.zltel.broadcast.um.service.OrganizationNatureService;

@Service
public class OrganizationNatureServiceImpl extends BaseDaoImpl<OrganizationNature> implements OrganizationNatureService {
	@Resource
    private OrganizationNatureMapper organizationNatureMapper;
	
	@Override
    public BaseDao<OrganizationNature> getInstince() {
        return this.organizationNatureMapper;
    }
	
	/**
     * 根据条件查询组织性质
     * @param organizationNature 条件
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgNaturesNotPage(OrganizationNature organizationNature) {
		List<OrganizationNature> organizationNatures = organizationNatureMapper.queryOrgNatures(organizationNature);	//开始查询，没有条件则查询所有组织性质
		if (organizationNatures != null && organizationNatures.size() > 0) {	//是否查询到数据
			return R.ok().setData(organizationNatures).setMsg("查询组织性质成功");
		} else {
			return R.ok().setMsg("没有查询到组织性质");
		}
    }
}
