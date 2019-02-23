package com.zltel.broadcast.common.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import com.zltel.broadcast.common.util.SpringContextUtils;

/**
 * 行业语言包 配置
 * 
 * @author wangch
 */
@Component
public class IndustryLanguageConfig {

    private static final Logger log = LoggerFactory.getLogger(IndustryLanguageConfig.class);
    /**所属行业定义,默认medical 医疗行业**/
    @Value("${zltel.industry}")
    private String industry = "medical";
    /** 语言包 map **/
    private volatile Map<String, String> languageMap;


    public static final IndustryLanguageConfig getInstince() {
        IndustryLanguageConfig ins = SpringContextUtils.getBean(IndustryLanguageConfig.class);
        ins.initProperties();
        return ins;
    }

    /**
     * 调用加载语言包的配置文件
     */
    public void initProperties() {
        if (null == languageMap) {
            synchronized (IndustryLanguageConfig.class) {
                if (null == languageMap) {
                    languageMap = new HashMap<>();
                    loadProperties();
                }
            }
        }
    }

    private void loadProperties() {
        String pn = "industry/Language_" + this.industry + ".properties";
        try {
            log.info("加载 行业语言配置文件: {}", pn);
            EncodedResource encodedResource = new EncodedResource(new ClassPathResource(pn), "UTF-8");
            Properties ret = PropertiesLoaderUtils.loadProperties(encodedResource);
            ret.entrySet().forEach(e -> languageMap.put((String) e.getKey(), (String) e.getValue()));
        } catch (IOException e) {
            log.error("加载配置文件:{} 失败", e);
        }

    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Map<String, String> getLanguageMap() {
        return languageMap;
    }

    public void setLanguageMap(Map<String, String> languageMap) {
        this.languageMap = languageMap;
    }


}
