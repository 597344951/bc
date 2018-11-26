package com.zltel.broadcast.um.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.um.bean.OrganizationDuty;
import com.zltel.broadcast.um.dao.OrganizationDutyMapper;
import com.zltel.broadcast.um.service.OrganizationDutyService;
import com.zltel.broadcast.um.util.JumpOutRecursionException;

@Service
public class OrganizationDutyServiceImpl extends BaseDaoImpl<OrganizationDuty> implements OrganizationDutyService {
	@Resource
    private OrganizationDutyMapper organizationDutyMapper;
	
	@Override
    public BaseDao<OrganizationDuty> getInstince() {
        return this.organizationDutyMapper;
    }
	
	/**
     * 查询组织职责
     * @param organizationDuty 条件
     * @return	查询得到的组织职责
     */
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgDutys(OrganizationDuty organizationDuty, int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<OrganizationDuty> organizationDutys = organizationDutyMapper.queryOrgDutys(organizationDuty);	//开始查询，没有条件则查询所有组织职责
		PageInfo<OrganizationDuty> orgDutysForPageInfo = new PageInfo<>(organizationDutys);
		if (orgDutysForPageInfo != null && orgDutysForPageInfo.getList() != null && orgDutysForPageInfo.getList().size() > 0) {	//是否查询到数据
			return R.ok().setData(orgDutysForPageInfo).setMsg("查询组织职责成功");
		} else {
			return R.ok().setMsg("没有查询到组织职责");
		}
    }
    
    /**
     * 查询组织职责
     * @param organizationDuty 条件
     * @return	查询得到的组织职责
     */
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgDutyForOrgInfoClick(OrganizationDuty organizationDuty) throws Exception {
		if (organizationDuty == null) 
			organizationDuty = new OrganizationDuty();
		organizationDuty.setOrgDutyParentId(-1);
		//这是组织所对应职责的根节点
		List<OrganizationDuty> srganizationDutys = organizationDutyMapper.queryOrgDutyForOrgInfoClick(organizationDuty);	
		
		if (srganizationDutys != null && srganizationDutys.size() > 0) {	//是否查询到数据
			List<TreeNode<OrganizationDuty>> orgDutyTrees = new ArrayList<TreeNode<OrganizationDuty>>();
			for (OrganizationDuty organizationDuty2 : srganizationDutys) {
				TreeNode<OrganizationDuty> orgInfoTree = new TreeNode<OrganizationDuty>();
				orgInfoTree.setData(organizationDuty2);
				orgDutyTrees.add(orgInfoTree);
			}
			toTreeNode(orgDutyTrees);
			return R.ok().setData(orgDutyTrees).setMsg("查询组织上下级关系成功");
		} else {
			return R.ok().setMsg("没有查询到组织上下级关系");
		}
    }
	
	/**
     * 查询组织职责
     * @param organizationDuty 条件
     * @return	查询得到的组织职责
     */
	public R queryOrgDutyTreeForOrgInfo (OrganizationDuty organizationDuty) throws Exception {
		if (organizationDuty == null) 
			organizationDuty = new OrganizationDuty();
		organizationDuty.setOrgDutyParentId(-1);
		//这是组织所对应职责的根节点
		List<OrganizationDuty> srganizationDutys = organizationDutyMapper.queryOrgDutys(organizationDuty);	
		
		if (srganizationDutys != null && srganizationDutys.size() > 0) {	//是否查询到数据
			List<TreeNode<OrganizationDuty>> orgDutyTrees = new ArrayList<TreeNode<OrganizationDuty>>();
			for (OrganizationDuty organizationDuty2 : srganizationDutys) {
				TreeNode<OrganizationDuty> orgInfoTree = new TreeNode<OrganizationDuty>();
				orgInfoTree.setData(organizationDuty2);
				orgDutyTrees.add(orgInfoTree);
			}
			toTreeNodeForOrgInfo(orgDutyTrees);
			return R.ok().setData(orgDutyTrees).setMsg("查询组织上下级关系成功");
		} else {
			return R.ok().setMsg("没有查询到组织上下级关系");
		}
	}
	
