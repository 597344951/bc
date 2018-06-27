package com.zltel.broadcast.template.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.common.util.TreeNodeCreateUtil;
import com.zltel.broadcast.incision.sola.bean.Category;
import com.zltel.broadcast.incision.sola.bean.ProgramTemp;
import com.zltel.broadcast.incision.sola.service.SolaProgramService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = {"/pt"})
public class ProgramTemplateController extends BaseController {
    @Resource
    private SolaProgramService solaService;

    @GetMapping("/category")
    @ApiOperation(value = "获取节目模版目录")
    public R getProgramCategory() {
        List<Category> cats = this.solaService.getProgramCategoryMouldList();
        List<TreeNode<Category>> tree = TreeNodeCreateUtil.toTree(Category.class, cats, "pkId", "parentId");
        R r = R.ok().setData(tree);
        r.put("cats", cats);
        return r.setData(tree);
    }

    @GetMapping("/{categoryId}/ProgramTemplate/{pageIndex}-{limit}")
    @ApiOperation(value = "获取节目模版")
    public R getProgramTemplate(@PathVariable("categoryId") int categoryId, @PathVariable("pageIndex") int pageIndex,
            @PathVariable("limit") int limit) {
        Pager prb = new Pager(pageIndex, limit);
        List<ProgramTemp> data = this.solaService.getProgramList(categoryId, prb);
        return R.ok().setPager(prb).setData(data);
    }
}
