package com.zltel.broadcast.report.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.report.bean.ReportSubmitType;
import com.zltel.broadcast.report.dao.ReportSubmitTypeMapper;
import com.zltel.broadcast.report.service.ReportSubmitTypeService;

@Service
public class ReportSubmitTypeServiceImpl extends BaseDaoImpl<ReportSubmitType> implements ReportSubmitTypeService {

    @Resource
    private ReportSubmitTypeMapper submitTypeMapper;
    
    @Override
    public BaseDao<ReportSubmitType> getInstince() {
        return this.submitTypeMapper;
    }

    @Override
    public int deleteSubmitTypeWithAllData(ReportSubmitType record) {
        // TODO 完成目录删除
        return 0;
    }

    @Override
    public List<ReportSubmitType> query(ReportSubmitType record) {
        
        return this.getInstince().query(record, Pager.DEFAULT_PAGER);
    }

}
