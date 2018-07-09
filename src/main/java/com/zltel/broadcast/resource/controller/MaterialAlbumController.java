package com.zltel.broadcast.resource.controller;

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
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.common.util.TreeNodeCreateUtil;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.resource.bean.MaterialAlbum;
import com.zltel.broadcast.resource.service.MaterialAlbumService;
import com.zltel.broadcast.template.bean.TemplateType;
import com.zltel.broadcast.template.bean.TemplateTypeTreeNode;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = {"/MaterialAlbum"})
public class MaterialAlbumController extends BaseController {
    public static final Logger logout = LoggerFactory.getLogger(MaterialAlbumController.class);
    @Resource
    private MaterialAlbumService materialAlbumService;


    @ApiOperation(value = "查询用户专辑树")
    @GetMapping(value = "/Album")
    public R listTypeTree(String keyword) {
        SysUser user = this.getSysUser();
        MaterialAlbum ma = new MaterialAlbum(user);
        ma.setKeyword(keyword);

        List<MaterialAlbum> mas = this.materialAlbumService.listMaterialAlbum(ma);
        List<TreeNode<MaterialAlbum>> tree = TreeNodeCreateUtil.toTree(MaterialAlbum.class, mas, "albumId", "parent");
 
        return R.ok().setData(tree).set("list", mas);
    }

    @ApiOperation(value = "新建专辑信息")
    @PostMapping(value = "/Album")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_MANAGE_LOG, value = "新增资源专辑分类", template = "新增资源分类:${ma.name}")
    public R save(@RequestBody MaterialAlbum ma) {
        ValidatorUtils.validateEntity(ma);
        SysUser user = this.getSysUser();
        ma.setBuiltin(false);
        ma.setOrgid(user.getOrgId());
        ma.setUid(user.getUserId());

        this.materialAlbumService.insert(ma);
        return R.ok();
    }

    @ApiOperation(value = "更新分类信息")
    @PutMapping(value = "/Album")
    public R update(@RequestBody MaterialAlbum ma) {
        ValidatorUtils.validateEntity(ma);
        SysUser user = this.getSysUser();
        ma.setBuiltin(false);
        ma.setOrgid(user.getOrgId());
        ma.setUid(user.getUserId());

        int rc = this.materialAlbumService.updateByPrimaryKeySelective(ma);
        if (rc > 0) {
            return R.ok();
        } else {
            return R.error("无法更新内置节点!");
        }
    }

    @ApiOperation(value = "获取指定分类信息")
    @GetMapping("/Album/{albumId}")
    public R get(@PathVariable("albumId") Integer albumId) {
        MaterialAlbum ma = this.materialAlbumService.selectByPrimaryKey(albumId);
        return R.ok().setData(ma);
    }

    @ApiOperation(value = "删除分类信息")
    @DeleteMapping("/Album/{albumId}")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_MANAGE_LOG, value = "删除资源分类", template = "删除分类id:${tpTypeId}")
    public R delete(@PathVariable("albumId") Integer albumId) {
        if (null == albumId) throw new RRException("输入删除分类的id");
        MaterialAlbum ma = new MaterialAlbum(this.getSysUser());
        ma.setBuiltin(false);
        ma.setAlbumId(albumId);

        int rc = this.materialAlbumService.delete(ma);
        if (rc > 0) {
            return R.ok();
        } else {
            return R.error("删除失败!无法删除内置节点!");
        }
    }

}
