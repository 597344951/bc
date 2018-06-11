package com.zltel.broadcast.eventplan.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.eventplan.bean.CostType;
import com.zltel.broadcast.eventplan.dao.CostPlanMapper;
import com.zltel.broadcast.eventplan.dao.CostTypeMapper;
import com.zltel.broadcast.eventplan.service.CostPlanService;

@Service
public class CostPlanServiceImpl implements CostPlanService {

	public static final Logger logout = LoggerFactory.getLogger(CostPlanServiceImpl.class);

	@Resource
	private CostPlanMapper costPlanMapper;

	@Resource
	private CostTypeMapper costTypeMapper;

	@Override
	public List<CostType> listCostType(CostType record) {
		return this.costTypeMapper.query(record, RowBounds.DEFAULT);
	}

	@Override
	public List<TreeNode<CostType>> listCostTypeTree(CostType record) {
		List<CostType> cts = this.listCostType(record);

		List<TreeNode<CostType>> result = new ArrayList<>();
		// 第一层
		cts.stream().filter(ct -> ct.getParent() == 0).forEach(ct -> {
			TreeNode<CostType> tn = new TreeNode<>();
			tn.setData(ct);
			result.add(tn);
		});
		result.forEach(tn -> handleNextNode(tn, cts));
		return result;
	}

	/** 递归遍历子节点 **/
	private void handleNextNode(TreeNode<CostType> node, List<CostType> datas) {
		// 上一级节点的子节点
		List<TreeNode<CostType>> childs = new ArrayList<>();
		datas.stream().filter(n -> n.getParent() == node.getData().getCostType()).forEach(n -> {
			TreeNode<CostType> tn = new TreeNode<>();
			tn.setData(n);
			childs.add(tn);
		});
		if (childs.isEmpty())
			return;
		node.setChildren(childs);
		childs.forEach(n -> handleNextNode(n, datas));
	}

}
