package com.zltel.broadcast.template.service;

import java.util.List;

import com.github.pagehelper.PageRowBounds;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.template.bean.TemplateContent;

public interface TemplateContentService extends BaseDao<TemplateContent> {

    int updateByPrimaryKeyWithBLOBs(TemplateContent record);

    /**
     * 按类型 和关键字查询模板
     * 
     * @param record
     * @param prb
     * @return
     * @junit {@link com.zltel.broadcast.template.service.TemplateContentServiceTest#testQueryByType()}
     */
    public List<TemplateContent> queryByType(TemplateContent record, PageRowBounds prb);

    /** 给定条件删除记录 **/
    int delete(TemplateContent record);
}
