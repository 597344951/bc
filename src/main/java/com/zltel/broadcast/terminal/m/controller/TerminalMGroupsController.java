package com.zltel.broadcast.terminal.m.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.terminal.m.bean.TerminalMGroups;
import com.zltel.broadcast.terminal.m.service.TerminalMGroupsService;

@RestController
@RequestMapping("/terminal/m/group")
public class TerminalMGroupsController extends BaseController {
    @Autowired
    private TerminalMGroupsService terminalMGroupsService;

    @PostMapping("/add")
    public R add(@RequestBody TerminalMGroups terminalMGroups) {
        terminalMGroups.setOrgId(this.getSysUser().getOrgId());
        terminalMGroupsService.addGroup(terminalMGroups);
        return R.ok().setData(terminalMGroups);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody List<Integer> ids) {
        terminalMGroupsService.removeGroups(ids);
        return R.ok();
    }

    @GetMapping("/tree")
    public R groupTree() {
        Integer orgId = this.getSysUser().getOrgId();
        return R.ok().setData(terminalMGroupsService.groups(orgId));
    }

    @PostMapping("/addTerminals")
    public R addTerminals(@RequestBody Map<String, Object> body) {
        Map<?, ?> group = (Map<?, ?>) body.get("group");
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> terminals = (List<Map<String, Object>>) body.get("terminals");
        TerminalMGroups tmg = JSON.parseObject(JSON.toJSONString(group), TerminalMGroups.class);
        tmg.setOrgId(this.getSysUser().getOrgId());
        terminalMGroupsService.addTerminals(tmg, terminals);
        return R.ok();
    }

    @PostMapping("/terminals")
    public R terminals(@RequestBody List<Integer> ids) {
        R r = R.ok();
        r.setData(terminalMGroupsService.groupTerminals(ids, 1, Integer.MAX_VALUE));
        return r;
    }

    @GetMapping("/removeTerminal/{groupId}/{terminalId}")
    public R removeTerminal(@PathVariable("groupId") int groupId, @PathVariable("terminalId") int terminalId) {
        terminalMGroupsService.removeTerminal(groupId, terminalId);
        return R.ok();
    }
}
