package com.zltel.broadcast.terminal.m.service;

import com.zltel.broadcast.terminal.m.bean.TerminalMGroups;

import java.util.List;
import java.util.Map;

public interface TerminalMGroupsService {

    public void addGroup(TerminalMGroups terminalMGroups);
    public void removeGroups(List<Integer> ids);
    public void updateGroup(TerminalMGroups terminalMGroups);
    public List<TerminalMGroups> groups();

    public void addTerminals(TerminalMGroups group, List<Map<String, Object>> terminals);

    public List<Map<String, Object>> groupTerminals(List<Integer> groupIds, int pageNum, int pageSize);

    public void removeTerminal(int groupId, int terminalId);
}
