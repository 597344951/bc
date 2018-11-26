package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.DeedsImg;
import com.zltel.broadcast.um.dao.DeedsImgMapper;
import com.zltel.broadcast.um.service.DeedsImgService;

@Service
public class DeedsImgServiceImpl extends BaseDaoImpl<DeedsImg> implements DeedsImgService {
	@Resource
    private DeedsImgMapper deedsImgMapper;
	@Override
    public BaseDao<DeedsImg> getInstince() {
        return this.deedsImgMapper;
    }
	
	/**
     * 查询事迹图片
     * @param di
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryDeedsImgs(DeedsImg di) {
		List<DeedsImg> dis = deedsImgMapper.queryDeedsImgs(di);	
		if (dis != null && dis.size() > 0) {	
			return R.ok().setData(dis).setMsg("查询成功");
		} else {
			return R.ok().setMsg("没有查询到信息");
		}
    }
}
