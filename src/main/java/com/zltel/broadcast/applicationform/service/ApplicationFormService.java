package com.zltel.broadcast.applicationform.service;

import java.util.List;

import com.zltel.broadcast.applicationform.bean.ApplicationForm;

public interface ApplicationFormService {

    List<ApplicationForm> queryFull(ApplicationForm record);
    
    ApplicationForm selectFullByPrimaryKey(Integer formId);

}
