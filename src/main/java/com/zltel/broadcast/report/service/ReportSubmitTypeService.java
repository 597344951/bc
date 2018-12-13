package com.zltel.broadcast.report.service;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.report.bean.ReportSubmitType;

public interface ReportSubmitTypeService extends BaseDao<ReportSubmitType> {
    /**清空指定目录下所有数据**/
    int deleteSubmitTypeWithAllData(ReportSubmitType record);
    
    List<ReportSubmitType> query(ReportSubmitType record);
}
