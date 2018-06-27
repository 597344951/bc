package com.zltel.broadcast.terminal.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageRowBounds;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
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
        Object data = this.solaProgramService.getScreenProgramList(tid);
        return R.ok().setData(data);
    }

    @ApiOperation(value = "取消节目发布")
    @DeleteMapping("/{tid}/program/{pid}")
    public R cancelTerminalProgram(@PathVariable("tid") String tid, @PathVariable("pid") String pid) {
        this.solaProgramService.cancelProgram(pid, tid);
        return R.ok();
    }

    @ApiOperation(value = "查询终端操作日志")
    @GetMapping("/{tid}/operatelog/{pageIndex}-{limit}")
    public R terminalOperateLog(@PathVariable("tid") int tid, @PathVariable("pageIndex") int pageIndex,
            @PathVariable("limit") int limit) {
        Pager prb = new Pager(pageIndex, limit);
        Object data = this.solaProgramService.getScreenOperateLogList(tid, prb);
        return R.ok().setData(data).setPager(prb);
    }

    @ApiOperation(value = "查询终端执行日志")
    @GetMapping("/{tid}/executelog/{pageIndex}-{limit}")
    public R terminalExecuteLog(@PathVariable("tid") int tid, @PathVariable("pageIndex") int pageIndex,
            @PathVariable("limit") int limit) {
        Pager prb = new Pager(pageIndex, limit);
        Object data = this.solaProgramService.getScreenExecuteLogList(tid, prb);
        return R.ok().setData(data).setPager(prb);
    }

    @PostMapping("/{tid}/command")
    public R terminalCommand(@PathVariable("tid") int tid, @RequestBody CmdBean cmd) {
        ValidatorUtils.validateEntity(cmd);
        boolean ret = this.solaProgramService.terminalCommand(cmd.getScreenId(), cmd.getCommandName(), cmd.getCommand(),
                cmd.getCommandContent());
        return ret ? R.ok() : R.error();
    }


}
