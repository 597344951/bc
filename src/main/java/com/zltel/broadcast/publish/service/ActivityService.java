package com.zltel.broadcast.publish.service;

import com.zltel.broadcast.um.bean.SysUser;

import java.util.List;
import java.util.Map;

/**
 * ActivityService interface
 *
 * @author Touss
 * @date 2018/5/25
 */
public interface ActivityService {
    /**
     * 活动附加信息
     * @param contentId
     * @return
     */
    public Map<String, Object> getActivityAddition(int contentId);

    /**
     * 完善活动附加信息
     * @param user
     * @param addition
     */
    public void completeActivityAddition(SysUser user, Map<String, Object> addition);

    /**
     * 参与活动
     * @param contentId
     * @param user
     */
    public void participate(int contentId, SysUser user);

    /**
     * 更新参加人活动情况
     * @param sequel
     */
    public void updateParticipantSequel(Map<String, Object> sequel);

    /**
     * 更新参加人活动情况
     * @param sequels
     */
    public void updateParticipantSequel(List<Map<String, Object>> sequels);

    /**
     * 添加积分
     * @param user
     * @param moving
     * @param sourceType
     * @param reason
     */
    public void addIntegral(SysUser user, int moving, String sourceType, String reason);

    /**
     * 参加人员
     * @param contentId
     * @return
     */
    public List<Map<String, Object>> queryParticipant(int contentId);
}
