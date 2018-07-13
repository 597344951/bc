package com.zltel.broadcast.resource.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.resource.bean.ResourceMaterial;
import com.zltel.broadcast.resource.service.ResourceMaterialService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = {"/resource"})
public class ResourceMaterialController extends BaseController {
    public static final Logger logout = LoggerFactory.getLogger(ResourceMaterialController.class);
    @Resource
    private ResourceMaterialService materialService;

    @ApiOperation(value = "查询资源内容")
    @PostMapping(value = "/Material/{pageIndex}-{limit}")
    public R list(@PathVariable("pageIndex") int pageIndex, @PathVariable("limit") int limit,
            @RequestBody ResourceMaterial rm) {
        SysUser user = this.getSysUser();
        ResourceMaterial nm = new ResourceMaterial(user);
        nm.setKeyword(rm.getKeyword());
        nm.setAlbumId(rm.getAlbumId());
        nm.setType(rm.getType());
        nm.setContentType(rm.getContentType());
        nm.setVerify(rm.getVerify());

        Pager pager = new Pager(pageIndex, limit);
        List<ResourceMaterial> data = this.materialService.query(nm, pager);

        return R.ok().setData(data).setPager(pager);
    }

    @ApiOperation(value = "批量导入资源")
    @PostMapping(value = "/Materials")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_MANAGE_LOG, value = "导入资源", template = "批量导入资源:${rms.toString()}")
    public R saveList(@RequestBody List<ResourceMaterial> rms) {
        for (ResourceMaterial rm : rms) {
            ValidatorUtils.validateEntity(rm);
            SysUser user = this.getSysUser();
            rm.setOrgId(user.getOrgId());
            rm.setUserId(user.getUserId());
            rm.setAddDate(new Date());
        }

        this.materialService.inserts(rms);
        rms.stream().forEach(this.materialService::loadOtherInfo);
        return R.ok();
    }

    @ApiOperation(value = "新建资源内容")
    @PostMapping(value = "/Material")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_MANAGE_LOG, value = "新增资源", template = "新增资源:${rm.toString()}")
    public R save(@RequestBody ResourceMaterial rm) {
        ValidatorUtils.validateEntity(rm);
        SysUser user = this.getSysUser();
        rm.setOrgId(user.getOrgId());
        rm.setUserId(user.getUserId());
        rm.setAddDate(new Date());
        this.materialService.insert(rm);
        this.materialService.loadOtherInfo(rm);
        return R.ok();
    }

    @ApiOperation(value = "更新信息")
    @PutMapping(value = "/Material")
    public R update(@RequestBody ResourceMaterial rm) {
        ValidatorUtils.validateEntity(rm);
        SysUser user = this.getSysUser();
        rm.setOrgId(user.getOrgId());
        rm.setUserId(user.getUserId());

        this.materialService.updateByPrimaryKeySelective(rm);
        return R.ok();
    }

    @ApiOperation(value = "获取指定信息")
    @GetMapping("/Material/{materialId}")
    public R get(@PathVariable("materialId") Integer materialId) {
        if (null == materialId) throw new RRException("输入分类的id");
        ResourceMaterial m = this.materialService.selectByPrimaryKey(materialId);
        return R.ok().setData(m);
    }

    @ApiOperation(value = "删除分类信息")
    @DeleteMapping("/Material/{materialId}")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_MANAGE_LOG, value = "删除资源分类", template = "删除分类id:${materialId}")
    public R delete(@PathVariable("materialId") Integer materialId) {
        if (null == materialId) throw new RRException("输入删除分类的id");
        ResourceMaterial m = new ResourceMaterial(this.getSysUser());
        m.setMaterialId(materialId);

        int rc = this.materialService.delete(m);
        if (rc > 0) {
            return R.ok();
        } else {
            return R.error("删除失败!无法删除内置节点!");
        }
    }
}
