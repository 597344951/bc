package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.IntegralConstitute;

public interface IntegralConstituteMapper extends BaseDao<IntegralConstitute> {
    int deleteByPrimaryKey(Integer icId);

    int insert(IntegralConstitute record);

    int insertSelective(IntegralConstitute record);

    IntegralConstitute selectByPrimaryKey(Integer icId);

    int updateByPrimaryKeySelective(IntegralConstitute record);

    int updateByPrimaryKey(IntegralConstitute record);
    
    /**
     * 查询组织积分结构
     * @param conditions
     * @return
     */
    public List<Map<String, Object>> queryOrgIntegralConstitute(Map<String, Object> conditions);
    
    /**
     * 查询拥有积分结构的组织
     * @param conditions
     * @return
     */
    public List<Map<String, Object>> queryOrgInfoForIc(Map<String, Object> conditions);
    
    /**
     * 查询该组织拥有的党员，仅为党员积分功能服务
     * @param conditions
     * @return
     */
    public List<Map<String, Object>> queryPartyUserInfoAndIcInfo(Map<String, Object> conditions);
}