package com.zltel.broadcast.template.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.template.bean.TemplateType;

public interface TemplateTypeMapper extends BaseDao<TemplateType> {

    List<TemplateType> queryEachCount(TemplateType tp);

    int delete(TemplateType tp); }
