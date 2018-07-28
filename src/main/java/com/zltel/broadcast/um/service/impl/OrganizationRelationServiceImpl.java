package com.zltel.broadcast.um.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.BaseUserInfo;
import com.zltel.broadcast.um.bean.OrganizationRelation;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.dao.BaseUserInfoMapper;
import com.zltel.broadcast.um.dao.OrganizationRelationMapper;
import com.zltel.broadcast.um.dao.SysUserMapper;
import com.zltel.broadcast.um.service.OrganizationRelationService;
import com.zltel.broadcast.um.util.DateUtil;

@Service
public class OrganizationRelationServiceImpl extends BaseDaoImpl<OrganizationRelation>
		implements OrganizationRelationService {
	
	@Resource
    private OrganizationRelationMapper organizationRelationMapper;
	@Resource
    private SysUserMapper sysUserMapper;
	@Resource
    private BaseUserInfoMapper baseUserInfoMapper;
	
	@Override
    public BaseDao<OrganizationRelation> getInstince() {
        return this.organizationRelationMapper;
    }

	/**
     * 查询组织关系
     * @param organizationRelation 条件
     * @return	查询得到的组织信息
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgRelations(Map<String, Object> orgRelationConditiona, int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<HashMap<String, Object>> organizationRelations = organizationRelationMapper.queryOrgRelations(orgRelationConditiona);	//开始查询，没有条件则查询所有组织关系
		PageInfo<HashMap<String, Object>> orgRelationsForPageInfo = new PageInfo<>(organizationRelations);
		if (orgRelationsForPageInfo != null && orgRelationsForPageInfo.getList() != null && orgRelationsForPageInfo.getList().size() > 0) {	//是否查询到数据
			return R.ok().setData(orgRelationsForPageInfo).setMsg("查询组织关系成功");
		} else {
			return R.ok().setMsg("没有查询到组织关系");
		}
    }
	
	/**
     * 查询组织关系(新版)
     * @param organizationRelation 条件
     * @return	查询得到的组织信息
     */
    public R queryOrgRelationNewsNotPage(Map<String, Object> orgRelationConditiona) {
    	List<Map<String, Object>> organizationRelations = organizationRelationMapper.queryOrgRelationsNew(orgRelationConditiona);	//开始查询，没有条件则查询所有组织关系
    	if (organizationRelations != null && organizationRelations.size() > 0) {
    		return R.ok().setData(organizationRelations).setMsg("查询组织关系成功");
		} else {
			return R.ok().setMsg("没有查询到组织关系");
		}
    }
	
	/**
     * 查询组织关系(新版)
     * 同一个用户在一个组织中可能会有多个角色，这样组织关系的条数就会比用户多，所以不能用组织关系的条数
     * 来充当用户数量，要先合并在查询
     * @param organizationRelation 条件
     * @return	查询得到的组织信息
     */
    public R queryOrgRelationNews(Map<String, Object> orgRelationConditiona, int pageNum, int pageSize) throws Exception {
    	PageHelper.startPage(pageNum, pageSize);
    	//查询组织下的用户关系信息
		List<Map<String, Object>> organizationRelations = organizationRelationMapper.queryOrgRelationsNewForUserId(orgRelationConditiona);	//开始查询，没有条件则查询所有组织关系
		PageInfo<Map<String, Object>> orgRelationsForPageInfo = new PageInfo<>(organizationRelations);
		if (orgRelationsForPageInfo != null && orgRelationsForPageInfo.getList() != null && orgRelationsForPageInfo.getList().size() > 0) {	//是否查询到数据
			List<Map<String, Object>> orgLetions = new ArrayList<>();
			for (Map<String, Object> or : organizationRelations) {
				Map<String, Object> conditionMap = new HashMap<>();
				conditionMap.put("orgRltUserId", or.get("orgRltUserId"));
				//该用户在此组织的全部职责，可能>1条
				List<Map<String, Object>> orgletionMaps = organizationRelationMapper.queryOrgRelationsNew(conditionMap);
				Map<String, Object> orgLetion = new HashMap<>();
				int count = 0;
				if (orgletionMaps != null && orgletionMaps.size() > 0) {
					for (Map<String, Object> organizationRelationMap : orgletionMaps) {
						if (count == 0) {
							orgLetion = organizationRelationMap;
						} else {
							orgLetion.put("orgDutyName", orgLetion.get("orgDutyName") + "、" + organizationRelationMap.get("orgDutyName"));
						}
						organizationRelationMap.put("birthDate", 
							DateUtil.formatDate(DateUtil.YYYY_MM_DD, organizationRelationMap.get("birthDate") == null ||
							organizationRelationMap.get("birthDate") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD, organizationRelationMap.get("birthDate").toString())));
						
						organizationRelationMap.put("partyType", organizationRelationMap.get("partyType") == null || organizationRelationMap.get("partyType") == "" ? 
							null : (int)organizationRelationMap.get("partyType") == 1 ? "正式党员" : "预备党员");
						
						organizationRelationMap.put("partyStatus", organizationRelationMap.get("partyStatus") == null || organizationRelationMap.get("partyStatus") == "" ? 
							null : (int)organizationRelationMap.get("partyStatus") == 1 ? "正常" : "停止党籍");
						
						Date birthDay = DateUtil.toDate(DateUtil.YYYY_MM_DD, organizationRelationMap.get("birthDate") == null || organizationRelationMap.get("birthDate") == "" ?
							null : organizationRelationMap.get("birthDate").toString());
						organizationRelationMap.put("age", PartyUserInfoServiceImpl.getPartyUserAge(birthDay));
						organizationRelationMap.put("orgRltJoinTime", DateUtil.formatDate(DateUtil.YYYY_MM_DD, organizationRelationMap.get("orgRltJoinTime") == null ||
								organizationRelationMap.get("orgRltJoinTime") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD, organizationRelationMap.get("orgRltJoinTime").toString())));
						count++;
					}
				}
				orgLetions.add(orgLetion);
			}
			orgRelationsForPageInfo.getList().clear();	//清空原数据
			orgRelationsForPageInfo.getList().addAll(orgLetions);
			return R.ok().setData(orgRelationsForPageInfo).setMsg("查询组织关系成功");
		} else {
			return R.ok().setMsg("没有查询到组织关系");
		}
    }
	
	/**
     * 查询组织关系
     * @param organizationRelation 条件
     * @return	查询得到的组织信息
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgRelationsNotPage(Map<String, Object> organizationRelation) throws Exception {
		List<HashMap<String, Object>> organizationRelations = organizationRelationMapper.queryOrgRelations(organizationRelation);	//开始查询，没有条件则查询所有组织关系
		if (organizationRelations != null && organizationRelations.size() > 0) {	//是否查询到数据
			return R.ok().setData(organizationRelations).setMsg("查询组织关系成功");
		} else {
			return R.ok().setMsg("没有查询到组织关系");
		}
    }
    
    
    /**
     * 删除组织关系
     * @param organizationRelation 要删除的组织关系
     * @return	
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteOrgRelation(OrganizationRelation organizationRelation) throws Exception {
		if(organizationRelation != null) {
			int count = 0;
			if (organizationRelation.getOrgRltId() == null) {
				throw new Exception();	//删除组织关系一定需要id，依据id进行组织关系删除
			}
			count += this.deleteByPrimaryKey(organizationRelation.getOrgRltId());	//开始删除组织关系
			
			if (count == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除组织关系 " + count + "条。");
			} else {	//没有受影响行数或者受影响行数与要删除的组织关系数量不匹配表示删除失败
				throw new Exception();
			}
		} else {	//删除一定需要组织关系
			throw new Exception();
		}
    }
    
    /**
     * 根据组织id删除组织关系
     * @param organizationRelation 条件
     * @return
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteOrgRelationByOrgInfoId(OrganizationRelation organizationRelation) throws Exception {
		if(organizationRelation != null) {
			int count = 0;
			if (organizationRelation.getOrgRltInfoId() == null) {
				throw new Exception();	//删除组织关系一定需要id，依据id进行组织关系删除
			}
			count += organizationRelationMapper.deleteOrgRelationByOrgInfoId(organizationRelation.getOrgRltInfoId());	//开始删除组织关系
			
			if (count == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除组织关系 " + count + "条。").setData(count);
			} else {	
				return R.ok().setData(0).setMsg("没有需要删除的组织关系");
			}
		} else {	//删除一定需要组织关系
			throw new Exception();
		}
    }
    
    /**
     * 根据职责id删除组织关系
     * @param organizationRelation 条件
     * @return
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteOrgRelationByOrgDutyId(OrganizationRelation organizationRelation) throws Exception {
		if(organizationRelation != null) {
			int count = 0;
			if (organizationRelation.getOrgRltDutyId() == null) {
				throw new Exception();	//删除组织关系一定需要id，依据id进行组织关系删除
			}
			count = organizationRelationMapper.deleteOrgRelationByOrgDutyId(organizationRelation.getOrgRltDutyId());	//开始删除组织关系
			
			if (count == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除组织关系 " + count + "条。").setData(count);
			} else {	
				return R.ok().setMsg("没有需要删除的组织关系").setData(0);
			}
		} else {	//删除一定需要组织关系
			throw new Exception();
		}
    }
    
    /**
     * 根据用户id删除组织关系
     * @param organizationRelation 条件
     * @return
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteOrgRelationByUserId(OrganizationRelation organizationRelation) throws Exception {
		if(organizationRelation != null) {
			int count = 0;
			if (organizationRelation.getOrgRltUserId() == null) {
				throw new Exception();	//删除组织关系一定需要id，依据id进行组织关系删除
			}
			count = organizationRelationMapper.deleteOrgRelationByUserId(organizationRelation.getOrgRltUserId());	//开始删除组织关系
			
			if (count == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除组织关系 " + count + "条。").setData(count);
			} else {	
				return R.ok().setMsg("没有需要删除的组织关系").setData(0);
			}
		} else {	//删除一定需要组织关系
			throw new Exception();
		}
    }
    
    /**
     * 新增组织关系
     * @param organizationRelation 要新增的组织关系
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R insertOrgRelation(OrganizationRelation organizationRelation, List<Integer> orgRltDutys) throws Exception {
		if (organizationRelation != null) {
			//删除所有组织关系，确保只能加入一个组织
			organizationRelationMapper.deleteOrgRelationByUserId(organizationRelation.getOrgRltUserId());
			int count = 0;
			if (orgRltDutys != null && orgRltDutys.size() > 0) {
				if (organizationRelation.getOrgRltJoinTime() == null) organizationRelation.setOrgRltJoinTime(new Date());
				for (Integer orgDuty : orgRltDutys) {
					organizationRelation.setOrgRltId(null);	//自增，不需要设置值
					organizationRelation.setOrgRltDutyId(orgDuty);
					count += this.insertSelective(organizationRelation);	//开始添加组织关系
				}
				//在党员账号里更新加入组织id
				BaseUserInfo bui = baseUserInfoMapper.selectByPrimaryKey(organizationRelation.getOrgRltUserId());
				SysUser su = new SysUser();
				su.setUsername(bui.getIdCard());
				List<SysUser> sus = sysUserMapper.querySysUsers(su);
				if (sus != null && sus.size() == 1) {
					su = sus.get(0);
					su.setOrgId(organizationRelation.getOrgRltInfoId());
					sysUserMapper.updateByPrimaryKeySelective(su);
				} else {
					throw new Exception();
				}
				if (count == orgRltDutys.size()) {	//受影响的行数，判断是否修改成功
					return R.ok().setMsg("组织关系添加成功。");
				} else {	//没有受影响行数表示添加失败
					throw new Exception();
				}
			} else {
				return R.ok().setMsg("成功移除组织");
			}
			
			
		} else {	//添加一定需要一个组织关系
			throw new Exception();
		}
    }

}
