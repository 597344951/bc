package com.zltel.broadcast.incision.sola.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 资源提取工具类
 * 
 * @author iamwa
 *
 */
public class ResExtractUtil {
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
}
