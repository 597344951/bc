package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.NationType;
import com.zltel.broadcast.um.dao.NationTypeMapper;
import com.zltel.broadcast.um.service.NationTypeService;

@Service
public class NationTypeServiceImpl extends BaseDaoImpl<NationType> implements NationTypeService {
	@Resource
    private NationTypeMapper nationTypeMapper;
	
	@Override
    public BaseDao<NationType> getInstince() {
        return this.nationTypeMapper;
    }
	
	/**
     * 查询民族
     * @param nationType
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryNationType(NationType nationType) throws Exception {
		List<NationType> nationTypes = nationTypeMapper.queryNationType(nationType);	//开始查询，没有条件则查询所有基础用户信息
		if (nationTypes != null && nationTypes.size() > 0) {	//是否查询到数据
			return R.ok().setData(nationTypes).setMsg("查询民族成功");
		} else {
			return R.ok().setMsg("没有查询到民族");
		}
    }
}
