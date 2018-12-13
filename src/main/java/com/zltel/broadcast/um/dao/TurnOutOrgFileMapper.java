package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.JoinPartyOrgFile;
import com.zltel.broadcast.um.bean.TurnOutOrgFile;

public interface TurnOutOrgFileMapper extends BaseDao<TurnOutOrgFile> {
    int deleteByPrimaryKey(Integer id);

    int insert(TurnOutOrgFile record);

    int insertSelective(TurnOutOrgFile record);

    TurnOutOrgFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TurnOutOrgFile record);

    int updateByPrimaryKey(TurnOutOrgFile record);
    
    /**
     * 查询该步骤提交的材料
     * @param jpof
     * @return
     */
    public List<JoinPartyOrgFile> queryTurnOutOrgFiles(TurnOutOrgFile toof);
}