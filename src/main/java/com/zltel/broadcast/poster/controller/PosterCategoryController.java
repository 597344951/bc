package com.zltel.broadcast.poster.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.common.util.TreeNodeCreateUtil;
import com.zltel.broadcast.poster.bean.PosterCategory;
import com.zltel.broadcast.poster.service.PosterCategoryService;
import com.zltel.broadcast.poster.service.PosterInfoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/poster")
public class PosterCategoryController extends BaseController {
    @Resource
    private PosterInfoService posterInfoService;

    @Resource
    private PosterCategoryService posterCategoryService;

    @ApiOperation("获取海报分类信息")
    @GetMapping("/category/{type}")
    public R get(@PathVariable("type") Integer type) {
        List<PosterCategory> categorys = this.posterInfoService.queryCategorys(type);
        List<TreeNode<PosterCategory>> categoryTree =
                TreeNodeCreateUtil.toTree(categorys, PosterCategory::getCategoryId, PosterCategory::getParent);
        return R.ok().setData(categorys).set("treeData", categoryTree);
    }

    @ApiOperation("搜索海报分类信息")
    @PostMapping("/category/search")
    public R search(@RequestBody PosterCategory record) {
        List<PosterCategory> categorys = this.posterCategoryService.query(record);
        List<TreeNode<PosterCategory>> categoryTree =
                TreeNodeCreateUtil.toTree(categorys, PosterCategory::getCategoryId, PosterCategory::getParent);
        return R.ok().setData(categorys).set("treeData", categoryTree);
    }

    @ApiOperation("删除分类信息")
    @DeleteMapping("/category/{templateId}")
    public R delete(@PathVariable("templateId") Integer categoryId) {
        this.posterCategoryService.deleteByPrimaryKey(categoryId);
        return R.ok();
    }

    @ApiOperation("新增海报分类")
    @PostMapping("/category")
    public R save(@RequestBody PosterCategory record) {
        this.posterCategoryService.insertSelective(record);
        return R.ok();
    }

    @ApiOperation("更新海报分类信息")
    @PutMapping("/category")
    public R update(@RequestBody PosterCategory record) {
        this.posterCategoryService.updateByPrimaryKeySelective(record);
        return R.ok();
    }
}
