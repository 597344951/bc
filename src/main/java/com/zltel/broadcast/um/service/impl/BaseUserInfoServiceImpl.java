package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.BaseUserInfo;
import com.zltel.broadcast.um.dao.BaseUserInfoMapper;
import com.zltel.broadcast.um.service.BaseUserInfoService;

@Service
public class BaseUserInfoServiceImpl extends BaseDaoImpl<BaseUserInfo> implements BaseUserInfoService {
	@Resource
    private BaseUserInfoMapper baseUserInfoMapper;

    @Override
    public BaseDao<BaseUserInfo> getInstince() {
        return this.baseUserInfoMapper;
    }
    
    /**
     * 查询基础用户信息
     * @param baseUserInfo 条件
     * @return	查询得到的基础用户
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryBaseUserInfos(BaseUserInfo baseUserInfo, int pageNum, int pageSize) throws Exception {
    	PageHelper.startPage(pageNum, pageSize);
		List<BaseUserInfo> baseUserInfos = baseUserInfoMapper.queryBaseUserInfos(baseUserInfo);	//开始查询，没有条件则查询所有基础用户信息
		PageInfo<BaseUserInfo> baseUserInfosForPageInfo = new PageInfo<>(baseUserInfos);
		if (baseUserInfosForPageInfo != null && baseUserInfosForPageInfo.getList().size() > 0) {	//是否查询到数据
			return R.ok().setData(baseUserInfosForPageInfo).setMsg("查询基础用户信息成功");
		} else {
			return R.ok().setMsg("没有查询到基础用户信息");
		}
    }
}
