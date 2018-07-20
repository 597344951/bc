package com.zltel.broadcast.common.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.zltel.broadcast.common.logger.Level;
import com.zltel.broadcast.common.logger.LogFactory;
import com.zltel.broadcast.common.logger.Logger;
import com.zltel.broadcast.common.util.SpringContextUtils;
import com.zltel.broadcast.um.bean.SysUser;

/**
 * 基础控制器 BaseController class
 *
 * @author Touss
 * @date 2018/5/7
 */
public class BaseController {
    public static final org.slf4j.Logger logout = LoggerFactory.getLogger(BaseController.class);
    private Logger operateLog;

    public BaseController() {}

    public BaseController(Class<?> clazz, String feature) {
        this.operateLog = LogFactory.getLog(clazz, feature);
    }

    public SysUser getSysUser() {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        if (null == user) throw new UnauthenticatedException();
        return user;
    }

    public Subject getSubJect() {
        return SecurityUtils.getSubject();
    }

    public void log(HttpSession session, String level, String msg) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            operateLog.setUsername(username);
        }
        operateLog.log(level, msg);
    }

    public void debug(HttpSession session, String msg) {
        log(session, Level.LEVEL_DEBUG, msg);
    }

    public void info(HttpSession session, String msg) {
        log(session, Level.LEVEL_INFO, msg);
    }

    public void warn(HttpSession session, String msg) {
        log(session, Level.LEVEL_WARN, msg);
    }

    public void error(HttpSession session, String msg) {
        log(session, Level.LEVEL_ERROR, msg);
    }

    public void fatal(HttpSession session, String msg) {
        log(session, Level.LEVEL_FATAL, msg);
    }

    /** 注册日期格式化处理 **/
    @InitBinder
    public void dateParamsHandle(WebDataBinder dataBinder) {
        ConversionService conversionService = SpringContextUtils.getBean(ConversionService.class);
        dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                // 空数据 直接设置null
                if (StringUtils.isBlank(value)) {
                    this.setValue(null);
                } else {
                    // 调用转换服务
                    try {
                        TypeDescriptor targetType = TypeDescriptor.valueOf(Date.class);
                        TypeDescriptor sourceType = TypeDescriptor.valueOf(String.class);
                        this.setValue(conversionService.convert(value, sourceType, targetType));
                    } catch (Exception e) {
                        logout.error("转换类型出错:{}", e.getMessage());
                        this.setValue(null);
                    }
                }
            }

            @Override
            public String getAsText() {
                return this.getValue().toString();
            }

        });
    }
}
