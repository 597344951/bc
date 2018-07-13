package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.PartyUserInfo;

public interface PartyUserInfoMapper extends BaseDao<PartyUserInfo>  {
    int deleteByPrimaryKey(Integer partyUserId);

    int insert(PartyUserInfo record);

    int insertSelective(PartyUserInfo record);

    PartyUserInfo selectByPrimaryKey(Integer partyUserId);

    int updateByPrimaryKeySelective(PartyUserInfo record);

    int updateByPrimaryKey(PartyUserInfo record);
    
    /**
     * 查询党员信息
     * @param partyUserMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryPartyUserInfos(Map<String, Object> partyUserMap);
    
    /**
     * 导入验证
     * @param partyUserMap
     * @return
     * @throws Exception
     */
    public List<PartyUserInfo> queryPartyUserInfosForValidata(Map<String, Object> partyUserMap);
}