package com.zltel.broadcast.resource.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.common.util.TreeNodeCreateUtil;
import com.zltel.broadcast.resource.bean.MaterialAlbum;
import com.zltel.broadcast.resource.bean.ResourceMaterial;
import com.zltel.broadcast.resource.service.MaterialAlbumService;
import com.zltel.broadcast.resource.service.ResourceMaterialService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = {"/resource/Material/verify/"})
public class ResourceMaterialVerifyController extends BaseController {

    @Resource
    private ResourceMaterialService service;
    @Resource
    private MaterialAlbumService materialAlbumService;

    @ApiOperation(value = "审核资源内容")
    @PostMapping("/verify/{verify}")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_VERIFY_LOG, value = "审核资源", template = "审核素材:${rm.toString()} , 通过状态:${verify}")
    public R verify(@PathVariable("verify") boolean verify,
            @RequestBody ResourceMaterial rm) {
        rm.setVerify(verify);
        this.service.verify(rm);
        return R.ok();
    }
    @ApiOperation(value = "审核资源内容")
    @PostMapping("/verifys/{verify}")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_VERIFY_LOG, value = "审核资源", template = "审核素材:${rms.toString()} , 通过状态:${verify}")
    public R verifys(@PathVariable("verify") boolean verify,
            @RequestBody List<ResourceMaterial> rms) {

        this.service.verify(rms,verify);
        return R.ok();
    }

    @ApiOperation(value = "查询未审核资源")
    @PostMapping("/{pageIndex}-{limit}")
    public R query(@PathVariable("pageIndex") int pageIndex, @PathVariable("limit") int limit,
            @RequestBody ResourceMaterial rm) {
        SysUser user = this.getSysUser();
        rm.setOrgId(user.getOrgId());
        Pager pager = new Pager(pageIndex, limit);
        List<ResourceMaterial> data = this.service.query(rm, pager);

        return R.ok().setData(data).setPager(pager);
    }

    @ApiOperation(value = "查询未审核数据目录")
    @PostMapping(value = "/AlbumTree")
    public R listTypeTree(String keyword,Boolean verify,Boolean noVerify) {
        SysUser user = this.getSysUser();
        MaterialAlbum ma = new MaterialAlbum(user);
        ma.setUid(null);
        ma.setKeyword(keyword);
        ma.setVerify(verify);
        ma.setNoVerify(noVerify);

        List<MaterialAlbum> mas = this.materialAlbumService.listMaterialAlbum(ma);
        List<TreeNode<MaterialAlbum>> tree =
                TreeNodeCreateUtil.toTree(mas, MaterialAlbum::getAlbumId, MaterialAlbum::getParent);

        return R.ok().setData(tree).set("list", mas);
    }


}
