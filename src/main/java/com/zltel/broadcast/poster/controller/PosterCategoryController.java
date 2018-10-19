package com.zltel.broadcast.poster.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.poster.bean.PosterCategory;
import com.zltel.broadcast.poster.service.PosterInfoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/poster")
public class PosterCategoryController extends BaseController {
    @Resource
    private PosterInfoService posterInfoService;

    @ApiOperation("获取海报分类信息")
    @GetMapping("/category/{type}")
    public R get(@PathVariable("type") Integer type) {
        List<PosterCategory> categorys = this.posterInfoService.queryCategorys(type);
        return R.ok().setData(categorys);
    }
}
