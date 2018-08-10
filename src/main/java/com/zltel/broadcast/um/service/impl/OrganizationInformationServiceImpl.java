package com.zltel.broadcast.um.service.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.common.util.AdminRoleUtil;
import com.zltel.broadcast.um.bean.IntegralChangeType;
import com.zltel.broadcast.um.bean.IntegralConstitute;
import com.zltel.broadcast.um.bean.OrganizationInformation;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.dao.IntegralChangeTypeMapper;
import com.zltel.broadcast.um.dao.IntegralConstituteMapper;
import com.zltel.broadcast.um.dao.OrganizationInformationMapper;
import com.zltel.broadcast.um.dao.OrganizationRelationMapper;
import com.zltel.broadcast.um.service.OrganizationInformationService;
import com.zltel.broadcast.um.util.DateUtil;
import com.zltel.broadcast.um.util.XMLUtil;

@Service
public class OrganizationInformationServiceImpl extends BaseDaoImpl<OrganizationInformation> implements OrganizationInformationService {
	
	@Resource
    private OrganizationInformationMapper organizationInformationMapper;
	@Resource
    private OrganizationRelationMapper organizationRelationMapper;
	@Resource
    private IntegralConstituteMapper integralConstituteMapper;
	@Resource
    private IntegralChangeTypeMapper integralChangeTypeMapper;
	@Override
    public BaseDao<OrganizationInformation> getInstince() {
        return this.organizationInformationMapper;
    }
	
	/**
     * 根据条件查询组织信息树,为了点击树节点显示节点下的所有组织，已废弃现在使用queryOrgInfosForMap方法得到该节点的下一级组织
     * @param organizationInformation 条件
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	@Deprecated
    public R queryOrgInfos(OrganizationInformation organizationInformation) throws Exception {
		/*//先查询自身数据，在递归查询所有的子集
		//查询所有？把所有的父节点查询出在递归
		//所有根节点都有一个虚拟的父节点，id为-1，点击查询所有传入父节点的id（-1），
		//但虚拟父节点不存在，所以省略查虚拟父节点信息直接查询虚拟父节点的子节点，即全部数据
		if(organizationInformation == null) {
			organizationInformation = new OrganizationInformation();
			organizationInformation.setOrgInfoId(-1);
		}
		if (organizationInformation != null && organizationInformation.getOrgInfoId() == -1) {	
			organizationInformation.setOrgInfoParentId(-1);
			organizationInformation.setOrgInfoId(null);
		}
		
		List<OrganizationInformation> organizationInformationsOfMengBi = new ArrayList<>();
		List<OrganizationInformation> organizationInformations = organizationInformationMapper.queryOrgInfos(organizationInformation);	//所选节点的数据
		organizationInformationsOfMengBi.addAll(organizationInformations);
		queryOrgInfosOfMengBi(organizationInformations, organizationInformation, organizationInformationsOfMengBi);
		
