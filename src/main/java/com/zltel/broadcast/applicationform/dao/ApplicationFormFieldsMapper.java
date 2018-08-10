package com.zltel.broadcast.applicationform.dao;

import java.util.List;

import com.zltel.broadcast.applicationform.bean.ApplicationFormFields;

public interface ApplicationFormFieldsMapper {
    int deleteByPrimaryKey(Integer fieldId);

    int insert(ApplicationFormFields record);

    int insertSelective(ApplicationFormFields record);

    ApplicationFormFields selectByPrimaryKey(Integer fieldId);

    int updateByPrimaryKeySelective(ApplicationFormFields record);

    int updateByPrimaryKey(ApplicationFormFields record);
    /**根据表单id查询关联字段**/
    List<ApplicationFormFields> selectByFormId(Integer formid);
}
