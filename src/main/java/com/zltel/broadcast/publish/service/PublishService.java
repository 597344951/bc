package com.zltel.broadcast.publish.service;

import com.zltel.broadcast.um.bean.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 内容发布
 * PublishService interface
 *
 * @author Touss
 * @date 2018/5/7
 */
public interface PublishService {

    /**
     * 创建内容
     * @param user
     * @param content
     * @return
     */
    public Map<String, Object> create(SysUser user, Map<String, Object> content);

    /**
     * 流程进行
     * @param contentId
     */
    public void doNext(int contentId);

    /**
     * 获取发布流程
     * @param contentTypeId 内容类型
     * @return 发布流程
     */
    public List<Map<String, Object>> getProcess(int contentTypeId);

    /**
     * 获取内容类型
     * @return 内容类型
     */
    public List<Map<String, Object>> getContentType();

    /**
     * 审核
     * @param user 审核人
     * @param operate 审核操作
     * @param contentId 待审核内容
     * @param contentTypeId 内容类型
     * @param opinion 意见
     */
    public void verify(SysUser user, int operate, String opinion, int contentId, int contentTypeId);

    /**
     * 在编辑提交
     * @param user
     * @param contentId
     * @param snapshot
     */
    public void moreEditCommit(SysUser user, int contentId, String snapshot);

    /**
     * 在编辑开始
     * @param user
     * @param contentId
     * @return
     */
    public int moreEditStart(SysUser user, int contentId);

    /**
     * 获取在编辑人员
     * @param contentId
     * @return
     */
    public List<Map<String, Object>> getMoreEditUser(int contentId);

    /**
     * 获取内容
     * @param contentId
     * @return
     */
    public Map<String, Object> getContent(int contentId);

    /**
     * 正在进行的内容
     * @param user
     * @return
     */
    public List<Map<String, Object>> queryProcessContent(SysUser user);

    /**
     * 正在显示的内容
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> queryPublishingContent(int pageNum, int pageSize);

    /**
     * 下架内容
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> queryPublishedContent(int pageNum, int pageSize);

    /**
     * 废弃内容
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> queryDiscardContent(int pageNum, int pageSize);

    /**
     * 获取流程状态
     * @param contentId
     * @return
     */
    public Map<Integer, List<Map<String, Object>>> getProcessState(int contentId);

    /**
     * 获取审核人员
     * @param contentId
     * @return
     */
    public List<Map<String, Object>> getExamineUser(int contentId);

    /**
     * 流程切换
     * @param contentId
     * @param processItemId
     */
    public void changeProcess(int contentId, int processItemId);

    /**
     * 添加进度状态
     * @param contentId
     * @param userId
     * @param processItemId
     * @param msg
     * @param remark
     */
    public void addProcessState(int contentId, int userId, int processItemId, String msg, String remark);

    /**
     * 废弃内容
     * @param user
     * @param contentId
     */
    public void discard(SysUser user, int contentId);

    /**
     * 发布
     * @param user
     * @param contentId
     * @param period
     */
    public void publish(SysUser user, int contentId, Map<String, Object> period);

    /**
     * 下架
     * @param user
     * @param contentId
     */
    public void offline(SysUser user, int contentId);

    /**
     * 下架
     * @return
     */
    public int offline();

    /**
     * 显示状态进度
     * @param contentTypeId
     * @param contentId
     * @return
     */
    public Map<String, Object> getShowProcessState(int contentTypeId, int contentId);

    /**
     * 预发布到终端
     * @param contentId
     * @return
     */
    public List<Map<String, Object>> queryPublishTerminal(int contentId);

    /**
     * 审核超时处理
     */
    public void verifyTimeout();
}
