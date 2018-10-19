package com.zltel.broadcast.poster.service;

import java.util.List;

import com.zltel.broadcast.poster.bean.PosterCategory;
import com.zltel.broadcast.poster.bean.PosterInfo;
import com.zltel.broadcast.poster.bean.PosterSize;
/**
 * 
 * @author iamwa
 * @junit {@link com.zltel.broadcast.poster.service.PosterInfoServiceTest}
 */
public interface PosterInfoService {

    List<PosterInfo> query(PosterInfo posterinfo);

    PosterInfo selectAllByPrimaryKey(Integer templateId);

    int deleteByPrimaryKey(Integer templateId);

    int insert(PosterInfo record);

    int insertSelective(PosterInfo record);

    int updateByPrimaryKey(PosterInfo record);

    int updateByPrimaryKeySelective(PosterInfo record);

    List<PosterSize> queryPosterSize(PosterSize record);
    /**
     * 获取海报分类信息
     * @param type 分类类型
     * @return
     */
    List<PosterCategory> queryCategorys(Integer type);
}  
