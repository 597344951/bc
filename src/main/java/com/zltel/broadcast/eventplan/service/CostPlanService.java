package com.zltel.broadcast.eventplan.service;

import java.util.List;

import com.zltel.broadcast.eventplan.bean.CostType;
import com.zltel.broadcast.common.tree.TreeNode;
public interface CostPlanService {
    
    public List<CostType> listCostType(CostType record);

    public List<TreeNode<CostType>> listCostTypeTree(CostType object);

}
