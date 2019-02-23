package com.zltel.broadcast.terminal.service;

import com.zltel.broadcast.common.json.R;

public interface TerminalPlaylistService {
    public R userProgram(Integer userId);
    
    public R statistics(String string);
}
