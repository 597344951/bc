package com.zltel.broadcast.lesson.controller;

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
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.util.TreeNodeCreateUtil;
import com.zltel.broadcast.lesson.bean.LessonCategory;
import com.zltel.broadcast.lesson.service.LessonCategoryService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/lesson/category")
public class LessonCategoryController extends BaseController {

    @Resource
    private LessonCategoryService categoryService;

    @ApiOperation("增加分类")
    @PostMapping("/category")
    public R save(@RequestBody LessonCategory record) {
        this.initRelatedInfo(record);
        this.categoryService.insertSelective(record);
        return R.ok();
    }



    @ApiOperation("更新分类信息")
    @PutMapping("/category")
    public R update(@RequestBody LessonCategory record) {
        if (record == null || record.getCategoryId() == null) throw RRException.makeThrow("更新数据不能为空");
        this.initRelatedInfo(record);
        this.categoryService.updateByPrimaryKeySelective(record);
        return R.ok();
    }

    @ApiOperation("删除分类信息")
    @DeleteMapping("/category/{categoryId}")
    public R delete(@PathVariable("categoryId") Integer categoryId) {
        LessonCategory lc = this.categoryService.selectByPrimaryKey(categoryId);
        SysUser user = getSysUser();
        if (null != lc && lc.getOrgId() == user.getOrgId() && lc.getUserId() == user.getUserId()) {
            this.categoryService.deleteByPrimaryKey(categoryId);
        }
        return R.ok();
    }

    @ApiOperation("获取分类信息")
    @GetMapping("/category/{categoryId}")
    public R get(@PathVariable("categoryId") Integer categoryId) {
        LessonCategory lc = this.categoryService.selectByPrimaryKey(categoryId);
        SysUser user = getSysUser();
        if (null != lc && lc.getOrgId() == user.getOrgId() && lc.getUserId() == user.getUserId()) {
            return R.ok().setData(user);
        }
        return R.ok();
    }

    @GetMapping("/category/tree")
    @ApiOperation("获取分类树信息")
    public R tree() {
        LessonCategory record = new LessonCategory();
        this.initRelatedInfo(record);
        List<LessonCategory> list = this.categoryService.query(record);
        List<LessonCategory> tree = TreeNodeCreateUtil.toTree(list, LessonCategory::getCategoryId,
                LessonCategory::getParent, LessonCategory::setChildren);
        return R.ok().setData(tree).set("list", list);
    }

    private void initRelatedInfo(LessonCategory record) {
        SysUser user = getSysUser();
        record.setOrgId(user.getOrgId());
        record.setUserId(user.getUserId());
    }
}
