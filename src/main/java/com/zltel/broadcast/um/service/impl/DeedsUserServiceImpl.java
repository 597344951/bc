package com.zltel.broadcast.um.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.DeedsUser;
import com.zltel.broadcast.um.dao.DeedsUserMapper;
import com.zltel.broadcast.um.service.DeedsUserService;

@Service
public class DeedsUserServiceImpl extends BaseDaoImpl<DeedsUser> implements DeedsUserService {
	@Resource
    private DeedsUserMapper deedsUserMapper;
	@Override
    public BaseDao<DeedsUser> getInstince() {
        return this.deedsUserMapper;
    }
	
	/**
     * 查询用户事迹
     * @param conditions
     * @return
     */
	@Override
    public R queryDeedsUsers(Map<String, Object> conditions) {
    	List<Map<String, Object>> dusMap = deedsUserMapper.queryDeedsUsers(conditions);	
		if (dusMap != null && dusMap.size() > 0) {	
			return R.ok().setData(dusMap).setMsg("查询成功");
		} else {
			return R.ok().setMsg("没有查询到信息");
		}
    }
    
    /**
     * 新增
     * @param du
     * @return
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertDeedsUser(DeedsUser du) {
    	if (du == null) {
    		return R.error().setMsg("未填写事迹");
    	}
    	du.setTime(new Date());
    	int count = deedsUserMapper.insertSelective(du);
    	if (count == 1) {
    		return R.ok().setMsg("添加成功");
    	} else {
    		return R.error().setMsg("添加失败");
    	}
    }
	
	
	
	
	
	
	
	@Override
	public int updateByPrimaryKeyWithBLOBs(DeedsUser record) {
		return 0;
	}
}
