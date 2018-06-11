package com.zltel.broadcast.terminal.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.terminal.bean.TerminalGatherBean;
import com.zltel.broadcast.terminal.bean.TerminalGroup;
import com.zltel.broadcast.terminal.service.TerminalGroupsService;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value={"/terminal/group"})
@RestController
public class TerminalGroupsController {
    @Autowired
    private TerminalGroupsService tbs;
    @RequestMapping(value="/queryInfo/{pageNum}-{pageSize}", method=RequestMethod.POST)
    @LogPoint("查询终端分组信息")
    @RequiresPermissions(value = {"terminal:group:query"})
    @ApiOperation(value = "查询终端分组信息")
    public R queryBaiscInfo(@RequestBody TerminalGroup tbi,  @PathVariable("pageNum")int pageNum,@PathVariable("pageSize") int pageSize) {
        try {
            return tbs.queryBasicGroup(tbi, pageNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().setMsg("查询终端分组信息失败");
        }
    }
    @RequestMapping(value="/queryInfos", method=RequestMethod.POST)
    @LogPoint("查询终端基础信息")
    @RequiresPermissions(value = {"terminal:group:query"})
    @ApiOperation(value = "查询终端基础信息")
    public R queryBaiscInfos(@RequestBody TerminalGroup record) {
        try {
            return tbs.queryBasicInfo(record);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().setMsg("查询终端基础信息失败");
        }
    }
    @ApiOperation(value = "删除终端分组信息")
    @DeleteMapping("/addup/{oid}")
    public R delete(@PathVariable("oid") Integer oid) {
        if (null == oid) throw new RRException("输入删除分组的oid");
        int rc = this.tbs.deleteByPrimaryKey(oid);
        if (rc > 0) {
            return R.ok();
        } else {
            return R.error("删除失败");
        }
    }
    @ApiOperation(value = "新建终端分组信息")
    @PostMapping(value = "/addup")
    public R save(@RequestBody TerminalGatherBean tgb) {
        
        return this.tbs.insertTgb(tgb);
    }

    @ApiOperation(value = "更新终端分组信息")
    @PutMapping(value = "/addup")
    public R update(@RequestBody TerminalGatherBean tgb) {
        ValidatorUtils.validateEntity(tgb);
        this.tbs.updateByPrimaryKeySelective(tgb);
        return R.ok();
    }
}
