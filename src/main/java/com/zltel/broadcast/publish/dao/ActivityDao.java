package com.zltel.broadcast.publish.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ActivityDao interface
 *
 * @author Touss
 * @date 2018/5/25
 */
@Repository
public interface ActivityDao {

    /**
     * 活动信息
     * @param contentId
     * @return
     */
    public Map<String, Object> getActivityAdditionByContentId(@Param("contentId") int contentId);

    /**
     * 活动信息
     * @param id
     * @return
     */
    public Map<String, Object> getActivityAdditionById(@Param("id") int id);

    /**
     * 更新活动信息
     * @param addition
     * @return
     */
    public int updateActivityAddition(@Param("addition") Map<String, Object> addition);

    /**
     * 添加参与者
     * @param participant
     * @return
     */
    public int addParticipant(@Param("participant") Map<String, Object> participant);

    /**
     * 参与人
     * @param id
     * @return
     */
    public Map<String, Object> getParticipantById(@Param("id") int id);

    /**
     * 参与人
     * @param contentId
     * @param userId
     * @return
     */
    public Map<String, Object> getParticipantByContentId(@Param("contentId") int contentId, @Param("userId") int userId);

    /**
     * 活动参与信息更新
     * @param participant
     * @return
     */
    public int updateParticipant(@Param("participant") Map<String, Object> participant);

    /**
     * 添加积分动态
     * @param integralMoving
     * @return
     */
    public int addIntegralMoving(@Param("integralMoving") Map<String, Object> integralMoving);

    /**
     * 活动参加人员
     * @param contentId
     * @return
     */
    public List<Map<String, Object>> queryParticipant(@Param("contentId") int contentId);
}
