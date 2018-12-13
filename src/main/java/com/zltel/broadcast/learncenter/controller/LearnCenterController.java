package com.zltel.broadcast.learncenter.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.resource.bean.MaterialAlbum;
import com.zltel.broadcast.resource.bean.ResourceMaterial;
import com.zltel.broadcast.resource.service.MaterialAlbumService;
import com.zltel.broadcast.resource.service.ResourceMaterialService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/learn-center")
public class LearnCenterController extends BaseController {

    @Resource
    private ResourceMaterialService resourceMaterialService;
    @Resource
    private MaterialAlbumService materialAlbumService;

    @ApiOperation("获取学习中心素材分类信息")
    @GetMapping("/category")
    public R getLearnCategorys() {
        MaterialAlbum record = new MaterialAlbum();
        record.setLearnResource(true);
        List<MaterialAlbum> albums = this.materialAlbumService.listMaterialAlbum(record);
        return R.ok().setData(albums);
    }

    @ApiOperation("按分类获取素材信息")
    @PostMapping("/category/{pageIndex}-{limit}")
    public R getResourceByCategory(@PathVariable("pageIndex") int pageIndex, @PathVariable("limit") int limit,
            @RequestBody ResourceMaterial record) {
        Pager pager = new Pager(pageIndex, limit);
        List<ResourceMaterial> rms = this.resourceMaterialService.queryLearnResource(record, pager);
        return R.ok().setData(rms).setPager(pager);
    }

    @GetMapping("/resource/{materialId}")
    public R getResource(@PathVariable("materialId") Integer materialId) {
        ResourceMaterial dt = this.resourceMaterialService.selectByPrimaryKey(materialId);
        return R.ok().setData(dt);
    }

    @GetMapping("/view/{materialId}")
    public void viewResource(@PathVariable("materialId") Integer materialId ,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResourceMaterial dt = this.resourceMaterialService.selectByPrimaryKey(materialId);
        
        req.setAttribute("data",JSON.toJSONString(dt));
        req.getRequestDispatcher("/view/learn-center/view.jsp").forward(req,resp);
    }
}
