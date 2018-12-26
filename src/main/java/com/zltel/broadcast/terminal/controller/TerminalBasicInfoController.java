package com.zltel.broadcast.terminal.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.zltel.broadcast.common.util.AdminRoleUtil;
import com.zltel.broadcast.common.util.TreeNodeCreateUtil;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;
import com.zltel.broadcast.terminal.service.TerminalBasicInfoService;
import com.zltel.broadcast.um.bean.OrganizationInfo;
import com.zltel.broadcast.um.bean.OrganizationInformation;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.service.OrganizationInformationService;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = {"/terminal/basic"})
@RestController
public class TerminalBasicInfoController extends BaseController {
    @Autowired
    private TerminalBasicInfoService tbs;
    
    @Resource
    private OrganizationInformationService orgService;

    @PostMapping(value = "/queryInfo/{pageNum}-{pageSize}")
    @RequiresPermissions(value = {"terminal:basic:query"})
    @ApiOperation(value = "查询终端基础信息")
    public R queryBaiscInfo(@RequestBody TerminalBasicInfo tbi, @PathVariable("pageNum") int pageNum,
            @PathVariable("pageSize") int pageSize) {
        tbi.setOrgId(null);
        // 增加只查询自身组织的设备
        SysUser user = this.getSysUser();
        if(AdminRoleUtil.isPlantAdmin()) {
            
            //平台管理员查询所有
        }else {
            // 查询自身组织所具有的 终端
            List<OrganizationInformation> orgs = orgService.queryOrgInfosSelect(null);
            OrganizationInformation target = new OrganizationInformation();
            target.setOrgInfoId(user.getOrgId());
            List<OrganizationInformation> chi = TreeNodeCreateUtil.getAllChildrenList(target, orgs, OrganizationInformation::getOrgInfoId, OrganizationInformation::getOrgInfoParentId);
            if(chi.isEmpty())chi.add(target);
            List<Integer> orgIds = chi.stream().map(OrganizationInformation::getOrgInfoId).collect(Collectors.toList());
            tbi.setOrgIds(orgIds);
        }
        R r = tbs.queryBasicInfo(tbi, pageNum, pageSize);
        Map<String, Integer> count = this.tbs.countOnlineTerminal(tbi);
        r.put("online", count);
        return r;
    }

    @ApiOperation(value = "查询地图信息")
    @GetMapping("/addup")
    public R queryMapInfo() {
        try {
            return tbs.queryMapInfo();
        } catch (Exception e) {
            logout.error(e.getMessage(), e);
            return R.error().setMsg("查询地图信息失败");
        }

    }

    @ApiOperation(value = "删除终端基础信息")
    @DeleteMapping("/addup/{oid}")
    public R delete(@PathVariable("oid") Integer oid) {
        if (null == oid) throw new RRException("输入删除信息的oid");
        int rc = this.tbs.deleteByPrimaryKey(oid);
        if (rc > 0) {
            return R.ok();
        } else {
            return R.error("删除失败");
        }
    }

    @ApiOperation(value = "同步终端基础信息")
    @PostMapping(value = "/addup")
    public R syn() {
        int c = this.tbs.synchronizTerminalInfo();
        return R.ok().setMsg("同步终端数:" + c);
    }

    @ApiOperation(value = "更新终端基础信息")
    @PutMapping(value = "/addup")
    public R update(@RequestBody TerminalBasicInfo tbi) {
        ValidatorUtils.validateEntity(tbi);
        this.tbs.updateByPrimaryKeySelective(tbi);
        return R.ok();
    }

    @ApiOperation(value = "统计终端基础信息")
    @PutMapping("/echarts/{string}")
    public R echarts(@PathVariable("string") String string) {
        try {
            return tbs.echarts(string);
        } catch (Exception e) {
            logout.error(e.getMessage());
            return R.error().setMsg("统计终端基础信息失败");
        }
    }

}

