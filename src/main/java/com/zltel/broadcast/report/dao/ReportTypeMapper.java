package com.zltel.broadcast.report.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.report.bean.ReportType;

public interface ReportTypeMapper extends BaseDao<ReportType> {
    int deleteByPrimaryKey(Integer typeId);

    int insert(ReportType record);

    int insertSelective(ReportType record);

    ReportType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(ReportType record);

    int updateByPrimaryKey(ReportType record);

    /** 查询报告模版每一类的条数 **/
    List<ReportType> queryReportTemplateCount(ReportType rcord);
}
