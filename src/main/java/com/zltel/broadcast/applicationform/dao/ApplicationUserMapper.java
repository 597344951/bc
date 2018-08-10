package com.zltel.broadcast.applicationform.dao;

import java.util.List;

import com.zltel.broadcast.applicationform.bean.ApplicationUser;

public interface ApplicationUserMapper {
    int deleteByPrimaryKey(Integer userFormId);

    int insert(ApplicationUser record);

    int insertSelective(ApplicationUser record);

    ApplicationUser selectByPrimaryKey(Integer userFormId);

    int updateByPrimaryKeySelective(ApplicationUser record);

    int updateByPrimaryKey(ApplicationUser record);

    List<ApplicationUser> queryFull(ApplicationUser record);
}
