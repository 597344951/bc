package com.zltel.broadcast.resource.bean;


import javax.annotation.Generated;

import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.common.codegenerator.MyBatisScriptCreateUtil;
import com.zltel.broadcast.template.bean.TemplateType;

@Generated(value = "org.junit-tools-1.0.6")
public class MaterialTest extends BaseTests {


    /**生成 查询Where 代码**/
    @Test
    public void testWhereCode() throws Exception {
        StringBuilder sb = MyBatisScriptCreateUtil.createWhereScript(Material.class);
        logout.info(TemplateType.class.getName() +" _> WHERE: \n"+sb.toString());
        
        sb = MyBatisScriptCreateUtil.createSelectScript(Material.class);
        logout.info(TemplateType.class.getName() +" _> SELECT: \n"+sb.toString());
    }
}
