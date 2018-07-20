package com.zltel.broadcast.publish.service.impl;

import com.zltel.broadcast.common.dao.SimpleDao;
import com.zltel.broadcast.common.util.FileUtil;
import com.zltel.broadcast.publish.Constant;
import com.zltel.broadcast.publish.dao.PublishDao;
import com.zltel.broadcast.publish.service.MaterialService;
import com.zltel.broadcast.um.bean.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MaterialServiceImpl class
 *
 * @author Touss
 * @date 2018/5/10
 */
@Service
public class MaterialServiceImpl implements MaterialService {
    public static final Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);
    @Autowired
    SimpleDao simpleDao;
    @Autowired
    PublishDao publishDao;

    @Override
    public void transferMaterial(SysUser user, List<Map<String, Object>> material, String srcDir, String descDir) {
        String url, fileName, srcFile, descFile, newUrl;
        boolean isFile;
        for (Map<String, Object> m : material) {
            isFile = (boolean) m.get("isFile");
            if (!isFile) {
                continue;
            }
            url = (String) m.get("url");
            srcFile = srcDir + url;
            descFile = descDir + url;
            try {
                FileUtil.copy(srcFile, descFile, true);
            } catch (IOException e) {
                log.error(e.getMessage());
                log.warn("素材文件同步失败，失败文件：" + url + ",错误信息：" + e);
            }
        }
    }

    @Override
    public Map<String, Object> getMaterial(int id) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("material_id", id);
        return simpleDao.get("material", queryParam);
    }

    @Override
    public List<Map<String, Object>> queryMaterial(int contentId) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("relate_content_id", contentId);
        return simpleDao.query("material", queryParam);
    }

    @Override
    public void saveUeditorMaterial(SysUser user, Map<String, Object> content, String srcDir, String descDir) {

        List<Map<String, Object>> materials = (List<Map<String, Object>>) content.get("material");
        Document doc = Jsoup.parse((String) content.get("templateText"));
        //图片
        Elements imgs = doc.getElementsByTag("img");
        String src, fileName, alt;
        String srcFile, descFile, descFileUrl;
        Map<String, Object> material;
        Date addDate = new Date();
        for (Element img : imgs) {
            src = img.attr("src");
            alt = img.attr("alt");
            fileName = src.substring(src.lastIndexOf("/") + 1, src.length());
            alt = StringUtils.isNotEmpty(alt) ? alt : fileName;
            // 添加记录
            material = new HashMap<String, Object>();
            material.put("isFile", true);
            material.put("type", Constant.MATERIAL_TYPE_IMAGE);
            material.put("name", alt);
            material.put("url", src);
            material.put("coverUrl", src);
            //回填到素材
            materials.add(material);
            img.remove();
        }
        //文字
        /*Elements ps = doc.getElementsByTag("p");
        for (int i = 0; i < ps.size(); i++) {
            Element p = ps.get(i);
            String name = p.attr("id");
            if(StringUtils.isEmpty(name)) {
                continue;
            }
            String inner = p.html();
            material = new HashMap<String, Object>();
            material.put("isFile", false);
            material.put("type", Constant.MATERIAL_TYPE_TEXT);
            material.put("name", StringUtils.isNotEmpty(name) ? name : "段落" + i);
            material.put("content", inner);
            materials.add(material);
        }*/
        material = new HashMap<String, Object>();
        material.put("isFile", false);
        material.put("type", Constant.MATERIAL_TYPE_TEXT);
        material.put("name", "节目文字内容");
        material.put("content", doc.body().html());
        materials.add(material);
    }
}
