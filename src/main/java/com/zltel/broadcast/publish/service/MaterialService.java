package com.zltel.broadcast.publish.service;

import com.zltel.broadcast.um.bean.SysUser;

import java.util.List;
import java.util.Map;

/**
 * MaterialService interface
 *
 * @author Touss
 * @date 2018/5/10
 */
public interface MaterialService {

    /**
     * 素材转移
     * @param user
     * @param material
     * @param srcDir
     * @param descDir
     */
    public void transferMaterial(SysUser user, List<Map<String, Object>> material, String srcDir, String descDir);

    /**
     * 获取素材
     * @param id
     * @return
     */
    public Map<String, Object> getMaterial(int id);

    /**
     * 查询内容相关素材
     * @param contentId
     * @return
     */
    public List<Map<String, Object>> queryMaterial(int contentId);

    /**
     * 转存Ueditor内容素材文件
     * @param user
     * @param content
     * @param srcDir
     * @param descDir
     */
    public void saveUeditorMaterial(SysUser user, Map<String, Object> content, String srcDir, String descDir);
}
