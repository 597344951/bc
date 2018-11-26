package com.zltel.broadcast.poster.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zltel.broadcast.common.configuration.SystemInfoConfig;
import com.zltel.broadcast.incision.sola.utils.HttpUtil;
import com.zltel.broadcast.poster.bean.PosterInfo;
import com.zltel.broadcast.poster.bean.PosterLayouts;
import com.zltel.broadcast.poster.service.PosterCoverService;
import com.zltel.broadcast.poster.service.PosterInfoService;

@Service
public class PosterCoverServiceImpl implements PosterCoverService {

    private static final Logger logout = LoggerFactory.getLogger(PosterCoverServiceImpl.class);

    @Resource
    private SystemInfoConfig sysInfo;

    @Resource
    private PosterInfoService posterInfoService;


    @Override
    public void updateCover(PosterInfo posterinfo) {
        Thread t = new Thread(() -> {
            this.doUpdateCover(posterinfo);
        }, "PosterCover-create-thread");
        t.start();
    }


    private void doUpdateCover(PosterInfo posterinfo) {
        String shotUrl = sysInfo.getMediaServerUrl().append("image/catch").toString();

        Map<String, Object> values = screenshootData(posterinfo);
        StringBuilder sb = new StringBuilder();
        values.forEach((k, v) -> {
            sb.append(k).append("=").append(v).append("&");
        });
        String fullUrl = shotUrl + "?" + sb.toString();
        logout.debug("更新{}封面, 计算后截图地址: {}", posterinfo.getTemplateId(), fullUrl);
        String result = HttpUtil.get(fullUrl, "");
        JSONObject jo = JSON.parseObject(result);
        String coverUrl = jo.getString("url");
        logout.debug("截图获取地址: {}", coverUrl);

        PosterInfo npi = new PosterInfo();
        npi.setTemplateId(posterinfo.getTemplateId());
        npi.setPreview(coverUrl);
        this.posterInfoService.updateByPrimaryKeySelective(npi);
    }

    private Map<String, Object> screenshootData(PosterInfo posterinfo) {
        String page = sysInfo.getPosterServeUrl().append("view/").append(posterinfo.getTemplateId()).toString();
        Map<String, Object> values = new HashMap<>();
        values.put("page", page);
        if (posterinfo.getWidth() > posterinfo.getHeight()) {
            double maxWidth = 800;
            double s = posterinfo.getWidth() / maxWidth;
            double height = posterinfo.getHeight() / s;
            values.put("width", (int) maxWidth);
            values.put("height", (int) height);
        } else {
            double maxHeight = 600;
            double s = posterinfo.getHeight() / maxHeight;
            double width = posterinfo.getWidth() / s;
            values.put("width", (int) width);
            values.put("height", (int) maxHeight);
        }
        return values;
    }


    @Override
    public void replaceTarget(Integer[] templateIds, String search, String rep) {
        final List<PosterInfo> infos = new ArrayList<>();
        if (templateIds == null) {
            List<PosterInfo> t = this.posterInfoService.searchMetaData(search);
            t.forEach(_pi -> {
                PosterInfo pi = this.posterInfoService.selectAllByPrimaryKey(_pi.getTemplateId());
                if (pi != null) infos.add(pi);
            });
        } else {
            Arrays.asList(templateIds).forEach(templateId -> {
                PosterInfo pi = this.posterInfoService.selectAllByPrimaryKey(templateId);
                if (pi != null) infos.add(pi);
            });
        }
        infos.forEach(pi -> {
            if (pi == null) return;
            Pattern pattern = Pattern.compile(search);

            String title = pi.getTitle();
            String newTitle = pattern.matcher(title).replaceAll(rep);
            if (!pi.getTitle().equals(newTitle)) {
                PosterInfo npi = new PosterInfo();
                npi.setTemplateId(pi.getTemplateId());
                npi.setTitle(newTitle);
                this.posterInfoService.updateByPrimaryKeySelective(npi);
                logout.debug("替换模板标题: {} -> {} ", title, newTitle);
            }


            String layouts = pi.getLayouts();
            if (StringUtils.isBlank(layouts)) return;

            List<String> fields = Arrays.asList("name", "text");
            JSONArray ja = JSON.parseArray(layouts);
            ja.forEach(obj -> {
                JSONObject jo = (JSONObject) obj;
                fields.forEach(field -> {
                    String ov = jo.getString(field);
                    if (ov == null) return;
                    String nv = pattern.matcher(ov).replaceAll(rep);
                    if (!ov.equals(nv)) {
                        jo.put(field, nv);
                        logout.debug("替换{} : {} -> {} ", field, ov, nv);
                    }
                });
            });

            String newLayouts = ja.toJSONString();
            if (!layouts.equals(newLayouts)) {
                PosterLayouts pl = new PosterLayouts(pi.getTemplateId(), newLayouts);
                this.posterInfoService.updateLayoutsByPrimaryKey(pl);
            }
        });
    }

}
