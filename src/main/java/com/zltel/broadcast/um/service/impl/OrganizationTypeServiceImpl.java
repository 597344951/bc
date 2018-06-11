package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.OrganizationType;
import com.zltel.broadcast.um.dao.OrganizationTypeMapper;
import com.zltel.broadcast.um.service.OrganizationTypeService;

@Service
public class OrganizationTypeServiceImpl extends BaseDaoImpl<OrganizationType>
		implements OrganizationTypeService {

	@Resource
    private OrganizationTypeMapper organizationTypeMapper;
	
	@Override
    public BaseDao<OrganizationType> getInstince() {
        return this.organizationTypeMapper;
    }
	
	/**
     * 查询组织类型
     * @param organizationType 条件
     * @return	查询得到的组织类型
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgTypes(OrganizationType organizationType, int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<OrganizationType> organizationTypes = organizationTypeMapper.queryOrgTypes(organizationType);	//开始查询，没有条件则查询所有组织类型
		PageInfo<OrganizationType> orgTypesForPageInfo = new PageInfo<>(organizationTypes);
		if (orgTypesForPageInfo != null && orgTypesForPageInfo.getList() != null && orgTypesForPageInfo.getList().size() > 0) {	//是否查询到数据
			return R.ok().setData(orgTypesForPageInfo).setMsg("查询组织类型成功");
		} else {
			return R.ok().setMsg("没有查询到组织类型");
		}
    }
	
	/**
     * 查询组织类型
     * @param organizationType 条件
     * @return	查询得到的组织类型
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgTypesNotPage(OrganizationType organizationType) throws Exception {
		List<OrganizationType> organizationTypes = organizationTypeMapper.queryOrgTypes(organizationType);	//开始查询，没有条件则查询所有组织类型
		if (organizationTypes != null && organizationTypes.size() > 0) {	//是否查询到数据
			return R.ok().setData(organizationTypes).setMsg("查询组织类型成功");
		} else {
			return R.ok().setMsg("没有查询到组织类型");
		}
    }

}
