package com.zltel.broadcast.common.configuration;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zltel.broadcast.common.util.SpringContextUtils;

/**
 * 系统相关描述信息
 * 
 * @author iamwa
 *
 */
@Component
public class SystemInfoConfig {
    @Value("${zltel.appname}")
    private String appname = "新党建平台";
    @Value("${zltel.version}")
    private String version = "1.0";
    @Value("${zltel.mediaserve}")
    private String mediaserve;

    /** 获取用户自定义上传路径 **/
    public String getCustomizeUploadUrl() {

        return getMediaServerUrl().append("customize").toString();
    }

    /** 获取媒体服务器地址 **/
    public StringBuilder getMediaServerUrl() {
        StringBuilder sb = new StringBuilder();
        if (mediaserve.startsWith("//")) {
            sb.append("http:");
        } else if (!mediaserve.startsWith("http://") && mediaserve.startsWith("https://")) {
            sb.append("http://");
        }
        sb.append(mediaserve);
        if (!mediaserve.endsWith("/")) sb.append("/");
        return sb;
    }


    public String getAppname() throws UnsupportedEncodingException {
        // properties 中文需要转换编码
        return new String(appname.getBytes("ISO-8859-1"), "UTF-8");
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static final SystemInfoConfig getInstince() {
        return SpringContextUtils.getBean(SystemInfoConfig.class);
    }

    public String getMediaserve() {
        return mediaserve;
    }

    public void setMediaserve(String mediaserve) {
        this.mediaserve = mediaserve;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }


}