		if (organizationInformationsOfMengBi != null && organizationInformationsOfMengBi.size() > 0) {	//是否查询到数据
			return R.ok().setData(organizationInformationsOfMengBi).setMsg("查询组织信息成功");
		} else {
			return R.ok().setMsg("没有查询到组织信息");
		}*/
		return null;
    }
	
	
	/**
	 * 递归查询当前节点包括节点下的所有数据（为queryOrgInfos方法服务）
	 */
	@Transactional(rollbackFor=java.lang.Exception.class)
	@Deprecated
	private void queryOrgInfosOfMengBi(List<OrganizationInformation> organizationInformations, 
			OrganizationInformation organizationInformation, List<OrganizationInformation> organizationInformationsOfMengBi) {
		/*if (organizationInformations != null && organizationInformations.size() > 0) {
			for (OrganizationInformation organizationInformation2 : organizationInformations) {
				organizationInformation.setOrgInfoParentId(organizationInformation2.getOrgInfoId());
				organizationInformation.setOrgInfoId(null);
				List<OrganizationInformation> ois = organizationInformationMapper.queryOrgInfos(organizationInformation);
				organizationInformationsOfMengBi.addAll(ois);
				queryOrgInfosOfMengBi(ois, organizationInformation, organizationInformationsOfMengBi);
			}
		}*/
	}
	
	/**
	 * 得到用户数量
	 * @param organizationInformation
	 * @return
	 */
	public R queryOrgRelationsNewForUserId(Map<String, Object> organizationInformation) {
		List<Map<String, Object>> orgMembers = organizationRelationMapper.queryOrgRelationsNewForUserId(organizationInformation);
		if(orgMembers != null && orgMembers.size() > 0) {
			return R.ok().setData(orgMembers);
		} else {
			return R.ok().setMsg("没有查询到用户数量");
		}
	}
	
	/**
     * 根据条件查所有询组织信息
     * @param organizationInformation 条件
     * @return
     * @since 
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public R queryOrgInfosForMap(Map<String, Object> organizationInformation, int pageNum, int pageSize) throws Exception {
		Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) subject.getPrincipal();
        if (organizationInformation == null) organizationInformation = new HashMap<>();
        if (AdminRoleUtil.isPlantAdmin()) {	//如果是平台管理员
        	//不做任何处理
        } else if (AdminRoleUtil.isOrgAdmin()) {	//如果是组织管理员
        	if (sysUser.getOrgId() == null) {
        		return R.ok().setCode(100).setMsg("组织管理员请设置所属的组织，如果是党员请加入组织");
        	}
        	organizationInformation.put("orgInfoId", sysUser.getOrgId());
        } else {	//个人用户，即党员
        	if (sysUser.getUserType() == 0) {
        		return R.ok().setCode(100).setMsg("非党员账户请先设置管理员类型");
        	} else if (sysUser.getUserType() == 1) {
        		if (sysUser.getOrgId() == null) {
            		return R.ok().setCode(100).setMsg("未成为组织成员，请加入任一组织后查看");
            	}
        		organizationInformation.put("orgInfoId", sysUser.getOrgId());
            }
        }
		
		PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> organizationInformations = organizationInformationMapper.queryOrgInfosForMap(organizationInformation);	//所选节点的数据
		PageInfo<Map<String, Object>> organizationInformationsForPageInfo = new PageInfo<>(organizationInformations);
		if (organizationInformationsForPageInfo != null && organizationInformationsForPageInfo.getList() != null &&
				organizationInformationsForPageInfo.getList().size() > 0) {
			for (Map<String, Object> oiMap : organizationInformationsForPageInfo.getList()) {	//遍历组织查询组织相关信息
				if (oiMap.get("orgInfoId") != null) {
					Map<String, Object> map = new HashMap<>();
					map.put("orgRltInfoId", oiMap.get("orgInfoId"));
					List<Map<String, Object>> orgMembers = new ArrayList<>();
					orgMembers = organizationRelationMapper.queryOrgRelationsNewForUserId(map);
					oiMap.put("orgMemberNum", orgMembers.size() == 0 ? 0 : orgMembers.size());	//组织人员数量
					
					map.put("orgInfoId", oiMap.get("orgInfoId"));
					List<Map<String, Object>> orgChildrensAll = new ArrayList<>();
					orgChildrensAll = this.queryThisOrgChildren(map);
					oiMap.put("orgChildrensNum", orgChildrensAll.size() == 0 ? 0 : orgChildrensAll.size());	//子组织数量
					
					//查询高层信息
					map.put("orgDutyParentId", -1);
					List<Map<String, Object>> orgLevel1s = organizationRelationMapper.queryOrgRelationsNew(map);	//高层1
					oiMap.put("orgLevel1s", orgLevel1s);
					//保存上一级职责id，用于查询下一级职责，上级同一职责可能有多人，
					//如果遍历查询会导致重复查询下层人数
					Set<Integer> orgDutyId = new HashSet<>();	
					if(orgLevel1s != null && orgLevel1s.size() > 0) {
						for (Map<String, Object> orgLevel : orgLevel1s) {
							orgDutyId.add((Integer)orgLevel.get("orgDutyId"));
							orgLevel.put("birthDate", 
								DateUtil.formatDate(DateUtil.YYYY_MM_DD, orgLevel.get("birthDate") == null ||
								orgLevel.get("birthDate") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD, orgLevel.get("birthDate").toString())));
							
							orgLevel.put("partyType", orgLevel.get("partyType") == null || orgLevel.get("partyType") == "" ? 
								null : (int)orgLevel.get("partyType") == 1 ? "正式党员" : "预备党员");
							
							orgLevel.put("partyStatus", orgLevel.get("partyStatus") == null || orgLevel.get("partyStatus") == "" ? 
								null : (int)orgLevel.get("partyStatus") == 1 ? "正常" : "停止党籍");
							
							Date birthDay = DateUtil.toDate(DateUtil.YYYY_MM_DD, orgLevel.get("birthDate") == null || orgLevel.get("birthDate") == "" ?
									null : orgLevel.get("birthDate").toString());
							orgLevel.put("age", PartyUserInfoServiceImpl.getPartyUserAge(birthDay));
						}
					}
					
					List<Map<String, Object>> orgLevel2s = new ArrayList<>();	//高层2
					for (Integer orgLevel : orgDutyId) {
						map.put("orgDutyParentId", orgLevel);
						orgLevel2s.addAll(organizationRelationMapper.queryOrgRelationsNew(map));
					}
					oiMap.put("orgLevel2s", orgLevel2s);
					
					//查询当天加入组织的人员数量（如果在今天之前没有出现此人在此组织担任的角色，则此人是今天加入组织）
					map.clear();
					map.put("orgRltInfoId", oiMap.get("orgInfoId"));
					Date date = new Date();
					map.put("orgRltStartJoinTime", DateUtil.getDateOfStartTime(date));
					map.put("orgRltEndJoinTime", DateUtil.getDateOfEndTime(date));
					map.put("sex", "男");
					List<Map<String, Object>> thisOrgNowTimeMans = organizationRelationMapper.queryOrgRelationsNewForUserId(map);
					map.put("sex", "女");
					List<Map<String, Object>> thisOrgNowTimeWoMans = organizationRelationMapper.queryOrgRelationsNewForUserId(map);
					int thisOrgNowTimeManCounts = 0;
					int thisOrgNowTimeWoManCounts = 0;
					if (thisOrgNowTimeMans != null && thisOrgNowTimeMans.size() > 0) {
						map.clear();
						for (Map<String, Object> thisOrgNowTimeMan : thisOrgNowTimeMans) {
							map.put("orgRltEndJoinTime", DateUtil.getDateOfStartTime(date));	//当天之前的时间
							map.put("orgRltUserId", thisOrgNowTimeMan.get("orgRltUserId"));
							map.put("orgRltInfoId", oiMap.get("orgInfoId"));
							map.put("sex", "男");
							List<Map<String, Object>> thisOrgBeforeTimeMans = organizationRelationMapper.queryOrgRelationsNewForUserId(map);
							if (thisOrgBeforeTimeMans != null && thisOrgBeforeTimeMans.size() > 0) {
								continue;
							} else {
								thisOrgNowTimeManCounts++;
							}
						}
					}
					if (thisOrgNowTimeWoMans != null && thisOrgNowTimeWoMans.size() > 0) {
						map.clear();
						for (Map<String, Object> thisOrgNowTimeWoMan : thisOrgNowTimeWoMans) {
							map.put("orgRltEndJoinTime", DateUtil.getDateOfStartTime(date));	//当天之前的时间
							map.put("orgRltUserId", thisOrgNowTimeWoMan.get("orgRltUserId"));
							map.put("orgRltInfoId", oiMap.get("orgInfoId"));
							map.put("sex", "女");
							List<Map<String, Object>> thisOrgBeforeTimeWoMans = organizationRelationMapper.queryOrgRelationsNewForUserId(map);
							if (thisOrgBeforeTimeWoMans != null && thisOrgBeforeTimeWoMans.size() > 0) {
								continue;
							} else {
								thisOrgNowTimeWoManCounts++;
							}
						}
					}
					oiMap.put("thisOrgNowTimeManCounts", thisOrgNowTimeManCounts);
					oiMap.put("thisOrgNowTimeWoManCounts", thisOrgNowTimeWoManCounts);
				}
				
			}
			return R.ok().setData(organizationInformationsForPageInfo).setMsg("查询组织信息成功");
		} else {
			return R.ok().setMsg("没有查询到组织信息");
		}
	}
	
	/**
	 * 查询当前组织下的所有子组织（查询全部，在组织信息里调用此方法查看子组织的数量）
	 * @param organizationInformation
	 * @return
	 */
	private List<Map<String, Object>> queryThisOrgChildren(Map<String, Object> organizationInformation) {
		
		List<Map<String, Object>> orgChildrensAll = new ArrayList<>();	//保存子组织信息
		List<Map<String, Object>> organizationInformations = new ArrayList<>();	//保存条件
		organizationInformations.add(organizationInformation);
		if (organizationInformation == null) return null;
		this.queryOrgInfoChildrens(organizationInformations, orgChildrensAll);
		return orgChildrensAll;
	}
	
	/**
	 * 递归查询子组织
	 * @param organizationInformations
	 * @param orgChildrensAll
	 */
	private void queryOrgInfoChildrens(List<Map<String, Object>> organizationInformations, 
			List<Map<String, Object>> orgChildrensAll) {
		if (organizationInformations != null && organizationInformations.size() > 0) {
			for (Map<String, Object> organizationInformation2 : organizationInformations) {
				Map<String, Object> organizationInformation = new HashMap<>();
				organizationInformation.put("orgInfoParentId", organizationInformation2.get("orgInfoId"));
				organizationInformation.remove("orgInfoId");
				List<Map<String, Object>> ois = organizationInformationMapper.queryOrgInfosForMap(organizationInformation);
				if (ois != null && ois.size() > 0) {
					orgChildrensAll.addAll(ois);	//用来保存总数
				}
				
				queryOrgInfoChildrens(ois, orgChildrensAll);
			}
		}
	}
	
	private int _index = 0;
	
	/**
     * 查询当前组织下的所有子组织（分页查询，组织详细信息里点击弹窗调用此方法）
     * @param organizationInformation
     * @return
     * @throws Exception
     */
    public R queryThisOrgChildren(Map<String, Object> organizationInformation, int pageNum, int pageSize) throws Exception {
    	List<Map<String, Object>> orgChildrens = new ArrayList<>();
    	List<Map<String, Object>> orgChildrensAll = new ArrayList<>();
    	List<Map<String, Object>> organizationInformations = new ArrayList<>();
    	organizationInformations.add(organizationInformation);
    	if (organizationInformation == null) return R.error().setMsg("查询子组织出错");
    	_index = 0;
    	flag = true;
    	this.queryOrgInfosOfMengBiForMap(organizationInformations, orgChildrens, orgChildrensAll, pageNum, pageSize);
    	
    	PageInfo<Map<String, Object>> orgRelationsForPageInfo = new PageInfo<>(orgChildrens);
    	orgRelationsForPageInfo.setList(orgChildrens);
    	orgRelationsForPageInfo.setPageNum(pageNum);
    	orgRelationsForPageInfo.setPageSize(pageSize);
    	orgRelationsForPageInfo.setTotal(orgChildrensAll.size());
    	return R.ok().setData(orgRelationsForPageInfo).setMsg("查询成功");
    }
    
    private boolean flag = true;
    
    /**
     * 查询子组织（分页，递归查询）
     * @param organizationInformations	//查询到的当前子组织
     * @param organizationInformationsOfMengBi	//所有子组织
     * @param index 已经读取的个数
     */
    private void queryOrgInfosOfMengBiForMap(List<Map<String, Object>> organizationInformations, 
    		List<Map<String, Object>> orgChildrens, List<Map<String, Object>> orgChildrensAll, int pageNum, int pageSize) {
    	if (organizationInformations != null && organizationInformations.size() > 0) {
			for (Map<String, Object> organizationInformation2 : organizationInformations) {
				Map<String, Object> organizationInformation = new HashMap<>();
				organizationInformation.put("orgInfoParentId", organizationInformation2.get("orgInfoId"));
				organizationInformation.remove("orgInfoId");
				List<Map<String, Object>> ois = organizationInformationMapper.queryOrgInfosForMap(organizationInformation);
				if (ois != null && ois.size() > 0) {
					for (Map<String, Object> oiMap : ois) {
						if (oiMap.get("orgInfoId") == null) continue;
						Map<String, Object> map = new HashMap<>();
						map.put("orgRltInfoId", oiMap.get("orgInfoId"));
						List<Map<String, Object>> orgMembers = new ArrayList<>();
						orgMembers = organizationRelationMapper.queryOrgRelationsNew(map);
						oiMap.put("orgMemberNum", orgMembers.size());
					}
					
					if (flag) {
						int start = (pageNum - 1) * pageSize;
						int end = start + pageSize - 1;
						for (Map<String, Object> map : ois) {
							if (_index > end) {
								flag = false;
								break;
							} else if (_index >= start) {
								orgChildrens.add(map);
							}
							_index++;
						}
					}
					orgChildrensAll.addAll(ois);	//用来保存总数
				}
				
				queryOrgInfosOfMengBiForMap(ois, orgChildrens, orgChildrensAll, pageNum, pageSize);
			}
		}
    }
	
	/**
	 * 递归查询当前节点包括节点下的所有数据（为queryOrgInfosForMap服务）
	 */
	@Deprecated
	@Transactional(rollbackFor=java.lang.Exception.class)
	private void queryOrgInfosOfMengBiForMap(List<Map<String, Object>> organizationInformations, 
			Map<String, Object> organizationInformation, List<Map<String, Object>> organizationInformationsOfMengBi) {
		/*if (organizationInformations != null && organizationInformations.size() > 0) {
			for (Map<String, Object> organizationInformation2 : organizationInformations) {
				organizationInformation.put("orgInfoParentId", organizationInformation2.get("orgInfoId"));
				organizationInformation.remove("orgInfoId");
				List<Map<String, Object>> ois = organizationInformationMapper.queryOrgInfosForMap(organizationInformation);
				if (ois != null && ois.size() > 0) {
					for (Map<String, Object> oiMap : ois) {
						if (oiMap.get("orgInfoId") == null) continue;
						Map<String, Object> map = new HashMap<>();
						map.put("orgRltInfoId", oiMap.get("orgInfoId"));
						List<Map<String, Object>> orgMembers = new ArrayList<>();
						orgMembers = organizationRelationMapper.queryOrgRelationsNew(map);
						oiMap.put("orgMemberNum", orgMembers.size());
					}
				}
				
				organizationInformationsOfMengBi.addAll(ois);
				queryOrgInfosOfMengBiForMap(ois, organizationInformation, organizationInformationsOfMengBi);
			}
		}*/
	}
    
    /**
     * 根据条件查询组织信息并生成树
     * @param organizationInformation
     * @return
     * @throws Exception
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    @Deprecated
    public R queryOrgInfosToTree(OrganizationInformation organizationInformation) throws Exception {
    	if (organizationInformation == null) organizationInformation = new OrganizationInformation();
    	organizationInformation.setOrgInfoParentId(-1);	//查询最上级组织
		List<OrganizationInformation> organizationInformations = organizationInformationMapper
				.queryOrgInfos(organizationInformation);//这是根节点
		
		if (organizationInformations != null && organizationInformations.size() > 0) {
			List<TreeNode<OrganizationInformation>> orgInfoTrees = new ArrayList<>();
			for (OrganizationInformation orgInfo : organizationInformations) {
				TreeNode<OrganizationInformation> orgInfoTree = new TreeNode<>();
				orgInfoTree.setData(orgInfo);
				orgInfoTrees.add(orgInfoTree);
			}
			toTreeNode(orgInfoTrees);
			return R.ok().setData(orgInfoTrees).setMsg("查询组织上下级关系成功");
		} else {
			return R.ok().setMsg("没有查询到组织上下级关系");
		}
		
    }
    
    /**
     * 根据条件查询组织信息并生成树（使用中）
     * @param orgInfoConditions
     * @return
     * @throws Exception
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgInfosToTree(Map<String, Object> orgInfoConditions) {
    	Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) subject.getPrincipal();
        if (orgInfoConditions == null) orgInfoConditions = new HashMap<>();
        if (AdminRoleUtil.isPlantAdmin()) {	//如果是平台管理员
        	//不做任何处理
        } else if (AdminRoleUtil.isOrgAdmin()) {	//如果是组织管理员，只查询自己组织及下属组织关系
        	if (sysUser.getOrgId() == null) {
        		return R.ok().setCode(100).setMsg("组织管理员请设置所属的组织，如果是党员请加入组织");
        	}
        	orgInfoConditions.remove("orgInfoParentId");
        	orgInfoConditions.put("orgInfoId", sysUser.getOrgId());
        } else {	//个人用户，即党员，不能查看组织图关系
        	return null;
        }
    	
    	List<Map<String, Object>> orgInfoTrees = organizationInformationMapper.queryOrgInfosForMap(orgInfoConditions);
    	List<TreeNode<Map<String, Object>>> treeNodes = new ArrayList<>();
    	if (orgInfoTrees != null && orgInfoTrees.size() > 0) {
    		for (Map<String, Object> map : orgInfoTrees) {
    			TreeNode<Map<String, Object>> treeNode = new TreeNode<Map<String, Object>>();
    			treeNode.setData(map);
    			treeNodes.add(treeNode);
			}
    		treeNodes.get(0).getData().put("count", 0);
    		this.toTreeNodes(treeNodes, 0, treeNodes.get(0));
    	}
    	return R.ok().setData(treeNodes);
    }
    
    /**
     * 查询积分结构树
     * @param conditions
     * @return
     */
    public R queryOrgIntegralConstituteToTree(Map<String, Object> conditions) {
    	List<Map<String, Object>> icsMaps = integralConstituteMapper.queryOrgIntegralConstitute(conditions);
    	List<TreeNode<Map<String, Object>>> treeNodes = new ArrayList<>();
    	if (icsMaps != null && icsMaps.size() > 0) {
    		for (Map<String, Object> ic : icsMaps) {
    			TreeNode<Map<String, Object>> treeNode = new TreeNode<Map<String, Object>>();
    			treeNode.setData(ic);
    			treeNodes.add(treeNode);
			}
    		this.toTreeNodes_IC(treeNodes);
    	}
    	
    	return R.ok().setData(treeNodes);
    }
    
    private void toTreeNodes_IC(List<TreeNode<Map<String, Object>>> treeNodes) {
    	if (treeNodes != null && treeNodes.size() > 0) {
    		for (TreeNode<Map<String, Object>> treeNode : treeNodes) {
    			Map<String, Object> condition = new HashMap<>();
				condition.put("orgId", treeNode.getData().get("orgId"));
				condition.put("parentIcId", treeNode.getData().get("icId"));
				List<Map<String, Object>> orgInfoTrees = integralConstituteMapper.queryOrgIntegralConstitute(condition);
				List<TreeNode<Map<String, Object>>> treeNodes2 = new ArrayList<>();
				if (orgInfoTrees != null && orgInfoTrees.size() > 0) {
					for (Map<String, Object> map : orgInfoTrees) {
						TreeNode<Map<String, Object>> treeNode2 = new TreeNode<Map<String, Object>>();
		    			treeNode2.setData(map);
		    			treeNodes2.add(treeNode2);
					}
				} else {
					//当为最低级时，查看当前积分节点是否有对应的加分减分方法
					IntegralChangeType ict = new IntegralChangeType();
					ict.setIcId(Integer.parseInt(String.valueOf(treeNode.getData().get("icId"))));
					ict.setOperation(0);	//是否有扣分处理方式
					List<IntegralChangeType> icts = integralChangeTypeMapper.queryICT(ict);
					treeNode.getData().put("isReduceIntegral", (icts == null || icts.size() == 0) ? false : true);
					ict.setOperation(1);	//是否有加分处理方式
					icts = integralChangeTypeMapper.queryICT(ict);
					treeNode.getData().put("isAddIntegral", (icts == null || icts.size() == 0) ? false : true);;
				}
				treeNode.setChildren(treeNodes2);
				treeNode.getData().put("isChildrens", (treeNode.getChildren() == null || treeNode.getChildren().size() == 0) ? false : true);
				toTreeNodes_IC(treeNode.getChildren());
			}
    	}
    }
    
    /**
	 * 生成树
	 * @param organizationInfo
	 */
    private void toTreeNodes(List<TreeNode<Map<String, Object>>> treeNodes, int count, TreeNode<Map<String, Object>> node) {
    	if (treeNodes != null && treeNodes.size() > 0) {
    		for (int i = 0; i < treeNodes.size(); i++) {
    			TreeNode<Map<String, Object>> treeNode = treeNodes.get(i);
    			if (i == 0) {	//表示新开一层，结果是上级count+1
    				count = (int)node.getData().get("count");
    				count++;
    			} else if ((int)treeNode.getData().get("orgInfoParentId") == -1) {	//最顶层，为第一层
    				count = 1;
    			} else {	//本层循环，count不变
    				count = (int)treeNodes.get(i - 1).getData().get("count");
    			}
    			treeNode.getData().put("count", count);
    			Map<String, Object> condition = new HashMap<>();
				condition.put("orgInfoParentId", treeNode.getData().get("orgInfoId"));
				List<Map<String, Object>> orgInfoTrees = organizationInformationMapper.queryOrgInfosForMap(condition);
				List<TreeNode<Map<String, Object>>> treeNodes2 = new ArrayList<>();
				if (orgInfoTrees != null && orgInfoTrees.size() > 0) {
					for (Map<String, Object> map : orgInfoTrees) {
						TreeNode<Map<String, Object>> treeNode2 = new TreeNode<Map<String, Object>>();
		    			treeNode2.setData(map);
		    			treeNodes2.add(treeNode2);
					}
				}
				treeNode.setChildren(treeNodes2);
				toTreeNodes(treeNode.getChildren(), count, treeNode);
			}
    	}
    }
    
    /**
	 * 生成树
	 * @param organizationInfo
	 */
	@Transactional(rollbackFor=java.lang.Exception.class)
	@Deprecated
	private void toTreeNode(List<TreeNode<OrganizationInformation>> orgInfoTrees) {
		if (orgInfoTrees != null) {
			for (TreeNode<OrganizationInformation> treeNode : orgInfoTrees) {
				OrganizationInformation organizationInformation = new OrganizationInformation();
				organizationInformation.setOrgInfoParentId(treeNode.getData().getOrgInfoId());	//设置信息查询子节点
				List<OrganizationInformation> organizationInformations = organizationInformationMapper.queryOrgInfos(organizationInformation);	//这是子节点
				
				if (organizationInformations != null && organizationInformations.size() > 0) {	
					for (OrganizationInformation organizationInformation2 : organizationInformations) {	//遍历子节点加入到此根节点
						TreeNode<OrganizationInformation> orgInfoTrees2 = new TreeNode<>();
						orgInfoTrees2.setData(organizationInformation2);	//生成子节点信息
						
						if (treeNode.getChildren() == null || treeNode.getChildren().size() == 0) {
							List<TreeNode<OrganizationInformation>> treeNode2 = new ArrayList<>();
							treeNode2.add(orgInfoTrees2);
							treeNode.setChildren(treeNode2);
						} else {
							treeNode.getChildren().add(orgInfoTrees2);
						}
						
					}
					toTreeNode(treeNode.getChildren());	//递归查询
				}
			}
		}
	}
	
	/**
     * 查询省份
     * @param organizationInformation
     * @return
     */
    public R queryOrgInfosCommitteeProvince(OrganizationInformation organizationInformation) throws Exception {
    	List<OrganizationInformation> organizationInformations = organizationInformationMapper.queryOrgInfosCommitteeProvince(organizationInformation);	//开始查询，没有条件则查询所有基础用户信息
		if (organizationInformations != null && organizationInformations.size() > 0) {	//是否查询到数据
			return R.ok().setData(organizationInformations).setMsg("查询省份信息情况成功");
		} else {
			return R.ok().setMsg("没有查询到省份信息");
		}
    }
    
    /**
     * 查询城市
     * @param organizationInformation
     * @return
     */
    public R queryOrgInfosCommitteeCity(OrganizationInformation organizationInformation) throws Exception {
    	List<OrganizationInformation> organizationInformations = organizationInformationMapper.queryOrgInfosCommitteeCity(organizationInformation);	//开始查询，没有条件则查询所有基础用户信息
		if (organizationInformations != null && organizationInformations.size() > 0) {	//是否查询到数据
			return R.ok().setData(organizationInformations).setMsg("查询城市信息情况成功");
		} else {
			return R.ok().setMsg("没有查询到城市信息");
		}
    }
    
    /**
     * 查询地区
     * @param organizationInformation
     * @return
     */
    public R queryOrgInfosCommitteeArea(OrganizationInformation organizationInformation) throws Exception {
    	List<OrganizationInformation> organizationInformations = organizationInformationMapper.queryOrgInfosCommitteeArea(organizationInformation);	//开始查询，没有条件则查询所有基础用户信息
		if (organizationInformations != null && organizationInformations.size() > 0) {	//是否查询到数据
			return R.ok().setData(organizationInformations).setMsg("查询地区信息情况成功");
		} else {
			return R.ok().setMsg("没有查询到地区信息");
		}
    }
    
    
    /**
     * 新增组织信息
     * @param organizationInfo 要新增的组织信息
     * @return
     * @throws Exception
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public R insertOrgInfo(OrganizationInformation organizationInformation) throws Exception {
		if (organizationInformation != null) {
			organizationInformation.setOrgInfoId(null);	//自增，不需要设置值
			int count = this.insertSelective(organizationInformation);	//开始添加组织信息
			if (count == 1) {	//受影响的行数，判断是否修改成功
				Integer insertedOrgId = organizationInformationMapper.queryInsertedOrgId();
				insertOrgIntegralConstitutes(XMLUtil.getOrgIntegralConstitutes(), insertedOrgId);
				return R.ok().setMsg("组织信息添加成功。");
			} else {	//没有受影响行数表示添加失败
				throw new Exception();
			}
		} else {	//添加一定需要一个组织信息
			throw new Exception();
		}
    }
	
	/**
	 * 在添加组织时添加默认的积分结构
	 * @param treeNodes
	 * @param insertedOrgId
	 */
	private void insertOrgIntegralConstitutes(List<TreeNode<Map<String, String>>> treeNodes, Integer insertedOrgId) {
		Deque<TreeNode<Map<String, String>>> nodeDeque = new ArrayDeque<>();
		for (TreeNode<Map<String, String>> treeNode : treeNodes) {
			nodeDeque.add(treeNode);
		    while (!nodeDeque.isEmpty()) {
		    	TreeNode<Map<String, String>> node = nodeDeque.pollFirst();
		    	
		    	Map<String, String> data = node.getData();
				IntegralConstitute ic = new IntegralConstitute();
				ic.setOrgId(insertedOrgId);
				ic.setType(data.get("type"));
				ic.setDescribes(data.get("describes"));
				ic.setParentIcId(Integer.parseInt(data.get("insertedIcId")));
				ic.setIsInnerIntegral(1);
				integralConstituteMapper.insertSelective(ic);
				Integer insertedIcId = integralConstituteMapper.queryInsertedIcId();
		    	
		    	List<TreeNode<Map<String, String>>> children = node.getChildren();
		        if (children != null && !children.isEmpty()) {
		            for (TreeNode<Map<String, String>> child : children) {
		            	child.getData().put("insertedIcId", String.valueOf(insertedIcId));
		                nodeDeque.add(child);
		            }
		        }
		    }
		}
	    
		
		/*if (treeNodes != null && treeNodes.size() > 0) {
			for (TreeNode<Map<String, String>> treeNode : treeNodes) {
				Map<String, String> node = treeNode.getData();
				IntegralConstitute ic = new IntegralConstitute();
				ic.setOrgId(insertedOrgId);
				ic.setType(node.get("type"));
				ic.setDescribes(node.get("describes"));
				ic.setParentIcId("1".equals(node.get("level")) ? -1 : insertedIcId);
				integralConstituteMapper.insertSelective(ic);
				insertedIcId = integralConstituteMapper.queryInsertedIcId();
				insertOrgIntegralConstitutes(treeNode.getChildren(), insertedOrgId, insertedIcId);
			}
		}*/
	}
	
	
	/**
     * 修改组织信息
     * @param organizationInfo 要修改的组织信息
     * @return	
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R updateOrgInfo(OrganizationInformation organizationInformation) throws Exception {
		if (organizationInformation != null) {
			int count = this.updateByPrimaryKeySelective(organizationInformation);	//开始修改组织信息
			if (count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("组织信息修改成功。");
			} else {
				throw new Exception();	//没有受影响行数表示修改失败
			}
		} else {
			throw new Exception();	//修改一定需要一个组织信息
		}
    }
	
	private StringBuffer havaPeopleOrgInfoId;	//有成员的组织id，用于删除组织时提示有成员的组织
	private boolean havaPeople;
	
	/**
     * 删除组织
     * @param organizationInformation
     * @return
     * @throws Exception
     */
    public R deleteOrgInfo(OrganizationInformation organizationInformation) throws Exception {
    	if(organizationInformation != null) {
    		List<OrganizationInformation> organizationInformations = new ArrayList<>();
    		organizationInformations.add(organizationInformation);
    		List<OrganizationInformation> organizationInformationAndChildren = new ArrayList<>();	//保存该组织及子组织
    		organizationInformationAndChildren.add(organizationInformation);
    		this.queryOrgInfosAndChildren(organizationInformations, organizationInformationAndChildren);	//遍历查询组织及子组织
    		
    		havaPeopleOrgInfoId = new StringBuffer();
			havaPeople = false;
    		for (OrganizationInformation condition : organizationInformationAndChildren) {	//遍历组织查询是否有成员
    			Map<String, Object> orMap = new HashMap<>();
    			orMap.put("orgRltInfoId", condition.getOrgInfoId());
    			List<Map<String, Object>> orsMap = organizationRelationMapper.queryOrgRelationsNew(orMap);
    			if (orsMap != null && orsMap.size() > 0) {	//表示有成员 ，记录组织信息
    				havaPeopleOrgInfoId.append(condition.getOrgInfoId() + " ");
					havaPeople = true;
    			}
			}
    		
    		if (havaPeople) {
    			return R.error().setMsg("组织或子组织下有成员，不能删除，请删除成员后在删除此节点。"
    					+ "包含成员的组织ID:" + havaPeopleOrgInfoId);
    		}
    		
    		int deleteCount = 0;
    		for (OrganizationInformation condition : organizationInformationAndChildren) {
    			deleteCount += this.deleteByPrimaryKey(condition.getOrgInfoId());	//开始递归删除组织及子组织信息
			}
    		integralConstituteMapper.deleteByOrgId(organizationInformation.getOrgInfoId());
    		
    		if (organizationInformationAndChildren.size() == deleteCount) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除组织信息 " + deleteCount + "条。");
			} else {	//没有受影响行数或者受影响行数与要删除的组织信息数量不匹配表示删除失败
				throw new Exception();
			}
    	} else {
    		throw new Exception();
    	}
    }
    
    /**
	 * 递归查询当前节点包括节点下的所有数据（递归删除组织时）
	 */
	@Transactional(rollbackFor=java.lang.Exception.class)
	private void queryOrgInfosAndChildren(List<OrganizationInformation> organizationInformations, 
			List<OrganizationInformation> organizationInformationsOfMengBi) {
		if (organizationInformations != null && organizationInformations.size() > 0) {
			for (OrganizationInformation organizationInformation : organizationInformations) {
				OrganizationInformation condition = new OrganizationInformation();
				condition.setOrgInfoParentId(organizationInformation.getOrgInfoId());
				List<OrganizationInformation> ois = organizationInformationMapper.queryOrgInfos(condition);
				organizationInformationsOfMengBi.addAll(ois);
				queryOrgInfosAndChildren(ois, organizationInformationsOfMengBi);
			}
		}
	}
    
    /**
	 * 删除组织时判断此组织以及子组织是否有人，有人终止删除
	 * 需要将所有人清空才能删除
	 * @return
	 */
    @Deprecated
	@Transactional(rollbackFor=java.lang.Exception.class)
	private void isHavaPeople(List<OrganizationInformation> organizationInformations, 
			OrganizationInformation organizationInformation) throws Exception {
		if (organizationInformations != null && organizationInformations.size() > 0) {
			for (OrganizationInformation organizationInformation2 : organizationInformations) {	//查询组织的子组织
				organizationInformation.setOrgInfoParentId(organizationInformation.getOrgInfoId());
				organizationInformation.setOrgInfoId(null);
				
				//查询的到的子组织
				List<OrganizationInformation> ois = organizationInformationMapper.queryOrgInfos(organizationInformation2);	
				
				if (ois != null && (ois == null ? false : ois.size() != 0)) {
					for (OrganizationInformation organizationInformation3 : ois) {	//遍历查询此组织下的成员信息
						Map<String, Object> orMap = new HashMap<String, Object>();
						orMap.put("orgRltInfoId", organizationInformation3.getOrgInfoId());
						List<Map<String, Object>> orsMap = organizationRelationMapper.queryOrgRelationsNew(orMap);
						if (orsMap != null && orsMap.size() > 0) {	//此节点有成员，禁止删除
							havaPeopleOrgInfoId.append(organizationInformation3.getOrgInfoId() + " ");
							havaPeople = true;
						}
						isHavaPeople(ois, organizationInformation3);	//递归查询子组织
					}
				}
				
			}
		}		
	}

    
    
    
    
    
	@Override
	public int updateByPrimaryKeyWithBLOBs(OrganizationInformation record) {
		return organizationInformationMapper.updateByPrimaryKeyWithBLOBs(record);
	}
    
}
