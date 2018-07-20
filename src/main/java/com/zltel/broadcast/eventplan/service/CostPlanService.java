package com.zltel.broadcast.eventplan.service;

import java.util.List;

import com.zltel.broadcast.eventplan.bean.CostPlan;
import com.zltel.broadcast.eventplan.bean.CostType;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.tree.TreeNode;

public interface CostPlanService extends BaseDao<CostPlan> {

    public List<CostType> listCostType(CostType record);

    public List<TreeNode<CostType>> listCostTypeTree(CostType object);

    /** 更新费用计划 **/
    public void updateCostPlan(List<CostPlan> plans);

    /** 新增费用计划 **/
    public void saveCostPlans(List<CostPlan> plans);
    /**根据活动计划id 删除费用计划**/
    public void deleteCostPlanByEventId(Integer eventPlanId);
    
    public List<CostPlan> selectByEventPlanId(Integer eventPlanId);


}
