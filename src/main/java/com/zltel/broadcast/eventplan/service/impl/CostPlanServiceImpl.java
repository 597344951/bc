package com.zltel.broadcast.eventplan.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.common.util.TreeNodeCreateUtil;
import com.zltel.broadcast.eventplan.bean.CostPlan;
import com.zltel.broadcast.eventplan.bean.CostType;
import com.zltel.broadcast.eventplan.dao.CostPlanMapper;
import com.zltel.broadcast.eventplan.dao.CostTypeMapper;
import com.zltel.broadcast.eventplan.service.CostPlanService;

@Service
public class CostPlanServiceImpl extends BaseDaoImpl<CostPlan> implements CostPlanService {

    public static final Logger logout = LoggerFactory.getLogger(CostPlanServiceImpl.class);

    @Resource
    private CostPlanMapper costPlanMapper;

    @Resource
    private CostTypeMapper costTypeMapper;

    @Override
    public BaseDao<CostPlan> getInstince() {
        return this.costPlanMapper;
    }

    @Override
    public List<CostType> listCostType(CostType record) {
        return this.costTypeMapper.query(record, RowBounds.DEFAULT);
    }

    @Override
    public List<TreeNode<CostType>> listCostTypeTree(CostType record) {
        List<CostType> cts = this.listCostType(record);
        return TreeNodeCreateUtil.toTree(cts, CostType::getCostType, CostType::getParent);
    }

    @Override
    public void updateCostPlan(List<CostPlan> plans) {
        if (null == plans || plans.isEmpty()) return;
        this.deleteCostPlanByEventId(plans.get(0).getEventPlanId());
        this.saveCostPlans(plans);
    }

    @Override
    public void saveCostPlans(List<CostPlan> plans) {
        plans.forEach(this::insertSelective);
    }

    @Override
    public void deleteCostPlanByEventId(Integer eventPlanId) {
        CostPlan costPlan = new CostPlan();
        costPlan.setEventPlanId(eventPlanId);
        this.costPlanMapper.delete(costPlan);
    }

    @Override
    public List<CostPlan> selectByEventPlanId(Integer eventPlanId) {
        return this.costPlanMapper.selectByEventPlanId(eventPlanId);
    }


}
