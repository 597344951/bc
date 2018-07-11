package com.zltel.broadcast.report.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.report.bean.ReportTemplate;
import com.zltel.broadcast.report.bean.ReportType;
import com.zltel.broadcast.report.dao.ReportTemplateMapper;
import com.zltel.broadcast.report.dao.ReportTypeMapper;
import com.zltel.broadcast.report.service.ReportTypeService;

@Service
public class ReportTypeServiceImpl extends BaseDaoImpl<ReportType> implements ReportTypeService {


    @Resource
    private ReportTypeMapper mapper;

    @Resource
    private ReportTemplateMapper reportTemplateMapper;

    @Override
    public BaseDao<ReportType> getInstince() {
        return this.mapper;
    }

    @Override
    public int delete(ReportType record) {
        PageRowBounds prb1 = new PageRowBounds(0, 0);
        ReportTemplate record1 = new ReportTemplate();
        record1.setTypeId(record.getTypeId());
        reportTemplateMapper.query(record1, prb1);
        if (prb1.getTotal() > 0) throw new RRException("此分类下包含数据,无法删除!");

        return this.mapper.deleteByPrimaryKey(record.getTypeId());
    }

    @Override
    public List<ReportType> queryReportTemplateCount(ReportType rcord) {
        return this.mapper.queryReportTemplateCount(rcord);
    }

}
