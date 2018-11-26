package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.JoinPartyOrgFile;

public interface JoinPartyOrgFileMapper extends BaseDao<JoinPartyOrgFile> {
    int deleteByPrimaryKey(Integer id);

    int insert(JoinPartyOrgFile record);

    int insertSelective(JoinPartyOrgFile record);

    JoinPartyOrgFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JoinPartyOrgFile record);

    int updateByPrimaryKey(JoinPartyOrgFile record);
    
    /**
     * 查询该步骤提交的材料
     * @param jpof
     * @return
     */
    public List<JoinPartyOrgFile> queryJoinPartyOrgFiles(JoinPartyOrgFile jpof);
}