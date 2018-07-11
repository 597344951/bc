package com.zltel.broadcast.report.dao;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.report.bean.ReportSubmit;

public interface ReportSubmitMapper extends BaseDao<ReportSubmit>{
    int deleteByPrimaryKey(Integer reportId);

    int insert(ReportSubmit record);

    int insertSelective(ReportSubmit record);

    ReportSubmit selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(ReportSubmit record);

    int updateByPrimaryKeyWithBLOBs(ReportSubmit record);

    int updateByPrimaryKey(ReportSubmit record);
}