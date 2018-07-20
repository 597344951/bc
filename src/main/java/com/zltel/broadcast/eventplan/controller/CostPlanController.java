package com.zltel.broadcast.eventplan.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.eventplan.bean.CostPlan;
import com.zltel.broadcast.eventplan.bean.CostType;
import com.zltel.broadcast.eventplan.service.CostPlanService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/costplan")
public class CostPlanController {

    @Resource
    private CostPlanService costPlanService;

    @ApiOperation(value = "费用花费类型")
    @GetMapping("/costtype")
    public R listCostType() {
        List<TreeNode<CostType>> ctTree = this.costPlanService.listCostTypeTree(null);
        return R.ok().setData(ctTree);
    }

    @ApiOperation(value = "新增费用计划")
    @PostMapping("/plan")
    public R saves(@RequestBody List<CostPlan> plans) {
        this.costPlanService.saveCostPlans(plans);
        return R.ok();
    }

    @ApiOperation("更新费用计划")
    @PutMapping("/plan")
    public R updates(@RequestBody List<CostPlan> plans) {
        this.costPlanService.updateCostPlan(plans);
        return R.ok();
    }

    @ApiOperation("删除费用计划")
    @DeleteMapping("/plan")
    public R delete(@RequestBody CostPlan plan) {
        this.costPlanService.deleteCostPlanByEventId(plan.getEventPlanId());
        return R.ok();
    }



}
