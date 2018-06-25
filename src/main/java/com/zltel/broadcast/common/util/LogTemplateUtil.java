package com.zltel.broadcast.common.util;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.ReflectUtils;

import com.zltel.broadcast.common.annotation.LogPoint;

/**
 * 日志模板生成工具类
 * 
 * @author wangch
 *
 */
public class LogTemplateUtil {
    private static final Logger logout = LoggerFactory.getLogger(LogTemplateUtil.class);

    private LogTemplateUtil() {}

    public static String getLogContent(LogPoint lp, String[] paramNames, Object[] paramValues) {
        String template = lp.template();
        if (StringUtils.isBlank(template)) return lp.value();
        if (paramValues == null) return template;

        Map<String, Object> params = convertToMap(paramNames, paramValues);
        return processTemplate(template, params);
    }

    /**
     * 计算pojo对应的 map
     * 
     * @param paramNames
     * @param paramValues
     * @return
     */
    private static Map<String, Object> convertToMap(String[] paramNames, Object[] paramValues) {
        // 基础类型， 变为 参数名：参数值
        // pojo ： pojo.参数名 : 参数值

        Map<String, Object> map = new HashMap<>();
        for (int idx = 0; idx < paramValues.length; idx++) {
            Object param = paramValues[idx];
            String pn = paramNames[idx];
            if (null == param) continue;
            // 基础类型 或 java.lang 开头
            if (param.getClass().isPrimitive() || param.getClass().getName().startsWith("java.lang")) {
                map.put(pn, param);
            } else if (param instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<Object, Object> m = (Map<Object, Object>) param;
                m.entrySet().forEach(e -> map.put(e.getKey().toString(), e.getValue()));
            } else {
                map.putAll(handlePOJO(pn + ".", param));
            }
        }

        return map;
    }

    /**
     * 处理 POJO类属性
     * 
     * @param bp 前置参数名
     * @param param 参数值
     * @param map map
     */
    public static Map<String, Object> handlePOJO(String bp, Object param) {
        Map<String, Object> map = new HashMap<>();
        PropertyDescriptor[] pds = ReflectUtils.getBeanGetters(param.getClass());
        for (PropertyDescriptor pd : pds) {
            Method method = pd.getReadMethod();
            String name = pd.getName();
            try {
                Object v = method.invoke(param);
                map.put(bp + name, v);
            } catch (Exception e) {
                logout.error("读取{}参数出错:{}", method.getName(), e.getMessage());
            }
        }
        return map;
    }

    /**
     * 自定义渲染模板
     * 
     * @param template 模版
     * @param params 参数
     * @return
     */
    public static String processTemplate(String template, Map<String, Object> params) {
        if (template == null || params == null) return null;
        StringBuffer sb = new StringBuffer();
        Matcher m = Pattern.compile("\\$\\{[\\w\\.]+\\}").matcher(template);
        while (m.find()) {
            String param = m.group();
            Object value = params.get(param.substring(2, param.length() - 1));
            m.appendReplacement(sb, value == null ? "" : value.toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
