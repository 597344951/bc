package com.zltel.broadcast.template.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.template.bean.TemplateContent;
import com.zltel.broadcast.template.dao.TemplateContentMapper;
import com.zltel.broadcast.template.service.TemplateContentService;
@Service
public class TemplateContentServiceImpl extends BaseDaoImpl<TemplateContent>
        implements
            TemplateContentService {
    
    @Resource
    private TemplateContentMapper templateContentMapper;

    @Override
    public BaseDao<TemplateContent> getInstince() {
        return this.templateContentMapper;
    }
    
    @Override
    public int updateByPrimaryKeyWithBLOBs(TemplateContent record) {
        return this.templateContentMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public List<TemplateContent> queryByType(TemplateContent record,PageRowBounds prb) {
        return this.templateContentMapper.queryByType(record,prb);
    }

    @Override
    public int delete(TemplateContent record) {
       return this.templateContentMapper.delete(record);
    }

}
