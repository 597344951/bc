package com.zltel.broadcast.eventplan.service;


import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.eventplan.bean.VotingResult;

@Generated(value = "org.junit-tools-1.0.6")
public class EventPlanVotingServiceTest extends BroadcastApplicationTests {

    @Resource
    private EventPlanVotingService service;

    @Test
    public void testQueryVotingInfo() throws Exception {
        Object data = this.service.queryVotingInfo(13);
        logout.info("表决投票数据: {}", JSON.toJSONString(data));
    }

    @Test
    public void testVotingPass() {
        boolean ret = this.service.votingPass(13);
        logout.info("表决是否通过: {}", ret);
    }
    
    @Test
    public void testVotingResult() {
        VotingResult vr = this.service.votingResult(13);
        logout.info("投票结果统计: {}", JSON.toJSONString(vr));
    }
}
