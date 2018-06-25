package com.zltel.broadcast.terminal.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.incision.sola.service.SolaProgramService;
import com.zltel.broadcast.terminal.bean.CmdBean;

import io.swagger.annotations.ApiOperation;

/**
 * 终端控制控制器
 * 
 * @author wangch
 *
 */
@RequestMapping(value = {"/terminal/control"})
@RestController
public class TerminalControlController extends BaseController {
    @Resource
    private SolaProgramService solaProgramService;

    @ApiOperation(value = "查询终端节目")
    @GetMapping("/{tid}/program")
    public R terminalProgram(@PathVariable("tid") int tid) {
        List<Map<String, Object>> data = this.solaProgramService.queryTerminalProgram(tid);
        return R.ok().setData(data);
    }

    @ApiOperation(value = "查询终端操作日志")
    @GetMapping("/{tid}/operatelog")
    public R terminalOperateLog(@PathVariable("tid") int tid) {
        List<Map<String, Object>> data = this.solaProgramService.queryTerminalOperateLog(tid);
        return R.ok().setData(data);
    }

    @ApiOperation(value = "查询终端执行日志")
    @GetMapping("/{tid}/executelog")
    public R terminalExecuteLog(@PathVariable("tid") int tid) {
        List<Map<String, Object>> data = this.solaProgramService.queryTerminalExecuteLog(tid);
        return R.ok().setData(data);
    }

    @PostMapping("/{tid}/command")
    public R terminalCommand(@PathVariable("tid") int tid, @RequestBody CmdBean cmd) {
        ValidatorUtils.validateEntity(cmd);
        boolean ret = this.solaProgramService.terminalCommand(cmd.getScreenId(), cmd.getCommandName(), cmd.getCommand(),
                cmd.getCommandContent());
        return ret ? R.ok() : R.error();
    }


}
