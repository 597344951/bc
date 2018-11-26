package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.DeedsImg;

public interface DeedsImgService {
	int deleteByPrimaryKey(Integer id);

    int insert(DeedsImg record);

    int insertSelective(DeedsImg record);

    DeedsImg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeedsImg record);

    int updateByPrimaryKey(DeedsImg record);
    
    /**
     * 查询事迹图片
     * @param di
     * @return
     */
    public R queryDeedsImgs(DeedsImg di);
}
