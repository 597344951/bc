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

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.common.util.TreeNodeCreateUtil;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.report.bean.ReportSubmitType;
import com.zltel.broadcast.report.service.ReportSubmitTypeService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = {"/report-submit"})
public class ReportSubmitTypeController extends BaseController {
    public static final Logger logout = LoggerFactory.getLogger(ReportSubmitTypeController.class);
   
    @Resource
    private ReportSubmitTypeService typeService;

    @ApiOperation(value = "查询目录信息成树")
    @GetMapping(value = "/type/tree")
    public R getSubmitTree() {
        SysUser user = this.getSysUser();
        ReportSubmitType record = new ReportSubmitType();
        record.setOrgid(user.getOrgId());
        List<ReportSubmitType> datas = this.typeService.query(record);
        List<TreeNode<ReportSubmitType>> tree =
                TreeNodeCreateUtil.toTree(datas, ReportSubmitType::getTypeId, ReportSubmitType::getParent);
        return R.ok().setData(tree).set("list", datas);
    }

    @ApiOperation(value = "新建报告类别")
    @PostMapping(value = "/type")
    public R save(@RequestBody ReportSubmitType rt) {
        ValidatorUtils.validateEntity(rt);
        SysUser user = this.getSysUser();
        rt.setOrgid(user.getOrgId());
        rt.setTime(new Date());
        this.typeService.insert(rt);
        return R.ok();
    }

    @ApiOperation(value = "更新信息")
    @PutMapping(value = "/type")
    public R update(@RequestBody ReportSubmitType rt) {
        ValidatorUtils.validateEntity(rt);
        SysUser user = this.getSysUser();
        rt.setOrgid(user.getOrgId());

        this.typeService.updateByPrimaryKeySelective(rt);
        return R.ok();
    }

    @ApiOperation(value = "获取类别")
    @GetMapping("/type/{typeId}")
    public R get(@PathVariable("typeId") Integer typeId) {
        if (null == typeId) throw new RRException("输入目录id");
        ReportSubmitType m = this.typeService.selectByPrimaryKey(typeId);
        return R.ok().setData(m);
    }

    @ApiOperation(value = "删除分类信息")
    @DeleteMapping("/type/{typeId}")
    public R delete(@PathVariable("reportId") Integer typeId) {
        if (null == typeId) throw new RRException("输入删除分类的id");
        SysUser user = this.getSysUser();
        ReportSubmitType rt = new ReportSubmitType();
        rt.setOrgid(user.getOrgId());
        rt.setTypeId(typeId);

        int rc = this.typeService.deleteSubmitTypeWithAllData(rt);
        if (rc > 0) {
            return R.ok();
        } else {
            return R.error("删除失败!无法删除内置节点!");
        }
    }
}
