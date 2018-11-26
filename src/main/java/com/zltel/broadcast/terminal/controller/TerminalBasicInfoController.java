package com.zltel.broadcast.terminal.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;
import com.zltel.broadcast.terminal.service.TerminalBasicInfoService;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = {"/terminal/basic"})
@RestController
public class TerminalBasicInfoController extends BaseController {
    @Autowired
    private TerminalBasicInfoService tbs;

    @RequestMapping(value = "/queryInfo/{pageNum}-{pageSize}", method = RequestMethod.POST)
    @RequiresPermissions(value = {"terminal:basic:query"})
    @ApiOperation(value = "查询终端基础信息")
    public R queryBaiscInfo(@RequestBody TerminalBasicInfo tbi, @PathVariable("pageNum") int pageNum,
            @PathVariable("pageSize") int pageSize) {
        R r = tbs.queryBasicInfo(tbi, pageNum, pageSize);
        Map<String, Integer> count = this.tbs.countOnlineTerminal();
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

