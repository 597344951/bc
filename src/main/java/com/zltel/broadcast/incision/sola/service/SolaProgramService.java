package com.zltel.broadcast.incision.sola.service;

import java.util.List;
import java.util.Map;

/**
 * 节目相关服务
 * SolaProgramService interface
 *
 * @author Touss
 * @date 2018/5/17
 */
public interface SolaProgramService {

    /**
     * 添加节目编辑
     * @param program
     * @return
     */
    public int addProgram(Map<String, Object> program);

    /**
     * 发布节目内容
     * @param content
     */
    public void publish(Map<String, Object> content);

    /**
     * 取消播放
     * @param pids
     * @param tids
     */
    public void cancelProgram(String pids, String tids);

    /**
     * 注册终端
     * @return
     */
    public List<Map<String, Object>> queryTerminal();

    /**
     * 组织信息
     * @return
     */
    public List<Map<String, Object>> queryOrg();

    /**
     * 操作日志
     * @param tid
     * @return
     */
    public List<Map<String, Object>> queryTerminalOperateLog(int tid);

    /**
     * 执行日志
     * @param tid
     * @return
     */
    public List<Map<String, Object>> queryTerminalExecuteLog(int tid);

    /**
     * 终端操作
     * @param tid
     * @param label
     * @param name
     * @param code
     * @return
     */
    public boolean terminalCommand(int tid, String label, String name, String code);
}
