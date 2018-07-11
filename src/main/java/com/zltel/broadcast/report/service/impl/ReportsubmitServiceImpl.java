package com.zltel.broadcast.report.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.report.bean.ReportSubmit;
import com.zltel.broadcast.report.dao.ReportSubmitMapper;
import com.zltel.broadcast.report.service.ReportsubmitService;
@Service
public class ReportsubmitServiceImpl extends BaseDaoImpl<ReportSubmit> implements ReportsubmitService {

    @Resource
    private ReportSubmitMapper mapper;

    @Override
    public BaseDao<ReportSubmit> getInstince() {
        return this.mapper;
    }


}
