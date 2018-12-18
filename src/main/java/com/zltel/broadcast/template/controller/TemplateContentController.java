package com.zltel.broadcast.template.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.util.AdminRoleUtil;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.template.bean.TemplateContent;
import com.zltel.broadcast.template.service.TemplateContentService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/tp")
public class TemplateContentController extends BaseController {
    public static final Logger logout = LoggerFactory.getLogger(TemplateContentController.class);
    @Resource
    private TemplateContentService templateContentService;

    @ApiOperation(value = "查询分类模板列表", notes = "根据用户所在组织查询其指定模板类别下的模板信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tpTypeId", value = "分类id", required = true, dataType = "Integer", paramType = "query")})
    @RequestMapping(value = "/listByType/{pageIndex}-{limit}", method = {RequestMethod.GET})
    @RequiresPermissions("template:template:query")
    public R listTpByType(TemplateContent tp,String keyword, @PathVariable("pageIndex") int pageIndex,
            @PathVariable("limit") int limit) {
        TemplateContent record = new TemplateContent();
        record.setOrgid(this.getSysUser().getOrgId());
        record.setTpTypeId(tp.getTpTypeId());
        record.setKeyword(keyword);
        Pager prb = new Pager(pageIndex, limit);
        AdminRoleUtil.handleAdminRole(record, item -> item.setUid(null), item -> {
            item.setUid(null);
            item.setOrgid(null);
        });
        
        List<TemplateContent> result = this.templateContentService.queryByType(record,prb);
        return R.ok().setData(result).setPager(prb);
    }
    
    @ApiOperation(value = "查询分类模板列表", notes = "根据用户所在组织查询其指定模板类别下的模板信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tpTypeId", value = "分类id", required = true, dataType = "Integer", paramType = "query")})
    @RequestMapping(value = "/listByType", method = {RequestMethod.GET})
    @RequiresPermissions("template:template:query")
    public R listTpByTypeWithoutPager(TemplateContent tp) {
        Pager prb = Pager.DEFAULT_PAGER;
        return this.listTpByType(tp,null, 1, prb.getLimit());
    }

    @ApiOperation(value = "获取指定模板信息")
    @GetMapping("/template/{tpId}")
    @RequiresPermissions("template:template:query")
    public R get(@PathVariable("tpId") Integer tpId) {
        TemplateContent tc = this.templateContentService.selectByPrimaryKey(tpId);
        return R.ok().setData(tc);
    }

    @ApiOperation(value = "删除模板信息")
    @DeleteMapping("/template/{tpId}")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_MANAGE_LOG, value = "删除模版", template = "删除模版id:${tpId}")
    @RequiresPermissions("template:template:delete")
    public R delete(@PathVariable("tpId") Integer tpId) {
        TemplateContent tc = new TemplateContent();
        tc.setTpId(tpId);
        tc.setOrgid(this.getSysUser().getOrgId());
        int rc = this.templateContentService.delete(tc);
        if (rc > 0) {
            return R.ok();
        } else {
            return R.error("删除失败!");
        }
    }

    @ApiOperation(value = "新增模板信息")
    @PostMapping("/template")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_MANAGE_LOG, value = "新增模版", template = "新增模版:${tc.title}")
    @RequiresPermissions("template:template:save")
    public R save(@RequestBody TemplateContent tc) {
        ValidatorUtils.validateEntity(tc);
        SysUser sysUser = this.getSysUser();
        tc.setUid(sysUser.getUserId());
        tc.setOrgid(sysUser.getOrgId());

        this.templateContentService.insert(tc);
        return R.ok();
    }

    @ApiOperation(value = "更新模板信息")
    @PutMapping("/template")
    @RequiresPermissions("template:template:update")
    public R update(@RequestBody TemplateContent tc) {
        ValidatorUtils.validateEntity(tc);
        tc.setUid(null);
        tc.setOrgid(null);
        this.templateContentService.updateByPrimaryKeySelective(tc);
        return R.ok();
    }

}
