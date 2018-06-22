package com.zltel.broadcast.incision.sola.service.impl;

import com.alibaba.fastjson.JSON;
import com.zltel.broadcast.incision.sola.service.SolaProgramService;
import com.zltel.broadcast.incision.sola.utils.DESUtil;
import com.zltel.broadcast.incision.sola.utils.HttpUtil;
import com.zltel.broadcast.incision.sola.utils.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SolaProgramServiceImpl class
 *
 * @author Touss
 * @date 2018/5/17
 */
@Service
public class SolaProgramServiceImpl implements SolaProgramService {
    private final Log log = LogFactory.getLog(SolaProgramServiceImpl.class);

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
        program.put("OrgId", org);

        String ret = execute(program, "AddProgram");

        if(ret==null || ret.contains("-1") || ret.contains("-2")) {
            log.error("添加节目编辑失败：" + ret);
            throw new RuntimeException("添加节目编辑失败");
        }
        return Integer.parseInt(ret.replaceAll("\"", ""));
    }

    @Override
    public void publish(Map<String, Object> content) {
        List<Map<String, Object>> terminals = (List<Map<String, Object>>) content.get("terminals");
        String ts = terminals.stream().map(terminal -> terminal.get("terminal_id").toString()).collect(Collectors.joining(","));
        Map<String, Object> program = new HashMap<String, Object>();
        program.put("OrgId", org);
        program.put("screenIds", ts);
        program.put("programIds", content.get("snapshot"));
        program.put("activeStartTime", content.get("start_date"));//开始日期
        program.put("activeEndTime", content.get("end_date"));//结束日期
        program.put("times", content.get("period"));//时间段
        program.put("weeks","0,1,2,3,4,5,6");//星期几
        program.put("programPlayType", 1);//节目播放类型：0为计划节目播放；1为轮播节目播放
        program.put("publishTimeType", 1);//发布时间类型：0为作息时间发布；1为计划时间发布
        program.put("delayTime", 0);//作息时间发布时，延后播放时间：单位分钟
        program.put("playTime", 0);//作息时间发布时，播放时间长度：单位分钟
        program.put("isDelete", 0);//是否删除过期节目
        String msg = execute(program, "PublishProgram");

        if(msg.contains("1")) {
            log.info("成功发布节目：" + content.get("title"));
        } else {
            log.error(msg);
            throw new RuntimeException("发布失败：" + msg);
        }
    }

    @Override
    public void cancelProgram(String pids, String tids) {
        Map<String, Object> program = new HashMap<String, Object>();
        program.put("programIds", pids);
        program.put("screenIds", tids);
        program.put("OrgId", org);

        String msg = execute(program, "CancelPublishProgram");
        if(msg.contains("1")) {
            log.info("取消播放成功..");
        } else {
            log.error("取消播放失败：" + msg);
            throw new RuntimeException("取消播放失败：" + msg);
        }
    }

    @Override
    public List<Map<String, Object>> queryTerminal() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("OrgId", org);
        param.put("categoryId", 0);
        param.put("pageIndex", 1);
        param.put("pageCount", Integer.MAX_VALUE);
        String msg = execute(param, "GetScreenList");
        if(msg.contains("-1:发生错误") || msg.contains("-2:你没有权限访问")) {
            log.error("拉取终端信息失败：" + msg);
            return null;
        } else {
            Map map = (Map) JSON.parse(msg);
            return (List<Map<String, Object>>) map.get("DISSchoolClassScreenModelList");
        }
    }

    @Override
    public List<Map<String, Object>> queryTerminalProgram(int tid) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("OrgId", org);
        param.put("screenId", tid);
        String msg = execute(param, "GetScreenProgramList");
        if(msg.contains("-1:发生错误") || msg.contains("-2:你没有权限访问") || msg.contains("0:执行失败")) {
            log.error("拉取终端操作日志失败：" + msg);
            return null;
        }
        return (List<Map<String, Object>>) JSON.parse(msg);
    }

    @Override
    public List<Map<String, Object>> queryOrg() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("pageIndex", 1);
        param.put("pageCount", Integer.MAX_VALUE);
        String msg = execute(param, "GetOrgList");
        if(msg.contains("-1:发生错误") || msg.contains("-2:你没有权限访问")) {
            log.error("拉取组织信息失败：" + msg);
            return null;
        }
        Map map = (Map) JSON.parse(msg);
        return (List<Map<String, Object>>) map.get("OOMList");
    }

    @Override
    public List<Map<String, Object>> queryTerminalOperateLog(int tid) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("OrgId", org);
        param.put("screenId", tid);
        param.put("pageIndex", 1);
        param.put("pageCount", Integer.MAX_VALUE);
        String msg = execute(param, "GetScreenOperateLogList");
        if(msg.contains("-1:发生错误") || msg.contains("-2:你没有权限访问")) {
            log.error("拉取终端操作日志失败：" + msg);
            return null;
        }
        Map map = (Map) JSON.parse(msg);
        return (List<Map<String, Object>>) map.get("DISOperationLogModelList");
    }

    @Override
    public List<Map<String, Object>> queryTerminalExecuteLog(int tid) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("OrgId", org);
        param.put("screenId", tid);
        param.put("pageIndex", 1);
        param.put("pageCount", Integer.MAX_VALUE);
        String msg = execute(param, "GetScreenExecuteLogList");
        if(msg.contains("-1:发生错误") || msg.contains("-2:你没有权限访问")) {
            log.error("拉取终端执行日志失败：" + msg);
            return null;
        }
        Map map = (Map) JSON.parse(msg);
        return (List<Map<String, Object>>) map.get("DISOperationLogModelList");
    }

    @Override
    public boolean terminalCommand(int tid, String label, String name, String code) {
        Map<String, Object> command = new HashMap<String, Object>();
        command.put("OrgId", org);
        command.put("screenId", tid);
        command.put("commandName", label);
        command.put("command", name);
        command.put("commandContent", code);
        String msg = execute(command, "SendScreenCommand");
        if(msg.contains("1:执行成功")) {
            return true;
        } else {
            log.error("操作执行失败：" + msg);
            return false;
        }

    }

    private String execute(Map<String, Object> program, String serviceName) {
        Map<String, Object> access = new HashMap<String, Object>();
        access.put("ServiceName", serviceName);
        access.put("Date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date()));
        access.put("LoginName", loginname);
        access.put("LoginPwd", password);

        String sign = DESUtil.encode(JsonUtils.jsonParams(access));

        program.put("ServiceName", serviceName);
        program.put("Sign", sign);

        return HttpUtil.post(url, "data=" + JsonUtils.serialization(program));
    }
}
