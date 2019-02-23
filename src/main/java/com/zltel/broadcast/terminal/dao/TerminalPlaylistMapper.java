package com.zltel.broadcast.terminal.dao;

import java.util.List;

import com.zltel.broadcast.terminal.bean.TerminalEcharts;

public interface TerminalPlaylistMapper {
    List<TerminalEcharts> userProgram(Integer userId);
   
 
}
