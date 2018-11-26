package com.zltel.broadcast.publish.service;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.publish.bean.Silhouette;

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
     *
     * @param contentId
     * @return
     */
    public Map<String, Object> getActivityAddition(int contentId);

    /**
     * 完善活动附加信息
     *
     * @param user
     * @param addition
     */
    public void completeActivityAddition(Map<String, Object> user, Map<String, Object> addition);

    /**
     * 参与活动
     *
     * @param contentId
     * @param user
     */
    public void participate(int contentId, Map<String, Object> user);

    /**
     * 更新参加人活动情况
     *
     * @param sequel
     */
    public void updateParticipantSequel(Map<String, Object> sequel);

    /**
     * 更新参加人活动情况
     *
     * @param sequels
     */
    public void updateParticipantSequel(List<Map<String, Object>> sequels);

    /**
     * 添加积分
     *
     * @param user
     * @param moving
     * @param sourceType
     * @param reason
     */
    public void addIntegral(Map<String, Object> user, int moving, String sourceType, String reason);

    /**
     * 参加人员
     *
     * @param contentId
     * @return
     */
    public List<Map<String, Object>> queryParticipant(int contentId);

    /**
     * 已结束活动
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> queryFinishedActivity(int pageNum, int pageSize);

    /**
     * 活动剪影操作
     */
    public int addSilhouette(Silhouette silhouette);

    public int addSilhouette(Map<String, Object> silhouette);

    public int deleteSilhouette(int silhouetteId);

    public int updateSilhouette(Silhouette silhouette);

    public Silhouette getSilhouette(int silhouetteId);

    public PageInfo<Silhouette> querySilhouette(int pageNum, int pageSize);

    public Map<String, Object> mapGetSilhouette(int id);

    public PageInfo<Map<String, Object>> queryGallery(int pageNum, int pageSize);

    public PageInfo<Map<String, Object>> queryXjdx(int pageNum, int pageSize);

    public PageInfo<Map<String, Object>> queryFlashing(int pageNum, int pageSize);


    public int delete(int id);
}
