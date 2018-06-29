package com.zltel.broadcast.template.controller;

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
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.template.bean.TemplateType;
import com.zltel.broadcast.template.bean.TemplateTypeTreeNode;
import com.zltel.broadcast.template.service.TemplateTypeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/tpt")
public class TemplateTypeController extends BaseController {
    public static final Logger logout = LoggerFactory.getLogger(TemplateContentController.class);
    @Resource
    private TemplateTypeService templateTypeService;

    @ApiOperation(value = "查询分类树形结构信息", notes = "根据用户所在组织查询其所具有的模板分类信息")
    @GetMapping(value = "/listTypeTree")
    public R listTypeTree(String keyword) {
        TemplateType tp = new TemplateType();
        tp.setOrgid(this.getSysUser().getOrgId());
        tp.setKeyword(keyword);
        List<TemplateTypeTreeNode> result = this.templateTypeService.getTypeTree(tp);
        return R.ok().setData(result);
    }

    @ApiOperation(value = "新建分类信息", notes = "新建模板分类信息")
    @PostMapping(value = "/tptype")
    @LogPoint(type=LogPoint.TYPE_RESOURCE_MANAGE_LOG,value="新增模版分类",template="新增模版分类:${tpt.name}")
    public R save(@RequestBody TemplateType tpt) {
        ValidatorUtils.validateEntity(tpt);
        tpt.setBuiltin(false);
        tpt.setOrgid(this.getSysUser().getOrgId());
        
        this.templateTypeService.insert(tpt);
        return R.ok();
    }

    @ApiOperation(value = "更新分类信息", notes = "更新模板分类信息")
    @PutMapping(value = "/tptype")
    public R update(@RequestBody TemplateType tpt) {
        ValidatorUtils.validateEntity(tpt);
        tpt.setBuiltin(null);
        tpt.setOrgid(null);
        this.templateTypeService.updateByPrimaryKeySelective(tpt);
        return R.ok();
    }

    @ApiOperation(value = "获取指定模板分类信息")
    @GetMapping("/tptype/{tpTypeId}")
    public R get(@PathVariable("tpTypeId") Integer tpTypeId) {
        TemplateType tt = this.templateTypeService.selectByPrimaryKey(tpTypeId);
        return R.ok().setData(tt);
    }

    @ApiOperation(value = "删除分类信息")
    @DeleteMapping("/tptype/{tpTypeId}")
    @LogPoint(type=LogPoint.TYPE_RESOURCE_MANAGE_LOG,value="删除模版分类",template="删除模版分类id:${tpTypeId}")
    public R delete(@PathVariable("tpTypeId") Integer tpTypeId) {
        if (null == tpTypeId) throw new RRException("输入删除分类的id");
        TemplateType tc = new TemplateType();
        tc.setTpTypeId(tpTypeId);
        tc.setBuiltin(false);
        tc.setOrgid(this.getSysUser().getOrgId());
        int rc = this.templateTypeService.delete(tc);
        if (rc > 0) {
            return R.ok();
        } else {
            return R.error("删除失败!无法删除内置节点!");
        }
    }

}
