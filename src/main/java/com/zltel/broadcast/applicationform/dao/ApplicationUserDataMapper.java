package com.zltel.broadcast.applicationform.dao;

import java.util.List;

import com.zltel.broadcast.applicationform.bean.ApplicationUserData;

public interface ApplicationUserDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplicationUserData record);

    int insertSelective(ApplicationUserData record);

    ApplicationUserData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplicationUserData record);

    int updateByPrimaryKey(ApplicationUserData record);
    
    List<ApplicationUserData> selectByUserFormId(Integer userFormId);
}