package com.zltel.broadcast.report.controller;

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
import com.zltel.broadcast.report.bean.ReportSubmit;
import com.zltel.broadcast.report.bean.ReportTemplate;
import com.zltel.broadcast.report.service.ReportTemplateService;
import com.zltel.broadcast.report.service.ReportsubmitService;
import com.zltel.broadcast.resource.bean.Material;
import com.zltel.broadcast.resource.bean.ResourceMaterial;
import com.zltel.broadcast.resource.service.ResourceMaterialService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = {"/report"})
public class ReportSubmitController extends BaseController {
    public static final Logger logout = LoggerFactory.getLogger(ReportSubmitController.class);
   
    @Resource
    private ReportsubmitService reportSubmitService;

    @ApiOperation(value = "查询内容")
    @PostMapping(value = "/submit/{pageIndex}-{limit}")
    public R list(@PathVariable("pageIndex") int pageIndex, @PathVariable("limit") int limit,
            @RequestBody ReportSubmit rs) {
        SysUser user = this.getSysUser();
        ReportSubmit nm = new ReportSubmit();
        nm.setOrgid(user.getOrgId());
        nm.setTypeId(rs.getTypeId());
        nm.setKeyword(rs.getKeyword());

        Pager pager = new Pager(pageIndex, limit);
        List<ReportSubmit> data = this.reportSubmitService.query(nm, pager);

        return R.ok().setData(data).setPager(pager);
    }

    @ApiOperation(value = "新建资源内容")
    @PostMapping(value = "/submit")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_MANAGE_LOG, value = "新增资源", template = "新增资源:${rm.title}")
    public R save(@RequestBody ReportSubmit rt) {
        ValidatorUtils.validateEntity(rt);
        SysUser user = this.getSysUser();
        rt.setOrgid(user.getOrgId());
        rt.setUid(user.getUserId());
        rt.setCreatetime(new Date());
        this.reportSubmitService.insert(rt);
        return R.ok();
    }

    @ApiOperation(value = "更新信息")
    @PutMapping(value = "/submit")
    public R update(@RequestBody ReportSubmit rt) {
        ValidatorUtils.validateEntity(rt);
        SysUser user = this.getSysUser();
        rt.setOrgid(user.getOrgId());
        rt.setUid(user.getUserId());

        this.reportSubmitService.updateByPrimaryKeySelective(rt);
        return R.ok();
    }

    @ApiOperation(value = "获取指定信息")
    @GetMapping("/submit/{reportId}")
    public R get(@PathVariable("reportId") Integer reportId) {
        if (null == reportId) throw new RRException("输入报告的id");
        ReportSubmit m = this.reportSubmitService.selectByPrimaryKey(reportId);
        return R.ok().setData(m);
    }

    @ApiOperation(value = "删除分类信息")
    @DeleteMapping("/submit/{reportId}")
    @LogPoint(type = LogPoint.TYPE_RESOURCE_MANAGE_LOG, value = "删除资源分类", template = "删除分类id:${reportId}")
    public R delete(@PathVariable("reportId") Integer reportId) {
        if (null == reportId) throw new RRException("输入删除分类的id");
        SysUser user = this.getSysUser();
        ReportSubmit rt = new ReportSubmit();
        rt.setOrgid(user.getOrgId());
        rt.setReportId(reportId);

        int rc = this.reportSubmitService.delete(rt);
        if (rc > 0) {
            return R.ok();
        } else {
            return R.error("删除失败!无法删除内置节点!");
        }
    }
}
