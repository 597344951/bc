package com.zltel.broadcast.um.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.DeedsUser;

public interface DeedsUserService {
	int deleteByPrimaryKey(Integer id);

    int insert(DeedsUser record);

    int insertSelective(DeedsUser record);

    DeedsUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeedsUser record);

    int updateByPrimaryKeyWithBLOBs(DeedsUser record);

    int updateByPrimaryKey(DeedsUser record);
    
    /**
     * 查询用户事迹
     * @param conditions
     * @return
     */
    public R queryDeedsUsers(Map<String, Object> conditions);
    
    /**
     * 新增
     * @param du
     * @return
     */
    public R insertDeedsUser(DeedsUser du);
}
