package com.zltel.broadcast.planjoin.service;


import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.planjoin.bean.UserPlanJoin;

@Generated(value = "org.junit-tools-1.0.6")
public class PlanJoinServiceTest extends BroadcastApplicationTests {
    @Resource
    private PlanJoinService service;

    private UserPlanJoin upj;
    private Pager pager;

    @Before
    public void init() {
        upj = new UserPlanJoin();
        upj.setOrgId(6);
        upj.setUserId(1);
        pager = new Pager(1, 10);
    }

    @Test
    public void testQueryEndPlan() throws Exception {
        Object data = this.service.queryEndPlan(upj, pager);
        logout.info("已结束活动数据:{}", JSON.toJSONString(data));
    }

    @Test
    public void testQueryJoinedPlan() throws Exception {
        Object data = this.service.queryJoinedPlan(upj, pager);
        logout.info("以报名活动数据:{}", JSON.toJSONString(data));
    }

    @Test
    public void testQueryRelatedPlan() throws Exception {
        Object data = this.service.queryRelatedPlan(upj, pager);
        logout.info("可参加活动数据:{}", JSON.toJSONString(data));
    }
}
