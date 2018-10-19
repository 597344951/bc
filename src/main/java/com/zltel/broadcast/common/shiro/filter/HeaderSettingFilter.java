package com.zltel.broadcast.common.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.zltel.broadcast.common.configuration.SystemInfoConfig;

/**
 * 设置头
 * 
 * @author wangch
 *
 */
public class HeaderSettingFilter extends AccessControlFilter {



    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse rep) throws Exception {
        HttpServletResponse response = (HttpServletResponse) rep;
        HttpServletRequest request = (HttpServletRequest) req;

        SystemInfoConfig sysInfo = SystemInfoConfig.getInstince();
        
        String origin = request.getHeader("Origin");
        // 如果跨域请求的域名 在允许的白名单中,允许跨域
        if(StringUtils.isNotBlank(origin) && sysInfo.getTrustCrossDomains().contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Authorization,Content-Type,token");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
        }
        
        return true;
    }
}
