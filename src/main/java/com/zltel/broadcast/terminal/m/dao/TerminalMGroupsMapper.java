package com.zltel.broadcast.terminal.m.dao;

import com.zltel.broadcast.terminal.m.bean.TerminalMGroups;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TerminalMGroupsMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByPrimaryKeys(@Param("ids") List<Integer> ids);

    int insert(TerminalMGroups record);

    int insertSelective(TerminalMGroups record);

    TerminalMGroups selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TerminalMGroups record);

    int updateByPrimaryKey(TerminalMGroups record);

    List<TerminalMGroups> selectAll();

    int insertTerminalLink(Map<String, Object> terminalLink);

    int deleteTerminalLink(@Param("groupId") Integer groupId, @Param("terminalId") Integer terminalId);

    int deleteTerminalLinkByGroup(@Param("groupId") Integer groupId);

    int deleteTerminalLinkByGroups(@Param("groupIds") List<Integer> groupIds);

    List<Map<String, Object>> selectTerminalByGroup(@Param("groupIds") List<Integer> groupIds, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}