	/**
	 * 生成树
	 * @param organizationInfo
	 */
	@Transactional(rollbackFor=java.lang.Exception.class)
	private void toTreeNodeForOrgInfo(List<TreeNode<OrganizationDuty>> orgDutyTrees) {
		if (orgDutyTrees != null) {
			for (TreeNode<OrganizationDuty> treeNode : orgDutyTrees) {
				OrganizationDuty organizationDuty = new OrganizationDuty();
				organizationDuty.setOrgDutyParentId(treeNode.getData().getOrgDutyId());	//设置信息查询子节点
				List<OrganizationDuty> organizationDutys = organizationDutyMapper.queryOrgDutys(organizationDuty);	//这是子节点
				
				if (organizationDutys != null && organizationDutys.size() > 0) {	
					for (OrganizationDuty srganizationDuty2 : organizationDutys) {	//遍历子节点加入到此根节点
						TreeNode<OrganizationDuty> orgDutyTree2 = new TreeNode<OrganizationDuty>();
						orgDutyTree2.setData(srganizationDuty2);	//生成子节点信息
						
						if (treeNode.getChildren() == null || treeNode.getChildren().size() == 0) {
							List<TreeNode<OrganizationDuty>> treeNode2 = new ArrayList<TreeNode<OrganizationDuty>>();
							treeNode2.add(orgDutyTree2);
							treeNode.setChildren(treeNode2);
						} else {
							treeNode.getChildren().add(orgDutyTree2);
						}
						
					}
					toTreeNodeForOrgInfo(treeNode.getChildren());	//递归查询
				}
			}
		}
	}
    
    /**
	 * 生成树
	 * @param organizationInfo
	 */
	@Transactional(rollbackFor=java.lang.Exception.class)
	private void toTreeNode(List<TreeNode<OrganizationDuty>> orgDutyTrees) {
		if (orgDutyTrees != null) {
			for (TreeNode<OrganizationDuty> treeNode : orgDutyTrees) {
				OrganizationDuty organizationDuty = new OrganizationDuty();
				organizationDuty.setOrgDutyParentId(treeNode.getData().getOrgDutyId());	//设置信息查询子节点
				List<OrganizationDuty> organizationDutys = organizationDutyMapper.queryOrgDutyForOrgInfoClick(organizationDuty);	//这是子节点
				
				if (organizationDutys != null && organizationDutys.size() > 0) {	
					for (OrganizationDuty srganizationDuty2 : organizationDutys) {	//遍历子节点加入到此根节点
						TreeNode<OrganizationDuty> orgDutyTree2 = new TreeNode<OrganizationDuty>();
						orgDutyTree2.setData(srganizationDuty2);	//生成子节点信息
						
						if (treeNode.getChildren() == null || treeNode.getChildren().size() == 0) {
							List<TreeNode<OrganizationDuty>> treeNode2 = new ArrayList<TreeNode<OrganizationDuty>>();
							treeNode2.add(orgDutyTree2);
							treeNode.setChildren(treeNode2);
						} else {
							treeNode.getChildren().add(orgDutyTree2);
						}
						
					}
					toTreeNode(treeNode.getChildren());	//递归查询
				}
			}
		}
	}
    
    /**
     * 查询组织职责
     * @param organizationDuty 条件
     * @return	查询得到的组织职责
     */
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgDutysOfQueryRelations(OrganizationDuty organizationDuty) throws Exception {
		List<OrganizationDuty> organizationDutys = organizationDutyMapper.queryOrgDutysOfQueryRelations(null);	//开始查询，没有条件则查询所有组织职责
		if (organizationDutys != null && organizationDutys.size() > 0) {	//是否查询到数据
			return R.ok().setData(organizationDutys).setMsg("查询组织职责成功");
		} else {
			return R.ok().setMsg("没有查询到组织职责");
		}
    }
    
