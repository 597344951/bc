package com.zltel.broadcast.report.dao;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.report.bean.ReportSubmitType;

public interface ReportSubmitTypeMapper extends BaseDao<ReportSubmitType>{
    int deleteByPrimaryKey(Integer typeId);

    int insert(ReportSubmitType record);

    int insertSelective(ReportSubmitType record);

    ReportSubmitType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(ReportSubmitType record);

    int updateByPrimaryKey(ReportSubmitType record);
}