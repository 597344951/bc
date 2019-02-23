package com.zltel.broadcast.terminal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.util.AdminRoleUtil;
import com.zltel.broadcast.terminal.service.TerminalPlaylistService;

import io.swagger.annotations.ApiOperation;
@RequestMapping(value = {"/terminal/playlist"})
@RestController
public class TerminalPlaylistController extends BaseController {
    @Autowired
    private TerminalPlaylistService tps;
    
    @ApiOperation(value="拉取用户节目")
    @GetMapping("/userProgram")
    public R userProgram() { 
       try {
          Integer userId = null;
          if(!AdminRoleUtil.isPlantAdmin()) {
              userId=this.getSysUser().getUserId();
          }
          return tps.userProgram(userId);
        
    } catch (Exception e) {
        return R.error().setMsg("查询用户节目失败");
    }
    }
    @ApiOperation(value = "统计终端播放信息")
    @PutMapping("/statistics/{string}")
    public R statistics(@PathVariable("string") String string) {
        try {                    
            return tps.statistics(string);
        } catch (Exception e) {
            logout.error(e.getMessage());
            return R.error().setMsg("统计终端基础信息失败");
        }
    }

}
