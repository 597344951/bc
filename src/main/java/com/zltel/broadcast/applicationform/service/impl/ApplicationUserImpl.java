package com.zltel.broadcast.applicationform.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.applicationform.bean.ApplicationForm;
import com.zltel.broadcast.applicationform.bean.ApplicationUser;
import com.zltel.broadcast.applicationform.bean.ApplicationUserData;
import com.zltel.broadcast.applicationform.dao.ApplicationFormMapper;
import com.zltel.broadcast.applicationform.dao.ApplicationUserDataMapper;
import com.zltel.broadcast.applicationform.dao.ApplicationUserMapper;
import com.zltel.broadcast.applicationform.service.ApplicationUserService;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.util.LoginUserUtil;
import com.zltel.broadcast.um.bean.SysUser;

@Service
public class ApplicationUserImpl implements ApplicationUserService {

    @Resource
    private ApplicationUserMapper userMapper;
    @Resource
    private ApplicationFormMapper formMapper;

    @Resource
    private ApplicationUserDataMapper userDataMapper;

    @Override
    public List<ApplicationUser> queryFull(ApplicationUser record) {
        return this.userMapper.queryFull(record);
    }

    @Override
    public void saveUserFormData(Integer formId, Map<String, String> data) {
        SysUser user = LoginUserUtil.getSysUser();
        ApplicationForm form = this.formMapper.selectFullByPrimaryKey(formId);
        if (null == form) throw new RRException("没有找到申请表");
        ApplicationUser userForm = new ApplicationUser(user);
        userForm.setFormId(form.getFormId());
        this.userMapper.insertSelective(userForm);
        List<ApplicationUserData> userDatas = form.getFields().stream().map(field -> {
            String value = data.get(field.getName());
            ApplicationUserData ud = new ApplicationUserData();
            ud.setUserFormId(userForm.getUserFormId());
            ud.setFieldId(field.getFieldId());
            ud.setValue(value);
            return ud;
        }).collect(Collectors.toList());

        userDatas.forEach(this.userDataMapper::insert);
    }



}
