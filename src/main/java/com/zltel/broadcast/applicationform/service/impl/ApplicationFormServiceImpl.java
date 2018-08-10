package com.zltel.broadcast.applicationform.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.applicationform.bean.ApplicationForm;
import com.zltel.broadcast.applicationform.dao.ApplicationFormMapper;
import com.zltel.broadcast.applicationform.service.ApplicationFormService;

@Service
public class ApplicationFormServiceImpl implements ApplicationFormService {

    @Resource
    private ApplicationFormMapper formMapper;

    @Override
    public List<ApplicationForm> queryFull(ApplicationForm record) {
        return this.formMapper.queryFull(record);
    }

    @Override
    public ApplicationForm selectFullByPrimaryKey(Integer formId) {
        return formMapper.selectFullByPrimaryKey(formId);
    }


}
