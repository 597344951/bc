package com.zltel.broadcast.incision.sola.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.incision.sola.bean.BreakingNews;
import com.zltel.broadcast.incision.sola.bean.Category;
import com.zltel.broadcast.incision.sola.bean.ExecuteLog;
import com.zltel.broadcast.incision.sola.bean.OptLog;
import com.zltel.broadcast.incision.sola.bean.ProgramTemp;
import com.zltel.broadcast.incision.sola.bean.PubedProgram;
import com.zltel.broadcast.incision.sola.bean.ResultStatus;
import com.zltel.broadcast.incision.sola.service.SolaProgramService;
import com.zltel.broadcast.incision.sola.utils.DESUtil;
import com.zltel.broadcast.incision.sola.utils.HttpUtil;
import com.zltel.broadcast.incision.sola.utils.JsonUtils;

/**
 * SolaProgramServiceImpl class
 *
 * @author Touss
 * @date 2018/5/17
 */
@Service
public class SolaProgramServiceImpl implements SolaProgramService {

    private static final String SCREEN_ID = "screenId";

    private static final String PAGE_COUNT = "pageCount";

    private static final String PAGE_INDEX = "pageIndex";

    private static final String ORG_ID = "OrgId";

    private static final String RECORD_COUNT = "RecordCount";

    private static final Logger log = LoggerFactory.getLogger(SolaProgramServiceImpl.class);

    @Value("${sola.user.loginname}")
    private String loginname;
    @Value("${sola.user.password}")
    private String password;
    @Value("${sola.url}")
    private String url;
    @Value("${sola.user.org}")
    private int org;

    @Override
    public int addProgram(Map<String, Object> program) {
        program.put(ORG_ID, org);
        String ret = execute(program, "AddProgram");
        log.debug("新增节目<AddProgram>:{}", ret);
        ResultStatus rs = this.handleResultStatus(ret);
        if (!rs.isSuccess()) {
            String em = "添加节目编辑失败：" + ret;
            log.error(em);
            RRException.makeThrow(em);
        }
        return Integer.parseInt(ret.replaceAll("\"", ""));
    }

