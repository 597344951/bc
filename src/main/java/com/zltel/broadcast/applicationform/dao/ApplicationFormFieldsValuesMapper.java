package com.zltel.broadcast.applicationform.dao;

import java.util.List;

import com.zltel.broadcast.applicationform.bean.ApplicationFormFieldsValues;

public interface ApplicationFormFieldsValuesMapper {
    int deleteByPrimaryKey(Integer vid);

    int insert(ApplicationFormFieldsValues record);

    int insertSelective(ApplicationFormFieldsValues record);

    ApplicationFormFieldsValues selectByPrimaryKey(Integer vid);

    int updateByPrimaryKeySelective(ApplicationFormFieldsValues record);

    int updateByPrimaryKey(ApplicationFormFieldsValues record);

    /** 根据字段id查询候选值 **/
    List<ApplicationFormFieldsValues> selectByFieldId(Integer fieldId);
}
