package com.zltel.broadcast.incision.sola.service;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageRowBounds;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.incision.sola.bean.Category;
import com.zltel.broadcast.incision.sola.bean.ExecuteLog;
import com.zltel.broadcast.incision.sola.bean.OptLog;
import com.zltel.broadcast.incision.sola.bean.ProgramTemp;
import com.zltel.broadcast.incision.sola.bean.PubedProgram;
import com.zltel.broadcast.incision.sola.bean.ResultStatus;

@Generated(value = "org.junit-tools-1.0.6")
public class SolaProgramServiceTest extends BroadcastApplicationTests {

    public static final int TID = 2;
    @Resource
    private SolaProgramService service;

    @Test
    public void testHandleResultStatus() {
        List<String> list = new ArrayList<>();
        list.add("60");
        list.add("0");
        list.add("-1:发生错误");
        list.add("-2:你没有权限访问该WEB服务");
        list.add("0:执行失败");
        list.add("1:执行成功");
        list.add("2:当前节目已经发布，如需删除请先删除终端节目！");
        list.add("1:删除成功");
        list.add("0:请核对参数[参数错误]");
        list.add("0:分类未选择");
        list.add("0:该节目不属于本单位");
        list.add("1:复制成功");
        list.add("1:导入成功");
        list.add("3:请输入用户姓名");
        list.add("2:手机号码已经存在，请重新输入");
        list.add("1:开通成功");
        list.add("0:请输入正确的手机号码");
        list.add("5:请选择节目！");
        list.add("4:请选择终端！");
        list.add("1:发布成功");
        list.add("0:发布失败");

        list.forEach(msg -> {
            ResultStatus rs = this.service.handleResultStatus(msg);
            logout.info("{} -> {}", msg, rs.toString());
        });
    }

    @Test
    public void testGetProgramCategoryList() throws Exception {
        Object v = this.service.getProgramCategoryList(0);
        logout.info("获取节目分类列表: {}", JSON.toJSONString(v));
    }

    @Test
    public void testGetProgramCategoryMouldList() throws Exception {
        List<Category> cats = this.service.getProgramCategoryMouldList();
        logout.info("获取节目模板分类: {}", JSON.toJSONString(cats));
    }

    @Test
    public void testGetProgramList() throws Exception {
        Pager prb = new Pager(1, 10);
        List<Category> cats = this.service.getProgramCategoryMouldList();
        cats.forEach(c -> {
            List<ProgramTemp> pgs = this.service.getProgramList(Integer.valueOf(c.getPkId()), prb);
            logout.info("获取模版节目信息:{}", JSON.toJSON(pgs));
        });
    }
    
    @Test
    public void testGetProgramList2()throws Exception{
        Pager prb = new Pager(1, 10);
        List<Category> cats  = this.service.getProgramCategoryList(0);
        cats.forEach(c -> {
            List<ProgramTemp> pgs = this.service.getProgramList(0, prb);
            logout.info("获取节目信息:{}", JSON.toJSON(pgs));
        });
    }



    @Test
    public void testGetScreenOperateLogList() {
        Pager prb = new Pager(1, 10);
        List<OptLog> logs = this.service.getScreenOperateLogList(TID, prb);
        logout.info("查询终端操作日志,分页:{},内容:{}", JSON.toJSONString(prb), JSON.toJSONString(logs));
    }

    @Test
    public void testGetScreenExecuteLogList() {
        Pager prb = new Pager(1, 10);
        List<ExecuteLog> logs = this.service.getScreenExecuteLogList(TID, prb);
        logout.info("查询终端执行日志,分页:{},内容:{}", JSON.toJSONString(prb), JSON.toJSONString(logs));
    }

    

    @Test
    public void testGetScreenCategoryList() {
       Object data = this.service.getScreenCategoryList(0);
       logout.info("获取终端分类信息:{}",JSON.toJSONString(data));
    }
   

    @Test
    public void testGetBreakingNewsList() {
        String stime = "";
        String etime = "";
        Pager prb = new Pager(1, 10);
        Object data = this.service.getBreakingNewsList(stime, etime, prb);
        logout.info("获取紧急插播节目: {}",JSON.toJSONString(data));
    }

    @Test
    public void testGetScreenProgramList() {
        List<PubedProgram> datas = this.service.getScreenProgramList(TID);
        logout.info("终端已发布节目: {}", JSON.toJSONString(datas));
    }
    
    @Test
    public void testQueryTerminal() {
        Object v = this.service.queryTerminal();
        logout.info("查询终端信息: {}",JSON.toJSONString(v));
    }
    

}
