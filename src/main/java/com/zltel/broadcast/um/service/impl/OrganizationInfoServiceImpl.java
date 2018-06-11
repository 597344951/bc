package com.zltel.broadcast.um.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.um.bean.OrganizationInfo;
import com.zltel.broadcast.um.bean.OrganizationRelation;
import com.zltel.broadcast.um.bean.OrganizationType;
import com.zltel.broadcast.um.dao.OrganizationInfoMapper;
import com.zltel.broadcast.um.dao.OrganizationTypeMapper;
import com.zltel.broadcast.um.service.OrganizationInfoService;
import com.zltel.broadcast.um.service.OrganizationRelationService;

@Service
@Deprecated
public class OrganizationInfoServiceImpl extends BaseDaoImpl<OrganizationInfo> implements OrganizationInfoService {

	@Resource
    private OrganizationInfoMapper organizationInfoMapper;
	
	@Autowired
	private OrganizationRelationService organizationRelationService;
	
	@Resource
    private OrganizationTypeMapper organizationTypeMapper;
	
	private List<OrganizationInfo> organizationInfosOfMengBi;
	
	@Override
    public BaseDao<OrganizationInfo> getInstince() {
        return this.organizationInfoMapper;
    }
	

	/**
     * 查询组织信息
     * @param organizationInfo 条件
     * @return	查询得到的组织信息
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgInfos(OrganizationInfo organizationInfo) throws Exception {
		//先查询自身数据，在递归查询所有的子集
		//查询所有？把所有的父节点查询出在递归
		//所有根节点都有一个虚拟的父节点，id为-1，点击查询所有传入父节点的id（-1），
		//但虚拟父节点不存在，所以省略查虚拟父节点信息直接查询虚拟父节点的子节点，即全部数据
		if (organizationInfo != null && organizationInfo.getOrgInfoId() == -1) {	
			organizationInfo.setOrgInfoParentId(-1);
			organizationInfo.setOrgInfoId(null);
		}
		
		organizationInfosOfMengBi = new ArrayList<OrganizationInfo>();
		List<OrganizationInfo> organizationInfos = organizationInfoMapper.queryOrgInfos(organizationInfo);	//所选节点的数据
		organizationInfosOfMengBi.addAll(organizationInfos);
		queryOrgInfosOfMengBi(organizationInfos, organizationInfo);
		
		if (organizationInfosOfMengBi != null && organizationInfosOfMengBi.size() > 0) {	//是否查询到数据
			return R.ok().setData(organizationInfosOfMengBi).setMsg("查询组织信息成功");
		} else {
			return R.ok().setMsg("没有查询到组织信息");
		}
    }
	
	/**
     * 查询组织信息
     * @param organizationInfo 条件
     * @return	查询得到的组织信息
     */
	@Deprecated
    public R queryOrgInfosForInsertRelation(OrganizationInfo organizationInfo) throws Exception {
    	List<OrganizationType> orgTypes = organizationTypeMapper.queryOrgTypes(null);
    	if (orgTypes != null && (orgTypes == null ? false : orgTypes.size() != 0)) {
    		List<Map<String, List<OrganizationInfo>>> orgInfosMaps = new ArrayList<Map<String, List<OrganizationInfo>>>();
    		for (OrganizationType ot : orgTypes) {	//查询每个组织类型下的组织
    			OrganizationInfo orgInfo = new OrganizationInfo();
    			orgInfo.setOrgInfoTypeId(ot.getOrgTypeId());
				List<OrganizationInfo> orgInfos = organizationInfoMapper.queryOrgInfos(null);	
			}
    	}
    	return null;
    }
	
	/**
	 * 递归查询当前节点包括节点下的所有数据
	 */
	@Transactional(rollbackFor=java.lang.Exception.class)
	private void queryOrgInfosOfMengBi(List<OrganizationInfo> organizationInfos, OrganizationInfo organizationInfo) {
		if (organizationInfos != null && organizationInfos.size() > 0) {
			for (OrganizationInfo organizationInfo2 : organizationInfos) {
				organizationInfo.setOrgInfoParentId(organizationInfo2.getOrgInfoId());
				organizationInfo.setOrgInfoId(null);
				List<OrganizationInfo> ois = organizationInfoMapper.queryOrgInfos(organizationInfo);
				organizationInfosOfMengBi.addAll(ois);
				queryOrgInfosOfMengBi(ois, organizationInfo);
			}
		}
	}
	
