package com.zltel.broadcast.eventplan.service;

import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.eventplan.bean.CostType;

@Generated(value = "org.junit-tools-1.0.6")
public class CostPlanServiceTest extends BroadcastApplicationTests {

	@Resource
	private CostPlanService costPlanService;

	private CostPlanService createTestSubject() {
		return this.costPlanService;
	}

	@Test
	public void testListCostTypeTree() throws Exception {
		CostPlanService testSubject;
		CostType object = null;
		List<TreeNode<CostType>> result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.listCostTypeTree(object);
		logout.info("CostType Tree Data:{}",JSON.toJSONString(result));
	}

	@Test
	public void testListCostType() throws Exception {
		CostPlanService testSubject;
		CostType record = null;
		List<CostType> result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.listCostType(record);
		logout.info("CostType Data:{}",JSON.toJSONString(result));
	}
}