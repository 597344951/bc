package com.zltel.broadcast.resource.service;


import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.resource.bean.MaterialAlbum;

@Generated(value = "org.junit-tools-1.0.6")
public class MaterialAlbumServiceTest extends BroadcastApplicationTests {

    @Resource
    private MaterialAlbumService service;

    @Test
    public void testListMaterialAlbum() throws Exception {
        MaterialAlbum m = new MaterialAlbum(6,1);
        List<MaterialAlbum> data = this.service.listMaterialAlbum(m);
        logout.info("资源分类: {}", JSON.toJSONString(data));
    }
}