	/**
     * 查询组织信息树
     * @param organizationInfo 条件
     * @return	查询得到的组织信息
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgInfosToTree() throws Exception {
		OrganizationInfo organizationInfo = new OrganizationInfo();
		organizationInfo.setOrgInfoParentId(-1);
		List<OrganizationInfo> organizationInfos = organizationInfoMapper.queryOrgInfos(organizationInfo);	//这是根节点
		
		if (organizationInfos != null && organizationInfos.size() > 0) {	//是否查询到数据
			List<TreeNode<OrganizationInfo>> orgInfoTrees = new ArrayList<TreeNode<OrganizationInfo>>();
			for (OrganizationInfo organizationInfo2 : organizationInfos) {
				TreeNode<OrganizationInfo> orgInfoTree = new TreeNode<OrganizationInfo>();
				orgInfoTree.setData(organizationInfo2);
				orgInfoTrees.add(orgInfoTree);
			}
			toTreeNode(orgInfoTrees);
			return R.ok().setData(orgInfoTrees).setMsg("查询组织上下级关系成功");
		} else {
			return R.ok().setMsg("没有查询到组织上下级关系");
		}
    }
	
	/**
	 * 生成树
	 * @param organizationInfo
	 */
	@Transactional(rollbackFor=java.lang.Exception.class)
	private void toTreeNode(List<TreeNode<OrganizationInfo>> orgInfoTree) {
		if (orgInfoTree != null) {
			for (TreeNode<OrganizationInfo> treeNode : orgInfoTree) {
				OrganizationInfo organizationInfo = new OrganizationInfo();
				organizationInfo.setOrgInfoParentId(treeNode.getData().getOrgInfoId());	//设置信息查询子节点
				List<OrganizationInfo> organizationInfos = organizationInfoMapper.queryOrgInfos(organizationInfo);	//这是子节点
				
				if (organizationInfos != null && organizationInfos.size() > 0) {	
					for (OrganizationInfo organizationInfo2 : organizationInfos) {	//遍历子节点加入到此根节点
						TreeNode<OrganizationInfo> orgInfoTree2 = new TreeNode<OrganizationInfo>();
						orgInfoTree2.setData(organizationInfo2);	//生成子节点信息
						
						if (treeNode.getChildren() == null || treeNode.getChildren().size() == 0) {
							List<TreeNode<OrganizationInfo>> treeNode2 = new ArrayList<TreeNode<OrganizationInfo>>();
							treeNode2.add(orgInfoTree2);
							treeNode.setChildren(treeNode2);
						} else {
							treeNode.getChildren().add(orgInfoTree2);
						}
						
					}
					toTreeNode(treeNode.getChildren());	//递归查询
				}
			}
		}
	}
	
