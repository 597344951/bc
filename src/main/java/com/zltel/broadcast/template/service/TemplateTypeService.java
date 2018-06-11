package com.zltel.broadcast.template.service;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.template.bean.TemplateType;
import com.zltel.broadcast.template.bean.TemplateTypeTreeNode;

public interface TemplateTypeService extends BaseDao<TemplateType> {
    /** 查询每一分类类 对应的文章数 **/
    public List<TemplateType> queryEachCount(TemplateType tp);

    /** 获取分类 树形结构信息 **/
    public List<TemplateTypeTreeNode> getTypeTree(TemplateType tp);
    /**删除分类数据**/
    public int delete(TemplateType tp);

}