    @Override
    public void publish(Map<String, Object> content) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> terminals = (List<Map<String, Object>>) content.get("terminals");
        String ts = terminals.stream().map(terminal -> terminal.get("terminal_id").toString())
                .collect(Collectors.joining(","));
        Map<String, Object> program = new HashMap<>();
        program.put(ORG_ID, org);
        program.put("screenIds", ts);
        program.put("programIds", content.get("snapshot"));
        program.put("activeStartTime", content.get("start_date"));// 开始日期
        program.put("activeEndTime", content.get("end_date"));// 结束日期
        program.put("times", content.get("period"));// 时间段
        program.put("weeks", "0,1,2,3,4,5,6");// 星期几
        program.put("programPlayType", 1);// 节目播放类型：0为计划节目播放；1为轮播节目播放
        program.put("publishTimeType", 1);// 发布时间类型：0为作息时间发布；1为计划时间发布
        program.put("delayTime", 0);// 作息时间发布时，延后播放时间：单位分钟
        program.put("playTime", 0);// 作息时间发布时，播放时间长度：单位分钟
        program.put("isDelete", 0);// 是否删除过期节目
        String msg = execute(program, "PublishProgram");
        log.debug("发布节目<PublishProgram>:{}", msg);
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "发布失败: " + rs.getMsg();
            log.error(em);
            RRException.makeThrow(em);
        }
        log.info("成功发布节目：{}", content.get("title"));
    }

    @Override
    public void cancelProgram(String pids, String tids) {
        Map<String, Object> program = new HashMap<>();
        program.put("programIds", pids);
        program.put("screenIds", tids);
        program.put(ORG_ID, org);
        String msg = execute(program, "CancelPublishProgram");
        log.debug("取消已发布节目<CancelPublishProgram>:{}", msg);
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "取消播放失败: " + rs.getMsg();
            log.error(em);
            RRException.makeThrow(em);
        }
        log.info("终端:{}取消播放{}成功", tids, pids);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> queryTerminal() {
        Map<String, Object> param = new HashMap<>();
        param.put(ORG_ID, org);
        param.put("categoryId", 0);
        param.put(PAGE_INDEX, 1);
        param.put(PAGE_COUNT, Integer.MAX_VALUE);
        String msg = execute(param, "GetScreenList");
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "拉取终端信息失败: " + rs.getMsg();
            log.error(em);
            RRException.makeThrow(em);
        }

        Map<?, ?> map = (Map<?, ?>) JSON.parse(msg);
        return (List<Map<String, Object>>) map.get("DISSchoolClassScreenModelList");
    }



    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> queryOrg() {
        Map<String, Object> param = new HashMap<>();
        param.put(PAGE_INDEX, 1);
        param.put(PAGE_COUNT, Integer.MAX_VALUE);
        String msg = execute(param, "GetOrgList");
        log.debug("开通的单位列表:{}", msg);
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "拉取组织信息失败: " + rs.getMsg();
            log.error(em);
            RRException.makeThrow(em);
        }

        Map<?, ?> map = (Map<?, ?>) JSON.parse(msg);
        return (List<Map<String, Object>>) map.get("OOMList");
    }


    @Override
    public boolean terminalCommand(int tid, String label, String name, String code) {
        Map<String, Object> command = new HashMap<>();
        command.put(ORG_ID, org);
        command.put(SCREEN_ID, tid);
        command.put("commandName", label);
        command.put("command", name);
        command.put("commandContent", code);
        String msg = execute(command, "SendScreenCommand");
        log.debug("发送命令{}:{}", label, msg);
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "执行命令失败: " + rs.getMsg();
            log.error(em);
            RRException.makeThrow(em);
        }
        return true;
    }

    private String execute(Map<String, Object> program, String serviceName) {
        Map<String, Object> access = new HashMap<>();
        access.put("ServiceName", serviceName);
        access.put("Date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date()));
        access.put("LoginName", loginname);
        access.put("LoginPwd", password);

        String sign = DESUtil.encode(JsonUtils.jsonParams(access));

        program.put("ServiceName", serviceName);
        program.put("Sign", sign);

        return HttpUtil.post(url, "data=" + JsonUtils.serialization(program));
    }

    @Override
    public List<PubedProgram> getScreenProgramList(int tid) {
        List<PubedProgram> rets = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ORG_ID, org);
        param.put(SCREEN_ID, tid);
        String msg = execute(param, "GetScreenProgramList");
        log.debug("终端已发布节目<GetScreenProgramList>:{}", msg);
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "拉取终端已发布节目失败: " + rs.getMsg();
            log.error(em);
            RRException.makeThrow(em);
        }
        JSONArray ja = JSON.parseArray(msg);
        rets = ja.stream().map(jo -> JSON.toJavaObject((JSONObject) jo, PubedProgram.class))
                .collect(Collectors.toList());
        return rets;
    }

    @Override
    public List<OptLog> getScreenOperateLogList(int tid, Pager prb) {
        List<OptLog> rets = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ORG_ID, org);
        param.put(SCREEN_ID, tid);
        param.put(PAGE_INDEX, prb.getPageIndex());
        param.put(PAGE_COUNT, prb.getLimit());
        String msg = execute(param, "GetScreenOperateLogList");
        log.debug("GetScreenOperateLogList: {}", msg);
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "拉取操作日志失败: " + rs.getMsg();
            log.error(em);
            RRException.makeThrow(em);
        }
        JSONObject jsonObject = JSON.parseObject(msg);
        Integer rcs = (Integer) jsonObject.get(RECORD_COUNT);
        prb.setTotal(Long.valueOf(rcs));
        JSONArray ja = jsonObject.getJSONArray("DISOperationLogModelList");
        rets = ja.stream().map(jo -> JSON.toJavaObject((JSONObject) jo, OptLog.class)).collect(Collectors.toList());
        return rets;
    }

    @Override
    public List<ExecuteLog> getScreenExecuteLogList(int tid, Pager prb) {
        List<ExecuteLog> rets = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ORG_ID, org);
        param.put(SCREEN_ID, tid);
        param.put(PAGE_INDEX, prb.getPageIndex());
        param.put(PAGE_COUNT, prb.getLimit());
        String msg = execute(param, "GetScreenExecuteLogList");
        log.debug("终端执行日志<GetScreenExecuteLogList>:{}", msg);
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "拉取执行日志失败: " + rs.getMsg();
            log.error(em);
            RRException.makeThrow(em);
        }
        JSONObject jsonObject = JSON.parseObject(msg);
        Integer rcs = (Integer) jsonObject.get(RECORD_COUNT);
        prb.setTotal(Long.valueOf(rcs));
        JSONArray ja = jsonObject.getJSONArray("DSISLogModelList");
        rets = ja.stream().map(jo -> JSON.toJavaObject((JSONObject) jo, ExecuteLog.class)).collect(Collectors.toList());
        return rets;
    }

    @Override
    public List<Category> getProgramCategoryList(int parentId) {
        List<Category> cats = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ORG_ID, org);
        param.put("parentId", parentId);
        String msg = execute(param, "GetProgramCategoryList");
        log.info("节目分类列表<GetProgramCategoryList>: {}", msg);
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "获取节目分类失败: " + rs.getMsg();
            log.error(em);
            throw new RRException(em);
        }
        JSONArray ja = JSON.parseArray(msg);
        cats = ja.stream().map(jo -> JSON.toJavaObject((JSONObject) jo, Category.class)).collect(Collectors.toList());
        return cats;
    }

    @Override
    public List<Category> getProgramCategoryMouldList() {
        List<Category> cats = null;
        Map<String, Object> command = new HashMap<>();
        command.put(ORG_ID, org);
        String msg = execute(command, "GetProgramCategoryMouldList");
        log.debug("查询节目模版分类<GetProgramCategoryMouldList>: {}", msg);
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "获取节目模版分类失败: " + rs.getMsg();
            log.error(em);
            throw new RRException(em);
        }
        JSONArray ja = JSON.parseArray(msg);
        cats = ja.stream().map(jo -> JSON.toJavaObject((JSONObject) jo, Category.class)).collect(Collectors.toList());
        return cats;
    }

    @Override
    public List<ProgramTemp> getProgramList(int categoryId, Pager prb) {
        List<ProgramTemp> rets = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ORG_ID, org);
        param.put("categoryId", categoryId);
        param.put(PAGE_INDEX, prb.getPageIndex());
        param.put(PAGE_COUNT, prb.getLimit());
        String msg = execute(param, "GetProgramList");
        log.debug("查询节目信息<GetProgramList>: {}", msg);
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "获取节目信息失败:" + rs.getMsg();
            log.error(em);
            throw new RRException(em);
        }
        JSONObject jsonObject = JSON.parseObject(msg);
        Integer rcs = (Integer) jsonObject.get(RECORD_COUNT);
        prb.setTotal(Long.valueOf(rcs));
        JSONArray ja = jsonObject.getJSONArray("OperateProgramModelList");
        rets = ja.stream().map(jo -> JSON.toJavaObject((JSONObject) jo, ProgramTemp.class))
                .collect(Collectors.toList());
        return rets;
    }


    @Override
    public List<Category> getScreenCategoryList(int parentId) {
        List<Category> cats = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ORG_ID, org);
        param.put("parentId", 0);
        String msg = execute(param, "GetScreenCategoryList");
        log.info("终端设备分类列表数据<GetScreenCategoryList>: {}", msg);
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "终端设备分类失败:" + rs.getMsg();
            log.error(em);
            throw new RRException(em);
        }
        JSONArray ja = JSON.parseArray(msg);
        cats = ja.stream().map(jo -> JSON.toJavaObject((JSONObject) jo, Category.class)).collect(Collectors.toList());
        return cats;
    }

    @Override
    public List<BreakingNews> getBreakingNewsList(String stime, String etime, Pager prb) {
        List<BreakingNews> ret = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ORG_ID, org);
        param.put("starTime", stime);
        param.put("endTime", etime);
        param.put(PAGE_INDEX, prb.getPageIndex());
        param.put(PAGE_COUNT, prb.getLimit());
        String msg = execute(param, "BreakingNewsList");
        log.debug("紧急节目<BreakingNewsList>: {}", msg);
        ResultStatus rs = this.handleResultStatus(msg);
        if (!rs.isSuccess()) {
            String em = "获取紧急插播信息失败:" + rs.getMsg();
            log.error(em);
            throw new RRException(em);
        }
        JSONObject jsonObject = JSON.parseObject(msg);
        Integer rcs = (Integer) jsonObject.get(RECORD_COUNT);
        prb.setTotal(Long.valueOf(rcs));
        JSONArray ja = jsonObject.getJSONArray("dsisaplmList");
        ret = ja.stream().map(jo -> JSON.toJavaObject((JSONObject) jo, BreakingNews.class))
                .collect(Collectors.toList());
        return ret;
    }

    public ResultStatus handleResultStatus(String msg) {
        ResultStatus rs = new ResultStatus();
        String regexStr = "^\"?(\\-?\\d+):(.+)?";
        if (StringUtils.isBlank(msg)) {
            rs.setCode(0);
            rs.setMsg("请求结果为空!");
            return rs;
        }
        Matcher matcher = Pattern.compile(regexStr).matcher(msg);
        if (matcher.matches()) {
            String code = matcher.group(1);
            rs.setCode(Integer.parseInt(code));
            if (matcher.groupCount() >= 2) {
                rs.setMsg(matcher.group(2));
            }
        } else {
            // 没有消息代码
            rs.setCode(1);
            rs.setMsg("请求成功");
        }
        return rs;
    }



}
