package com.zltel.broadcast.um.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.IntegralChangeType;
import com.zltel.broadcast.um.dao.IntegralChangeSceneMapper;
import com.zltel.broadcast.um.dao.IntegralChangeTypeMapper;
import com.zltel.broadcast.um.service.IntegralChangeTypeService;

@Service
public class IntegralChangeTypeServiceImpl extends BaseDaoImpl<IntegralChangeType> implements IntegralChangeTypeService {
	@Resource
    private IntegralChangeTypeMapper integralChangeTypeMapper;
	@Resource
    private IntegralChangeSceneMapper integralChangeSceneMapper;
	@Override
    public BaseDao<IntegralChangeType> getInstince() {
        return this.integralChangeTypeMapper;
    }
	
	
	/**
     * 删除分值变更场景
     * @param condition
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public boolean deleteIntegralChangeScene(Map<String, Object> condition) throws Exception {
    	Map<String, Object> querySceneCondition = new HashMap<>();
    	querySceneCondition.put("ictId", condition.get("ictId"));
    	querySceneCondition.put("type", condition.get("type"));
    	List<Map<String, Object>> scenes = integralChangeTypeMapper.queryAllIntegralChangeScene(querySceneCondition);
    	if (scenes == null || scenes.size() < 2) {
    		//如果该操作类型的分值改变场景只有一个或没有
    		integralChangeTypeMapper.deleteByPrimaryKey(Integer.parseInt(String.valueOf(condition.get("ictId"))));
    	} 
    	integralChangeSceneMapper.deleteByPrimaryKey(Integer.parseInt(String.valueOf(condition.get("icsId"))));
		return true;
    }
	
	/**
     * 查询所有分值改变的场景和变更的分值（用于观察内置积分加扣分的设置情况）
     * @param condition
     * @return
     */
    public List<Map<String, Object>> queryAllIntegralChangeScene(Map<String, Object> condition) {
    	return integralChangeTypeMapper.queryAllIntegralChangeScene(condition);
    }
	
	/**
     * 查询分值改变类型
     * @param conditions
     * @return
     */
    public R queryICT_ChangeType(IntegralChangeType ict) {
    	List<IntegralChangeType> icts = integralChangeTypeMapper.queryICT_ChangeType(ict);
    	if (icts != null && icts.size() > 0) {
    		return R.ok().setData(icts);
    	} else {
    		return R.ok().setMsg("么有查询到分值类型");
    	}
    }
    
    /**
     * 查询分值变更分类
     * @param conditions
     * @return
     */
    public R queryICT(IntegralChangeType ict) {
    	List<IntegralChangeType> icts = integralChangeTypeMapper.queryICT(ict);
    	if (icts != null && icts.size() > 0) {
    		return R.ok().setData(icts);
    	} else {
    		return R.ok().setMsg("么有查询到分值变更分类");
    	}
    }
}
