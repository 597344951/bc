package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.TurnOutOrgProcess;

public interface TurnOutOrgProcessMapper extends BaseDao<TurnOutOrgProcess> {
    int deleteByPrimaryKey(Integer id);

    int insert(TurnOutOrgProcess record);

    int insertSelective(TurnOutOrgProcess record);

    TurnOutOrgProcess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TurnOutOrgProcess record);

    int updateByPrimaryKey(TurnOutOrgProcess record);
    
    /**
     * 查询转移步骤
     * @param toop
     * @return
     */
    public List<TurnOutOrgProcess> queryToop(TurnOutOrgProcess toop);
    
    /**
     * 查询转移步骤
     * @param toop
     * @return
     */
    public List<Map<String, Object>> queryToopOtherOrg(Map<String, Object> condition);
    
    /**
     * 查询最大步骤
     * @param toop
     * @return
     */
    public List<Map<String, Object>> queryToopMaxProcess(Map<String, Object> condition);
}