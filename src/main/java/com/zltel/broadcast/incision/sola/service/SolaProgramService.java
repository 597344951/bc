package com.zltel.broadcast.incision.sola.service;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.incision.sola.bean.BreakingNews;
import com.zltel.broadcast.incision.sola.bean.Category;
import com.zltel.broadcast.incision.sola.bean.Command;
import com.zltel.broadcast.incision.sola.bean.ExecuteLog;
import com.zltel.broadcast.incision.sola.bean.OptLog;
import com.zltel.broadcast.incision.sola.bean.ProgramTemp;
import com.zltel.broadcast.incision.sola.bean.PubedProgram;
import com.zltel.broadcast.incision.sola.bean.ResultStatus;
import com.zltel.broadcast.incision.sola.bean.Screen;

/**
 * 节目相关服务 SolaProgramService interface
 *
 * @author Touss
 * @date 2018/5/17
 */
public interface SolaProgramService {

    /**
     * 添加节目编辑
     * 
     * @param program
     * @param type
     * @return
     */
    public int addProgram(Map<String, Object> program, int type);

    /**
     * 发布节目内容
     * 
     * @param content
     */
    public void publish(Map<String, Object> content);

    /**
     * 取消播放
     * 
     * @param pids
     * @param tids
     */
    public void cancelProgram(String pids, String tids);

    /**
     * 删除节目
     * @param programId
     */
    public void deleteProgram(int programId);

    /**
     * 终端
     * 
     * @return
     * @junit {@link com.zltel.broadcast.incision.sola.service.SolaProgramServiceTest#testQueryTerminal()}
     */
    public List<Map<String, Object>> queryTerminal();

    /**
     * 组织信息
     * 
     * @return
     */
    public List<Map<String, Object>> queryOrg();

    /**
     * 获取终端
     * 
     * @param categoryId 目录id ,0 :所有
     * @param pager 分页对象
     * @return
     * @junit {@link com.zltel.broadcast.incision.sola.service.SolaProgramServiceTest#testGetScreenList()}
     */
    public List<Screen> getScreenList(int categoryId, Pager pager);

    /**
     * 终端操作
     * 
     * @param tid
     * @param label
     * @param name
     * @param code
     * @return
     */
    public boolean terminalCommand(int tid, String label, String name, String code);
    /**
     * 执行终端操作
     * @param cmd
     * @return
     */
    public boolean terminalCommand(Command cmd);

    /**
     * 获取终端发布节目信息
     * 
     * @param tid
     * @return
     * @junit {@link com.zltel.broadcast.incision.sola.service.SolaProgramServiceTest#testGetScreenProgramList()}
     */
    public List<PubedProgram> getScreenProgramList(int tid);

    /**
     * 获取终端操作日志
     * 
     * @param tid 终端id
     * @param prb 分页信息
     * @return
     * @junit {@link com.zltel.broadcast.incision.sola.service.SolaProgramServiceTest#testGetScreenOperateLogList()}
     */
    public List<OptLog> getScreenOperateLogList(int tid, Pager prb);

    /**
     * 获取终端执行日志
     * 
     * @param tid 终端id
     * @param prb 分页信息
     * @return
     * @junit {@link com.zltel.broadcast.incision.sola.service.SolaProgramServiceTest#testGetScreenExecuteLogList()}
     */
    public List<ExecuteLog> getScreenExecuteLogList(int tid, Pager prb);

    /**
     * 获取节目分类列表
     * 
     * @param parentId 上一级目录id
     * @return
     * @junit {@link com.zltel.broadcast.incision.sola.service.SolaProgramServiceTest#testGetProgramCategoryList()}
     */
    public List<Category> getProgramCategoryList(int parentId);

    /**
     * 获取节目模板分类列表
     * 
     * @return 目录
     * @junit {@link com.zltel.broadcast.incision.sola.service.SolaProgramServiceTest#testGetProgramCategoryMouldList()}
     */
    public List<Category> getProgramCategoryMouldList();

    /**
     * 获取单位节目列表、获取单位模板列表
     * 
     * @param categoryId 节目/模版 分类id
     * @param prb 分页对象
     * @return
     * @junit {@link com.zltel.broadcast.incision.sola.service.SolaProgramServiceTest#testGetProgramList()}
     */
    public List<ProgramTemp> getProgramList(int categoryId, Pager prb);

    /**
     * 获取终端设备分类
     * 
     * @param parentId 上一级目录id
     * @return
     * @junit {@link com.zltel.broadcast.incision.sola.service.SolaProgramServiceTest#testGetScreenCategoryList()}
     */
    public List<Category> getScreenCategoryList(int parentId);

    /**
     * 获取紧急插播节目列表
     * 
     * @param stime
     * @param etime
     * @param prb
     * @return
     * @junit {@link com.zltel.broadcast.incision.sola.service.SolaProgramServiceTest#testGetBreakingNewsList()}
     */
    public List<BreakingNews> getBreakingNewsList(String stime, String etime, Pager prb);

    /**
     * 处理返回结果消息
     * 
     * @param msg 消息返回原始内容
     * @return
     * @junit {@link com.zltel.broadcast.incision.sola.service.SolaProgramServiceTest#testHandleResultStatus()}
     */
    public ResultStatus handleResultStatus(String msg);

}
