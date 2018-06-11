package com.zltel.broadcast.template.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageRowBounds;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.template.bean.TemplateContent;
import com.zltel.broadcast.template.bean.TemplateType;
import com.zltel.broadcast.template.bean.TemplateTypeTreeNode;
import com.zltel.broadcast.template.dao.TemplateContentMapper;
import com.zltel.broadcast.template.dao.TemplateTypeMapper;
import com.zltel.broadcast.template.service.TemplateTypeService;

@Service
public class TemplateTypeServiceImpl extends BaseDaoImpl<TemplateType> implements TemplateTypeService {
    @Resource
    private TemplateTypeMapper templateTypeMapper;
    @Resource
    private TemplateContentMapper templateContentMapper;

    @Override
    public BaseDao<TemplateType> getInstince() {
        return this.templateTypeMapper;
    }

    @Override
    public List<TemplateType> queryEachCount(TemplateType tp) {
        return this.templateTypeMapper.queryEachCount(tp);
    }

    @Override
    public List<TemplateTypeTreeNode> getTypeTree(TemplateType tp) {
        List<TemplateTypeTreeNode> result = new ArrayList<>();
        List<TemplateType> datas = this.queryEachCount(tp);
        // 获取第一层
        datas.stream().filter(e -> e.getParent() == 0).forEach(n -> {
            TemplateTypeTreeNode tn = new TemplateTypeTreeNode();
            tn.fromTemplateType(n);
            result.add(tn);
        });

        // 递归遍历 子节点
        result.forEach(node -> handleNextNode(node, datas));

        return result;
    }

    /** 递归遍历子节点 **/
    private void handleNextNode(TemplateTypeTreeNode node, List<TemplateType> datas) {
        // 上一级节点的子节点
        List<TemplateTypeTreeNode> childs = new ArrayList<>();
        datas.stream().filter(n -> n.getParent() == node.getId()).forEach(n -> {
            TemplateTypeTreeNode tn = new TemplateTypeTreeNode();
            tn.fromTemplateType(n);
            childs.add(tn);
        });
        if (childs.isEmpty()) return;
        node.setChildren(childs);
        childs.forEach(n -> handleNextNode(n, datas));
    }

    @Override
    @Transactional
    public int delete(TemplateType tp) {
        // 判断节点下是否有 关联的文章
        TemplateContent tc = new TemplateContent();
        tc.setTpTypeId(tp.getTpTypeId());
        PageRowBounds prb = new PageRowBounds(0, 0);
        templateContentMapper.query(tc, prb);
        if (prb.getTotal() > 0) throw new RRException("此分类下包含模板,无法删除!");
        return this.templateTypeMapper.delete(tp);
    }



}