	/**
     * 查询组织信息
     * @param organizationInfo 条件
     * @return	查询得到的组织信息
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgInfosNotPage(OrganizationInfo organizationInfo) throws Exception {
		List<OrganizationInfo> OrganizationInfos = organizationInfoMapper.queryOrgInfos(organizationInfo);	//开始查询，没有条件则查询所有组织信息
		if (OrganizationInfos != null && OrganizationInfos.size() > 0) {	//是否查询到数据
			return R.ok().setData(OrganizationInfos).setMsg("查询组织信息成功");
		} else {
			return R.ok().setMsg("没有查询到组织信息");
		}
    }
    
    /**
     * 修改组织信息
     * @param organizationInfo 要修改的组织信息
     * @return	
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R updateOrgInfo(OrganizationInfo organizationInfo) throws Exception {
		if (organizationInfo != null) {
			int count = this.updateByPrimaryKeySelective(organizationInfo);	//开始修改组织信息
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
     * 删除组织信息
     * @param organizationInfo 要删除的组织信息
     * @return	
     */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteOrgInfo(OrganizationInfo organizationInfo) throws Exception {
		if(organizationInfo != null) {
			int count = 0;
			int deleteCount = 0;
			if (organizationInfo.getOrgInfoId() == null) {
				throw new Exception();	//删除组织信息一定需要id，依据id进行组织信息删除
			}
			Map<String, Object> orMap = new HashMap<String, Object>();
			orMap.put("orgRltInfoId", organizationInfo.getOrgInfoId());
			List<OrganizationRelation> ors = (List<OrganizationRelation>)organizationRelationService.queryOrgRelationsNotPage(orMap).get("data");
			if (ors != null && (ors == null ? false : ors.size() != 0)) {	//此节点有成员，禁止删除
				return R.error().setMsg("此组织包含有成员，不能删除，请删除成员后在删除此节点。包含成员的组织ID:" + organizationInfo.getOrgInfoId());
			}
			List<OrganizationInfo> organizationInfos = new ArrayList<OrganizationInfo>();	//所删除节点
			organizationInfos.add(organizationInfo);
			
			havaPeopleOrgInfoId = new StringBuffer();
			havaPeople = false;
			if(isHavaPeople(organizationInfos, new OrganizationInfo())) {
				return R.error().setMsg("子组织下有成员，不能删除，请删除成员后在删除此节点。包含成员的组织ID:" + havaPeopleOrgInfoId);
			}
			
			this.queryOrgInfos(organizationInfo);	//查询此节点包括节点下子节点的信息，查询结果会报讯到organizationInfosOfMengBi里
			//即将删除的组织数量
			count = organizationInfosOfMengBi.size();
			for (OrganizationInfo deleteOis : organizationInfosOfMengBi) {
				deleteCount += this.deleteByPrimaryKey(deleteOis.getOrgInfoId());	//开始递归删除组织及子组织信息
			}
			
			if (count == deleteCount) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("共计删除组织信息 " + count + "条。");
			} else {	//没有受影响行数或者受影响行数与要删除的组织信息数量不匹配表示删除失败
				throw new Exception();
			}
		} else {	//删除一定需要组织信息
			throw new Exception();
		}
    }
	
	/**
	 * 删除组织时判断此组织以及子组织是否有人，有人终止删除
	 * 需要将所有人清空才能删除
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor=java.lang.Exception.class)
	private boolean isHavaPeople(List<OrganizationInfo> organizationInfos, OrganizationInfo organizationInfo) throws Exception {
		if (organizationInfos != null && organizationInfos.size() > 0) {
			for (OrganizationInfo organizationInfo2 : organizationInfos) {	//查询组织的子组织
				organizationInfo.setOrgInfoParentId(organizationInfo2.getOrgInfoId());
				organizationInfo.setOrgInfoId(null);
				List<OrganizationInfo> ois = organizationInfoMapper.queryOrgInfos(organizationInfo);	//查询的到的子组织
				
				if (ois != null && (ois == null ? false : ois.size() != 0)) {
					for (OrganizationInfo organizationInfo3 : ois) {	//遍历查询此组织下的成员信息
						Map<String, Object> orMap = new HashMap<String, Object>();
						orMap.put("orgRltInfoId", organizationInfo.getOrgInfoId());
						List<OrganizationRelation> ors = (List<OrganizationRelation>)organizationRelationService.queryOrgRelationsNotPage(orMap).get("data");
						if (ors != null && (ors == null ? false : ors.size() != 0)) {	//此节点有成员，禁止删除
							havaPeopleOrgInfoId.append(organizationInfo3.getOrgInfoId() + " ");
							havaPeople = true;
						}
					}
				}
				
				isHavaPeople(ois, organizationInfo);	//递归查询子组织
			}
		}
		
		return havaPeople;
		
	}
    
    /**
     * 新增组织信息
     * @param organizationInfo 要新增的组织信息
     * @return
     * @throws Exception
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertOrgInfo(OrganizationInfo organizationInfo) throws Exception {
		if (organizationInfo != null) {
			organizationInfo.setOrgInfoId(null);	//自增，不需要设置值
			int count = this.insertSelective(organizationInfo);	//开始添加组织信息
			if (count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("组织信息添加成功。");
			} else {	//没有受影响行数表示添加失败
				throw new Exception();
			}
		} else {	//添加一定需要一个组织信息
			throw new Exception();
		}
    }

}
