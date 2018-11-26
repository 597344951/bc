package com.zltel.broadcast.poster.service;


import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.poster.bean.PosterInfo;

public class PosterInfoServiceTest extends BroadcastApplicationTests {

    @Resource
    private PosterInfoService service;


    @Test
    public void testQuery() throws Exception {
        Object v = null;
        PosterInfo posterinfo = new PosterInfo();
        posterinfo.setUseCategory(16);
        v = this.service.query(posterinfo);
        logout.info(JSON.toJSONString(v));

    }
}
