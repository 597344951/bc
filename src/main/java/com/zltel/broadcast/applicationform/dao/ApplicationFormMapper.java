package com.zltel.broadcast.applicationform.dao;

import java.util.List;

import com.zltel.broadcast.applicationform.bean.ApplicationForm;

public interface ApplicationFormMapper {
    int deleteByPrimaryKey(Integer formId);

    int insert(ApplicationForm record);

    int insertSelective(ApplicationForm record);

    ApplicationForm selectByPrimaryKey(Integer formId);

    int updateByPrimaryKeySelective(ApplicationForm record);

    int updateByPrimaryKey(ApplicationForm record);
    /**完整查询**/
    List<ApplicationForm> queryFull(ApplicationForm record);

    ApplicationForm selectFullByPrimaryKey(Integer formId);
}
