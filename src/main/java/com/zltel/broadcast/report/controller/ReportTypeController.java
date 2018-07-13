package com.zltel.broadcast.report.controller;

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
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.common.util.TreeNodeCreateUtil;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.report.bean.ReportType;
import com.zltel.broadcast.report.service.ReportTypeService;
import com.zltel.broadcast.resource.bean.MaterialAlbum;
import com.zltel.broadcast.template.bean.TemplateType;
import com.zltel.broadcast.template.bean.TemplateTypeTreeNode;
import com.zltel.broadcast.template.service.TemplateTypeService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/report")
public class ReportTypeController extends BaseController {
    public static final Logger logout = LoggerFactory.getLogger(ReportTypeController.class);
    
    @Resource
    private ReportTypeService reportTypeService;
    
    @Resource
    private TemplateTypeService templateTypeService;

    @ApiOperation(value = "查询分类树形结构信息", notes = "根据用户所在组织查询其所具有的模板分类信息")
    @GetMapping(value = "/type")
    public R listTypeTree(String keyword) {
        ReportType rp = new ReportType();
        rp.setOrgid(this.getSysUser().getOrgId());
        rp.setKeyword(keyword);
        List<ReportType> mas = this.reportTypeService.queryReportTemplateCount(rp);
        List<TreeNode<ReportType>> tree = TreeNodeCreateUtil.toTree(mas, ReportType::getTypeId, ReportType::getParent);
        return R.ok().setData(tree).set("list", mas);
    }

    @ApiOperation(value = "新建专辑信息")
    @PostMapping(value = "/type")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_MANAGE_LOG, value = "新增模版专辑分类", template = "新增资源分类:${ma.name}")
    public R save(@RequestBody ReportType ma) {
        ValidatorUtils.validateEntity(ma);
        SysUser user = this.getSysUser();
        ma.setBuiltin(false);
        ma.setOrgid(user.getOrgId());
        this.reportTypeService.insert(ma);
        return R.ok();
    }

    @ApiOperation(value = "更新分类信息")
    @PutMapping(value = "/type")
    public R update(@RequestBody ReportType ma) {
        ValidatorUtils.validateEntity(ma);
        SysUser user = this.getSysUser();
        ma.setBuiltin(false);
        ma.setOrgid(user.getOrgId());

        int rc = this.reportTypeService.updateByPrimaryKeySelective(ma);
        if (rc > 0) {
            return R.ok();
        } else {
            return R.error("无法更新内置节点!");
        }
    }

    @ApiOperation(value = "获取指定分类信息")
    @GetMapping("/type/{albumId}")
    public R get(@PathVariable("typeId") Integer typeId) {
        ReportType ma = this.reportTypeService.selectByPrimaryKey(typeId);
        return R.ok().setData(ma);
    }

    @ApiOperation(value = "删除分类信息")
    @DeleteMapping("/type/{typeId}")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_MANAGE_LOG, value = "删除资源分类", template = "删除分类id:${tpTypeId}")
    public R delete(@PathVariable("typeId") Integer typeId) {
        if (null == typeId) throw new RRException("输入删除分类的id");
        SysUser user = this.getSysUser();
        ReportType ma = new ReportType();
        ma.setBuiltin(false);
        ma.setOrgid(user.getOrgId());
        ma.setTypeId(typeId);

        int rc = this.reportTypeService.delete(ma);
        if (rc > 0) {
            return R.ok();
        } else {
            return R.error("删除失败!无法删除内置节点!");
        }
    }

}
