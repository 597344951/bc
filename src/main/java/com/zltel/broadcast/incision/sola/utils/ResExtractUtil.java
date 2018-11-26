package com.zltel.broadcast.incision.sola.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源提取工具类
 * 
 * @author iamwa
 *
 */
public class ResExtractUtil {

    private static final Logger log = LoggerFactory.getLogger(ResExtractUtil.class);

    private ResExtractUtil() {}

    /**
     * 提取 img 标签内的图片地址
     * 
     * @param in
     * @return
     */
    public static final String getImageUrl(String in) {
        if (StringUtils.isBlank(in)) return "";
        // img\W+src=[\"]+([\/\-\.\w]+)+
        String rstr = "img\\W+src=[\"]?([\\/\\-\\.\\w:]+)\"?";
        Matcher matcher = Pattern.compile(rstr).matcher(in);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    /**
     * 替换资源地址的域名
     * 
     * @param resUrl 资源地址
     * @param domain 替换域名
     * @return 替换后地址
     */
    public static final String repResDomain(String resUrl, String domain) {
        String regexStr = "//([\\w-\\.:]+)/?";
        String tc = "";
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(domain);
        if (matcher.find()) {
            tc = matcher.group();
        } else {
            return resUrl;
        }
        String n = pattern.matcher(resUrl).replaceFirst(tc);
        if (!n.equals(resUrl)) {
            log.debug("替换资源域名, from : {} -> {}", resUrl, n);
        }
        return n;
    }
}
