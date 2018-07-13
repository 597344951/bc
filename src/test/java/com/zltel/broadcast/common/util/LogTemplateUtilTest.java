package com.zltel.broadcast.common.util;

import java.lang.annotation.Annotation;

import com.alibaba.fastjson.JSON;
import com.zltel.BaseTests;
import com.zltel.broadcast.common.annotation.LogPoint;

public class LogTemplateUtilTest extends BaseTests {

     

    @org.junit.Test
    public void testGetLogContent() {
        LogPoint lp = new LogPoint() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public String value() {
                return null;
            }

            @Override
            public int type() {
                return 0;
            }

            @Override
            public String template() {
                return "测试模板:${pa.name} - ${pa.value}, ${pa.toString()},${pa.getName()}";
            }
        };
        Test test = new Test();
        String[] paramNames = {"pa"};
        Object[] paramValues = {test};
        String content = LogTemplateUtil.getLogContent(lp, paramNames, paramValues);
        logout.info(content);
    }

    @org.junit.Test
    public void testGetVariableNames() {
        String tp = "测试模板:${pa.name} - ${pa.value}, ${pa.toString()}";
        Object data = LogTemplateUtil.getVariableNames(tp);
        logout.info(JSON.toJSONString(data));
    }

    public static class Test {
        private String name = "wangch";
        private String value = "test";

        
        @Override
        public String toString() {
            return "Test [name=" + name + ", value=" + value + "]";
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

}
