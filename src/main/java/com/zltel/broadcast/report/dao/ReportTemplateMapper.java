package com.zltel.broadcast.report.dao;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.report.bean.ReportTemplate;

public interface ReportTemplateMapper extends BaseDao<ReportTemplate> {
    int deleteByPrimaryKey(Integer tpId);

    int insert(ReportTemplate record);

    int insertSelective(ReportTemplate record);

    ReportTemplate selectByPrimaryKey(Integer tpId);

    int updateByPrimaryKeySelective(ReportTemplate record);

    int updateByPrimaryKeyWithBLOBs(ReportTemplate record);

    int updateByPrimaryKey(ReportTemplate record);
}