package com.zltel.broadcast.report.service;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.report.bean.ReportType;

public interface ReportTypeService extends BaseDao<ReportType> {
    List<ReportType> queryReportTemplateCount(ReportType rcord);
}
