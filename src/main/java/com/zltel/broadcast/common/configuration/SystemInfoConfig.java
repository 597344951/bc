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
    @Value("${zltel.posterServe}")
    private String posterServe;
    /** 信任的跨域域名 **/
    @Value("${zltel.trustCrossDomains}")
    private String trustCrossDomains;
    /**所属行业定义,默认medical 医疗行业**/
    @Value("${zltel.industry}")
    private String industry = "medical";

    public static final SystemInfoConfig getInstince() {
        return SpringContextUtils.getBean(SystemInfoConfig.class);
    }

    /** 获取用户自定义上传路径 **/
    public String getCustomizeUploadUrl() {

        return getMediaServerUrl().append("customize").toString();
        
    }

    /** 获取媒体服务器地址 **/
    public StringBuilder getMediaServerUrl() {
        
        return getFullUrl(mediaserve);
    }

    public StringBuilder getPosterServeUrl() {

        return getFullUrl(posterServe);
    }

    private StringBuilder getFullUrl(String in) {
        StringBuilder sb = new StringBuilder();
        if (in.startsWith("//")) {
            sb.append("http:");
        } else if (!in.startsWith("http://") && !in.startsWith("https://")) {
            sb.append("http://");
        }
        sb.append(in);
        if (!in.endsWith("/")) sb.append("/");
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


    public String getMediaserve() {
        return mediaserve;
    }

    public void setMediaserve(String mediaserve) {
        this.mediaserve = mediaserve;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPosterServe() {
        return posterServe;
    }

    public void setPosterServe(String posterServe) {
        this.posterServe = posterServe;
    }

    public String getTrustCrossDomains() {
        return trustCrossDomains;
    }

    public void setTrustCrossDomains(String trustCrossDomains) {
        this.trustCrossDomains = trustCrossDomains;
    }


}
