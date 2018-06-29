package com.zltel.broadcast.template.dao;

import java.util.List;

import com.github.pagehelper.PageRowBounds;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.template.bean.TemplateContent;

public interface TemplateContentMapper extends BaseDao<TemplateContent> {

    int updateByPrimaryKeyWithBLOBs(TemplateContent record);

    List<TemplateContent> queryByType(TemplateContent record, PageRowBounds prb);

    int delete(TemplateContent record);
}
