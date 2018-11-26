package com.zltel.broadcast.poster.service;

import java.util.List;

import com.zltel.broadcast.poster.bean.PosterCategory;
import com.zltel.broadcast.poster.bean.PosterInfo;
import com.zltel.broadcast.poster.bean.PosterLayouts;
import com.zltel.broadcast.poster.bean.PosterSize;

/**
 * 
 * @author iamwa
 * @junit {@link com.zltel.broadcast.poster.service.PosterInfoServiceTest}
 */
public interface PosterInfoService {

    List<PosterInfo> query(PosterInfo posterinfo);

    PosterInfo selectAllByPrimaryKey(Integer templateId);

    void deleteByPrimaryKey(Integer templateId);

    int insert(PosterInfo record);

    /**
     * 
     * @param record
     * @return
     */
    int insertSelective(PosterInfo record);

    void updateByPrimaryKey(PosterInfo record);

    void updateByPrimaryKeySelective(PosterInfo record);

    List<PosterSize> queryPosterSize(PosterSize record);

    /**
     * 获取海报分类信息
     * 
     * @param type 分类类型
     * @return
     */
    List<PosterCategory> queryCategorys(Integer type);

    /***
     * 从指定海报 创建新的模板， 如果不存在则创建新的空白模板
     * 
     * @param templateId 模板id
     * @return
     */
    PosterInfo newPosterFromTemplateId(Integer templateId);
    
    void updateLayoutsByPrimaryKey(PosterLayouts layouts);
    
    /**
     * 搜索源数据信息
     * @param regex 搜索字符串
     * @return 
     */
    List<PosterInfo> searchMetaData(String regex);
}
