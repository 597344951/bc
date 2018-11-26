package com.zltel.broadcast.terminal.m.service.impl;

import com.zltel.broadcast.terminal.m.bean.TerminalMGroups;
import com.zltel.broadcast.terminal.m.dao.TerminalMGroupsMapper;
import com.zltel.broadcast.terminal.m.service.TerminalMGroupsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TerminalMGroupsServiceImpl implements TerminalMGroupsService {
    private static final Log log = LogFactory.getLog(TerminalMGroupsServiceImpl.class);
    @Autowired
    private TerminalMGroupsMapper terminalMGroupsMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void addGroup(TerminalMGroups terminalMGroups) {
        terminalMGroupsMapper.insertSelective(terminalMGroups);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void removeGroups(List<Integer> ids) {
        terminalMGroupsMapper.deleteTerminalLinkByGroups(ids);
        terminalMGroupsMapper.deleteByPrimaryKeys(ids);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void updateGroup(TerminalMGroups terminalMGroups) {
        terminalMGroupsMapper.updateByPrimaryKeySelective(terminalMGroups);
    }

    @Override
    public List<TerminalMGroups> groups() {
        List<TerminalMGroups> groupList = terminalMGroupsMapper.selectAll();
        List<TerminalMGroups> group = new ArrayList<>();
        treeGroup(group, groupList);
        return group;
    }

    private void treeGroup(List<TerminalMGroups> group, List<TerminalMGroups> groupList) {
        if (groupList != null && groupList.size() > 0) {
            List<TerminalMGroups> nextList = new ArrayList<>();
            groupList.stream().forEach(item -> {
                if (0 == item.getParentId()) {
                    //顶层分组
                    group.add(item);
                } else {
                    if (!addToGroup(group, item)) nextList.add(item);
                }
            });

            if (nextList.size() == groupList.size()) {
                //匹配失败, 停止
                return;
            } else if (nextList.size() > 0) {
                treeGroup(group, nextList);
            }
        }
    }

    private boolean addToGroup(List<TerminalMGroups> group, TerminalMGroups terminalMGroups) {
        for (TerminalMGroups item : group) {
            if (terminalMGroups.getParentId() == item.getId()) {
                item.addChild(terminalMGroups);
                return true;
            } else {
                if (addToGroup(item.getChildren(), terminalMGroups)) {
                    return true;
                } else {
                    continue;
                }
            }
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void addTerminals(TerminalMGroups group, List<Map<String, Object>> terminals) {
        Date now = new Date(System.currentTimeMillis());
        terminals.stream().forEach(terminal -> {
            try {
                terminalMGroupsMapper.insertTerminalLink(new HashMap<String, Object>() {{
                    put("groupId", group.getId());
                    put("terminalId", terminal.get("oid"));
                    put("addDate", now);
                    put("updateDate", now);
                }});
            } catch (Exception e) {
                if (e instanceof DuplicateKeyException) {
                    //已存在
                    log.warn("重复添加, 终端：" + terminal.get("name") + "已添加到分组：" + group.getLabel());

                } else {
                    throw e;
                }
            }

        });
    }

    @Override
    public List<Map<String, Object>> groupTerminals(List<Integer> groupIds, int pageNum, int pageSize) {
        return terminalMGroupsMapper.selectTerminalByGroup(groupIds, pageNum, pageSize);
    }

    @Override
    public void removeTerminal(int groupId, int terminalId) {
        terminalMGroupsMapper.deleteTerminalLink(groupId, terminalId);
    }
}
