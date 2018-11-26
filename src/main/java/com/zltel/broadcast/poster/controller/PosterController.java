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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.pager.PagerHelper;
import com.zltel.broadcast.poster.bean.PosterInfo;
import com.zltel.broadcast.poster.bean.PosterReplaceMetaData;
import com.zltel.broadcast.poster.bean.PosterSize;
import com.zltel.broadcast.poster.service.PosterCoverService;
import com.zltel.broadcast.poster.service.PosterInfoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/poster")
public class PosterController extends BaseController {
    @Resource
    private PosterInfoService posterInfoService;

    @Resource
    private PosterCoverService posterCoverService;

    @ApiOperation("查询海报模版信息")
    @PostMapping("/posterinfo/search/{pageNum}-{pageSize}")
    public R search(@RequestBody PosterInfo posterInfo, @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize) {
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<PosterInfo> datas = this.posterInfoService.query(posterInfo);
        Pager pager = PagerHelper.toPager(page);
        return R.ok().setData(datas).setPager(pager);
    }

    @ApiOperation("获取海报模版信息")
    @GetMapping("/posterinfo/{templateId}")
    public R get(@PathVariable("templateId") Integer templateId) {
        PosterInfo posterInfo = this.posterInfoService.selectAllByPrimaryKey(templateId);
        return R.ok().setData(posterInfo);
    }

    @ApiOperation("获取海报模版信息")
    @DeleteMapping("/posterinfo/{templateId}")
    public R delete(@PathVariable("templateId") Integer templateId) {
        this.posterInfoService.deleteByPrimaryKey(templateId);
        return R.ok();
    }

    @ApiOperation("新增海报")
    @PostMapping("/posterinfo")
    public R save(@RequestBody PosterInfo record) {
        int templateId = this.posterInfoService.insertSelective(record);
        return R.ok().set("templateId", templateId);
    }

    @ApiOperation("更新海报")
    @PutMapping("/posterinfo")
    public R update(@RequestBody PosterInfo record) {
        this.posterInfoService.updateByPrimaryKeySelective(record);
        return R.ok();
    }


    @ApiOperation("查询海报预设规格")
    @PostMapping("/postersize/search")
    public R queryPostrSize(@RequestBody PosterSize record) {
        List<PosterSize> pss = this.posterInfoService.queryPosterSize(record);
        return R.ok().setData(pss);
    }

    @ApiOperation("从模板创建新海报")
    @GetMapping("/newPosterFromTemplateId/{templateId}")
    public R newPosterFromTemplateId(@PathVariable("templateId") Integer templateId) {
        PosterInfo posterInfo = this.posterInfoService.newPosterFromTemplateId(templateId);
        return R.ok().setData(posterInfo);
    }

    @ApiOperation("查询海报源数据")
    @PostMapping("/posterinfo/searchMetaData/{pageNum}-{pageSize}")
    public R searchMetaData(@RequestBody PosterReplaceMetaData prm, @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize) {
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<PosterInfo> datas = this.posterInfoService.searchMetaData(prm.getSearch());
        Pager pager = PagerHelper.toPager(page);
        return R.ok().setData(datas).setPager(pager);
    }

    @ApiOperation("替换海报源数据")
    @PostMapping("/posterinfo/replaceMetaData")
    public R replaceMetaData(@RequestBody PosterReplaceMetaData prm) {
        this.posterCoverService.replaceTarget(prm.getTemplateIds(), prm.getSearch(), prm.getRep());
        return R.ok();
    }


}
