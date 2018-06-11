package com.zltel.broadcast.eventplan.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.eventplan.bean.CostType;
import com.zltel.broadcast.eventplan.service.CostPlanService;

@RestController
@RequestMapping("/costplan")
public class CostPlanController {

    @Resource
    private CostPlanService costPlanService;

    @GetMapping("/costtype")
    public R listCostType() {
        List<TreeNode<CostType>> ctTree = this.costPlanService.listCostTypeTree(null);

        return R.ok().setData(ctTree);
    }

}
