package com.zltel.broadcast.poster.service;

import com.zltel.broadcast.poster.bean.PosterInfo;

/**
 * 
 * @author iamwa
 * @junit {@link PosterCoverServiceTest}
 */
public interface PosterCoverService {
    /**
     * 更新封面预览图
     * 
     * @param posterinfo
     * @junit {@link PosterCoverServiceTest#testUpdateCover}
     */
    void updateCover(PosterInfo posterinfo);

    /**
     * 替换指定海报的相关信息
     * 
     * @param templateId 要替换模板id
     * @param search 替换字符串
     * @param rep 替换内容
     * @junit {@link PosterCoverServiceTest#testReplaceTarget}
     */
    void replaceTarget(Integer[] templateId, String search, String rep);
}
