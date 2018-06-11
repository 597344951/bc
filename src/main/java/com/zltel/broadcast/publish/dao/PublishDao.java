package com.zltel.broadcast.publish.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * PublishDao interface
 *
 * @author Touss
 * @date 2018/5/11
 */
@Repository
public interface PublishDao {
    /**
     * 根据流程查询内容
     * @param processes
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> queryContentByProcess(@Param("processes") List<Integer> processes, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    /**
     * 内容进度内容
     * @param contentId
     * @return
     */
    public List<Map<String, Object>> queryProcessState(@Param("contentId") int contentId);

    /**
     * 发布步骤
     * @param contentTypeId
     * @return
     */
    public List<Map<String, Object>> queryProcess(@Param("contentTypeId") int contentTypeId);

    /**
     * 更新模板内容
     * @param template
     * @param id
     */
    public void updateTemplate(@Param("template") String template, @Param("id") int id);

    /**
     * 更新编辑ID
     * @param snapshot
     * @param id
     */
    public void updateSnapshot(@Param("snapshot") String snapshot, @Param("id") int id);

    /**
     * Get 内容
     * @param id
     * @return
     */
    public Map<String, Object> get(@Param("id") int id);

    /**
     * 修改为下线状态
     * @return
     */
    public int offline();
}
