package com.zltel.broadcast.poster.service;


import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;

import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.poster.bean.PosterInfo;

@Ignore
public class PosterCoverServiceTest extends BroadcastApplicationTests {

    @Resource
    private PosterCoverService service;

    @Test
    public void testUpdateCover() throws Exception {
        PosterInfo pi = new PosterInfo();
        pi.setTemplateId(27989);
        pi.setWidth(1920);
        pi.setHeight(1080);
        logout.info("截取屏幕:{},{}", pi.getWidth(), pi.getHeight());
        this.service.updateCover(pi);
        pi = new PosterInfo();
        pi.setTemplateId(27981);
        pi.setWidth(2463);
        pi.setHeight(3360);
        logout.info("截取屏幕:{},{}", pi.getWidth(), pi.getHeight());
        this.service.updateCover(pi);
        pi = new PosterInfo();
        pi.setTemplateId(825);
        pi.setWidth(1242);
        pi.setHeight(2208);
        logout.info("截取屏幕:{},{}", pi.getWidth(), pi.getHeight());
        this.service.updateCover(pi);

    }

    @Test
    public void testReplaceTarget() throws Exception {
        Integer[] templateIds = {27710};
        String search = "易图";
        String rep = "替换新的内容";
        this.service.replaceTarget(templateIds, search, rep);

    }
}
