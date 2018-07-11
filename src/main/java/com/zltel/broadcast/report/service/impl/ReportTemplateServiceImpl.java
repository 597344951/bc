package com.zltel.broadcast.report.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.report.bean.ReportTemplate;
import com.zltel.broadcast.report.dao.ReportTemplateMapper;
import com.zltel.broadcast.report.service.ReportTemplateService;

@Service
public class ReportTemplateServiceImpl extends BaseDaoImpl<ReportTemplate> implements ReportTemplateService{

    @Resource
    private ReportTemplateMapper mapper;
    @Override
    public BaseDao<ReportTemplate> getInstince() {
        return this.mapper;
    }

}
