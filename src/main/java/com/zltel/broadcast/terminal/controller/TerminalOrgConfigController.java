package com.zltel.broadcast.terminal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.util.AdminRoleUtil;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;
import com.zltel.broadcast.terminal.service.TerminalBasicInfoService;
import com.zltel.broadcast.terminal.service.TerminalOrgConfigService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/terminal/org-config")
public class TerminalOrgConfigController extends BaseController {

    @Resource
    private TerminalOrgConfigService configService;
    @Resource
    private TerminalBasicInfoService baseInfoService;

    @ApiOperation("查询所有未配置组织的终端信息")
    @PostMapping("/unconfig/{pageNum}-{pageSize}")
    @RequiresPermissions("terminal:orgconfig:list")
    public R queryUnConfigTerminal(@RequestBody TerminalBasicInfo info, @PathVariable("pageNum") int pageNum,
            @PathVariable("pageSize") int pageSize) {
        Pager pager = new Pager(pageNum, pageSize);
        // 非 平台管理员, 但查询组织类型为null, 设置查询未注册终端
        if (!AdminRoleUtil.isPlantAdmin() && info.getOrgId() == null) info.setOrgId(0);
        java.util.List<TerminalBasicInfo> datas = this.configService.queryUnConfigTerminal(info, pager);
        
        TerminalBasicInfo record = new TerminalBasicInfo();
        record.setOrgId(0);
        Map<String, Integer> count = this.baseInfoService.countOnlineTerminal(record);
        return R.ok().setPager(pager).setData(datas).set("online", count);
    }

    @ApiOperation("注册终端到组织下")
    @PostMapping("/config")
    @RequiresPermissions("terminal:orgconfig:config")
    public R config(@RequestBody TerminalBasicInfo info) {
        int r = this.configService.orgConfig(info);
        return R.ok().setData(r);
    }
    
    @ApiOperation("注册终端到组织下")
    @PostMapping("/configs")
    @RequiresPermissions("terminal:orgconfig:config")
    public R configs(@RequestBody java.util.List<TerminalBasicInfo> infos) {
        java.util.List<Integer> rets = new ArrayList<>();
        infos.forEach(info -> {
            int r = this.configService.orgConfig(info);
            rets.add(r);
        });
        
        return R.ok().setData(rets);
    }

    @ApiOperation("检测终端信息")
    @PostMapping("/check")
    public R check(@RequestBody List<TerminalBasicInfo> infos) {
        List<R> rets =  this.configService.checkTerminals(infos);
        return R.ok().setData(rets);
    }
    @ApiOperation("删除指定终端关联配置信息")
    @DeleteMapping("/config")
    @RequiresPermissions("terminal:orgconfig:unconfig")
    public R unConfig(@RequestBody TerminalBasicInfo info) {
        this.configService.unOrgConfig(info);
        return R.ok();
    }

}