    /**
     * 查询组织职责
     * @param organizationDuty 条件
     * @return	查询得到的组织职责
     */
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryOrgDutysNotPage(OrganizationDuty organizationDuty) throws Exception {
		List<OrganizationDuty> organizationDutys = organizationDutyMapper.queryOrgDutys(organizationDuty);	//开始查询，没有条件则查询所有组织职责
		if (organizationDutys != null && organizationDutys.size() > 0) {	//是否查询到数据
			return R.ok().setData(organizationDutys).setMsg("查询组织职责成功");
		} else {
			return R.ok().setMsg("没有查询到组织职责");
		}
    }
    
    /**
     * 修改组织职责
     * @param organizationDuty 要修改的组织职责
     * @return	
     */
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R updateOrgDuty(OrganizationDuty organizationDuty) throws Exception {
		if (organizationDuty != null) {
			int count = this.updateByPrimaryKeySelective(organizationDuty);	//开始修改组织职责
			if (count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("组织职责修改成功。");
			} else {
				throw new Exception();	//没有受影响行数表示修改失败
			}
		} else {
			throw new Exception();	//修改一定需要一个组织职责
		}
    }
    
    /**
     * 删除组织职责
     * @param organizationDuty 要删除的组织职责
     * @return	
     */
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteOrgDuty(OrganizationDuty organizationDuty) throws Exception {
		OrganizationDuty od = new OrganizationDuty();
		od.setOrgDutyParentId(organizationDuty.getOrgDutyId());
		List<OrganizationDuty> orgDutys = organizationDutyMapper.queryOrgDutys(od);
		if (orgDutys != null && orgDutys.size() > 0) {
    		return R.error().setMsg("请先删除此节点下的子节点");
    	}
		
		List<Integer> orgDutyIds = new ArrayList<>();
		orgDutyIds.add(organizationDuty.getOrgDutyId());
    	try {
			this.checkNodeHaveUser(orgDutyIds);
		} catch (JumpOutRecursionException e) {
			return R.error().setMsg(e.getMsg());
		}
    	int count = organizationDutyMapper.deleteByPrimaryKey(organizationDuty.getOrgDutyId());
    	if (count == 1) {
    		return R.ok().setMsg("删除成功");
    	}
    	return R.ok().setMsg("删除失败");
    }
	
	private void checkNodeHaveUser(List<Integer> orgDutyIds) throws JumpOutRecursionException {
		if (orgDutyIds != null && orgDutyIds.size() > 0) {
    		for (Integer orgDutyId : orgDutyIds) {
    			OrganizationDuty od = new OrganizationDuty();
    			od.setOrgDutyId(orgDutyId);
    	    	List<OrganizationDuty> ods = organizationDutyMapper.queryOrgDutysOfQueryRelations(od);
    	    	if (ods != null && ods.size() > 0) {
    	    		throw new JumpOutRecursionException("该节点或子节点有对应用户，不能删除");
    	    	}
    	    	
    	    	//查询此节点下的子节点
    	    	od = new OrganizationDuty();
    	    	od.setOrgDutyParentId(orgDutyId);
    	    	List<OrganizationDuty> orgDutys = organizationDutyMapper.queryOrgDutys(od);
    	    	if (orgDutys != null && orgDutys.size() > 0) {
    	    		List<Integer> _orgDutyIds = new ArrayList<>();
    	    		for (OrganizationDuty resultOd : orgDutys) {
    	    			_orgDutyIds.add(resultOd.getOrgDutyId());
					}
    	    		this.checkNodeHaveUser(_orgDutyIds);
    	    	}
			}
    	}
	}
    
    /**
     * 新增组织职责
     * @param organizationDuty 要新增的组织职责
     * @return
     * @throws Exception
     */
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertOrgDuty(OrganizationDuty organizationDuty) throws Exception {
		if (organizationDuty != null) {
			organizationDuty.setOrgDutyId(null);	//自增，不需要设置值
			int count = this.insertSelective(organizationDuty);	//开始添加组织职责
			if (count == 1) {	//受影响的行数，判断是否修改成功
				return R.ok().setMsg("组织职责添加成功。");
			} else {	//没有受影响行数表示添加失败
				throw new Exception();
			}
		} else {	//添加一定需要一个组织职责
			throw new Exception();
		}
    }

}
