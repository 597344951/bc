package com.zltel.broadcast.applicationform.service;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.applicationform.bean.ApplicationUser;

public interface ApplicationUserService {

    List<ApplicationUser> queryFull(ApplicationUser record);
    /**保存用户申请表数据**/
    void saveUserFormData(Integer formId, Map<String, String> data);
}
