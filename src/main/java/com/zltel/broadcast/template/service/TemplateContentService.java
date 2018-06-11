package com.zltel.broadcast.template.service;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.template.bean.TemplateContent;

public interface TemplateContentService extends BaseDao<TemplateContent> {
     
    int updateByPrimaryKeyWithBLOBs(TemplateContent record);
    /**按类型查询**/
    public List<TemplateContent> queryByType(TemplateContent record);
    /**给定条件删除记录**/
    int delete(TemplateContent record);
}
