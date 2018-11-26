package com.zltel.broadcast.terminal.m.controller;

import com.alibaba.fastjson.JSON;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.terminal.m.bean.TerminalMGroups;
import com.zltel.broadcast.terminal.m.service.TerminalMGroupsService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/terminal/m/group")
public class TerminalMGroupsController extends BaseController {
    @Autowired
    private TerminalMGroupsService terminalMGroupsService;

    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody TerminalMGroups terminalMGroups) {
        R r = R.ok();
        terminalMGroupsService.addGroup(terminalMGroups);
        r.setData(terminalMGroups);
        return r;
    }

    @PostMapping("/delete")
    @ResponseBody
    public R delete(@RequestBody List<Integer> ids) {
        terminalMGroupsService.removeGroups(ids);
        return R.ok();
    }

    @GetMapping("/tree")
    @ResponseBody
    public R groupTree() {
        R r = R.ok();
        r.setData(terminalMGroupsService.groups());
        return r;
    }

    @PostMapping("/addTerminals")
    @ResponseBody
    public R addTerminals(@RequestBody Map<String, Object> body) {
        Map<String, Object> group = (Map<String, Object>) body.get("group");
        List<Map<String, Object>> terminals = (List<Map<String, Object>>) body.get("terminals");
        terminalMGroupsService.addTerminals(JSON.parseObject(JSON.toJSONString(group), TerminalMGroups.class), terminals);
        return R.ok();
    }

    @PostMapping("/terminals")
    @ResponseBody
    public R terminals(@RequestBody List<Integer> ids) {
        R r = R.ok();
        r.setData(terminalMGroupsService.groupTerminals(ids, 1, Integer.MAX_VALUE));
        return r;
    }

    @GetMapping("/removeTerminal/{groupId}/{terminalId}")
    @ResponseBody
    public R removeTerminal(@PathVariable("groupId") int groupId, @PathVariable("terminalId") int terminalId) {
        terminalMGroupsService.removeTerminal(groupId, terminalId);
        return R.ok();
    }
}
