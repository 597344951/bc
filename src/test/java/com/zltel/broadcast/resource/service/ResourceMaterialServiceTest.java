package com.zltel.broadcast.resource.service;


import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.resource.bean.ResourceMaterial;

@Generated(value = "org.junit-tools-1.0.6")
public class ResourceMaterialServiceTest extends BroadcastApplicationTests {
    @Resource
    private ResourceMaterialService service;

    @Test
    public void testQuery() throws Exception {
        ResourceMaterial record = new ResourceMaterial(6, 6);
        Pager pager = new Pager(1, 20);
        List<ResourceMaterial> result = this.service.query(record, pager);

        logout.info("\n pager: {}\n result: {}",JSON.toJSONString(pager),JSON.toJSONString(result));
    }
}
