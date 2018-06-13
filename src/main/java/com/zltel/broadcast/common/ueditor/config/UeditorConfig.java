package com.zltel.broadcast.common.ueditor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zltel.broadcast.common.util.SpringContextUtils;

@Component
public class UeditorConfig {
    @Value("${ueditor.savepath}")
    private String savepath = "\\";

    public String getSavepath() {
        return savepath;
    }

    public static final UeditorConfig getInstince() {
        return SpringContextUtils.getBean(UeditorConfig.class);
    }
}
