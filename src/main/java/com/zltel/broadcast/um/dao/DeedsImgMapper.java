package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.DeedsImg;

public interface DeedsImgMapper extends BaseDao<DeedsImg>  {
    int deleteByPrimaryKey(Integer id);

    int insert(DeedsImg record);

    int insertSelective(DeedsImg record);

    DeedsImg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeedsImg record);

    int updateByPrimaryKey(DeedsImg record);
    
    public List<DeedsImg> queryDeedsImgs(DeedsImg di);
    
    /**
     * 根据用户事迹删除事迹对应的图片
     * @param deedsUserId
     * @return
     */
    public int deleteDeedsImgByDeedsUserId(Integer deedsUserId);
}