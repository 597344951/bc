package com.zltel.broadcast.common.util;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.ReflectUtils;

import com.zltel.broadcast.common.annotation.LogPoint;

/**
 * 日志模板生成工具类
 * 
 * @author wangch
 * @junit com.zltel.broadcast.common.util.LogTemplateUtilTest
 */
public class LogTemplateUtil {
    private static final Logger logout = LoggerFactory.getLogger(LogTemplateUtil.class);

    private LogTemplateUtil() {}

    public static String getLogContent(LogPoint lp, String[] paramNames, Object[] paramValues) {
        String template = lp.template();
        if (StringUtils.isBlank(template)) return lp.value();
        if (paramValues == null) return template;

        List<ProperyType> ptl = LogTemplateUtil.getVariableNames(template);
        Map<String, Object> params = convertToMap(paramNames, paramValues, ptl);
        logout.debug("预定义变量： {}", params);
        return processTemplate(template, params);
    }

    /**
     * 获取指定属性的值 成 Map
     * 
     * @param paramNames 变量名称
     * @param paramValues 变量对象值
     * @param ptl 变量信息
     * @return
     */
    private static Map<String, Object> convertToMap(String[] paramNames, Object[] paramValues, List<ProperyType> ptl) {
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
                map.putAll(handlePropertyValue(pn + ".", param,
                        ptl.stream().filter(pt -> pn.equals(pt.base)).collect(Collectors.toList())));
            }
        }
        return map;
    }

    /**
     * 读取 属性值
     * 
     * @param bp
     * @param param
     * @param ptl
     * @return
     */
    private static Map<String, Object> handlePropertyValue(String bp, Object param, List<ProperyType> ptl) {
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
        ptl.stream().filter(pt -> pt.type == ProperyType.TYPE.METHOD).forEach(pt -> {
            try {
                Object v = param.getClass().getMethod(pt.getName()).invoke(param);
                map.put(pt.toString(), v);
            } catch (Exception e) {
                logout.error(e.getMessage());
            }
        });
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
        Matcher m = Pattern.compile("\\$\\{[\\w\\.\\(\\)]+\\}").matcher(template);
        while (m.find()) {
            String param = m.group();
            Object value = params.get(param.substring(2, param.length() - 1));
            m.appendReplacement(sb, value == null ? "" : value.toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获取模板中的 变量信息
     * 
     * @param template
     * @return
     */
    public static List<ProperyType> getVariableNames(String template) {
        List<ProperyType> list = new ArrayList<>();
        if (template == null) return list;
        // 提取属性
        Matcher m = Pattern.compile("\\$\\{(\\w+)\\.(\\w+)\\}").matcher(template);
        while (m.find()) {
            String base = m.group(1);
            String name = m.group(2);

            list.add(new ProperyType(name, base, ProperyType.TYPE.PROPERTY));
        }
        // 提取方法
        m = Pattern.compile("\\$\\{(\\w+)\\.(\\w+)(\\(\\W*\\))\\}").matcher(template);
        while (m.find()) {
            String base = m.group(1);
            String name = m.group(2);
            String other = m.group(3);
            ProperyType p = new ProperyType(name, base, ProperyType.TYPE.METHOD);
            p.setOther(other);
            list.add(p);
        }
        return list;
    }

    public static class ProperyType {
        public enum TYPE {
            PROPERTY, METHOD
        }

        /** 属性名 **/
        private String name;
        /** 属性前置字段 **/
        private String base;
        /** 属性/方法 **/
        private TYPE type;
        /** 其他占位符 **/
        private String other;


        public ProperyType(String name, String base, TYPE type) {
            super();
            this.name = name;
            this.base = base;
            this.type = type;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(base).append(".").append(name);
            if (null != other) sb.append(other);
            return sb.toString();
        }

        public String getOther() {
            return other;
        }



        public void setOther(String other) {
            this.other = other;
        }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public TYPE getType() {
            return type;
        }

        public void setType(TYPE type) {
            this.type = type;
        }

    }
}
