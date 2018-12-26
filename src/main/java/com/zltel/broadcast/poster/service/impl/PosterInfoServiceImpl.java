package com.zltel.broadcast.poster.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.poster.bean.PosterCategory;
import com.zltel.broadcast.poster.bean.PosterCategoryRelationKey;
import com.zltel.broadcast.poster.bean.PosterInfo;
import com.zltel.broadcast.poster.bean.PosterLayouts;
import com.zltel.broadcast.poster.bean.PosterSize;
import com.zltel.broadcast.poster.dao.PosterCategoryMapper;
import com.zltel.broadcast.poster.dao.PosterCategoryRelationMapper;
import com.zltel.broadcast.poster.dao.PosterInfoMapper;
import com.zltel.broadcast.poster.dao.PosterLayoutsMapper;
import com.zltel.broadcast.poster.dao.PosterSizeMapper;
import com.zltel.broadcast.poster.service.PosterCoverService;
import com.zltel.broadcast.poster.service.PosterInfoService;

@Service
public class PosterInfoServiceImpl implements PosterInfoService {
    public static final Logger logout = LoggerFactory.getLogger(PosterInfoServiceImpl.class);

    @Resource
    private PosterInfoMapper posterInfoMapper;
    @Resource
    private PosterLayoutsMapper posterLayoutsMapper;
    @Resource
    private PosterSizeMapper posterSizeMapper;
    @Resource
    private PosterCategoryMapper posterCategoryMapper;
    @Resource
    private PosterCategoryRelationMapper posterCategoryRelationMapper;

    @Resource
    private PosterCoverService posterCoverService;

    @Override
    public List<PosterInfo> query(PosterInfo posterinfo) {
        return this.posterInfoMapper.query(posterinfo);
    }

    @Override
    public PosterInfo selectAllByPrimaryKey(Integer templateId) {
        PosterInfo pi = this.posterInfoMapper.selectByPrimaryKey(templateId);
        PosterLayouts pl = this.posterLayoutsMapper.selectByPrimaryKey(templateId);
        if (pl != null) pi.setLayouts(pl.getLayouts());
        return pi;
    }

    @Override
    @Transactional
    public void deleteByPrimaryKey(Integer templateId) {
        this.posterLayoutsMapper.deleteByPrimaryKey(templateId);
        this.posterInfoMapper.deleteByPrimaryKey(templateId);
    }

    @Override
    @Transactional
    public int insert(PosterInfo record) {
        record.setTemplateId(null);
        this.posterInfoMapper.insert(record);
        String ls = record.getLayouts();
        ls = StringUtils.isNotBlank(ls) ? ls : "[]";
        PosterLayouts layouts = new PosterLayouts(record.getTemplateId(), ls);
        this.posterLayoutsMapper.insert(layouts);
        this.posterCoverService.updateCover(record);
        return record.getTemplateId();
    }

    @Override
    @Transactional
    public int insertSelective(PosterInfo record) {
        record.setTemplateId(null);
        this.posterInfoMapper.insertSelective(record);
        String ls = record.getLayouts();
        ls = StringUtils.isNotBlank(ls) ? ls : "[]";
        PosterLayouts layouts = new PosterLayouts(record.getTemplateId(), ls);
        this.posterLayoutsMapper.insertSelective(layouts);

        this.updateUseCategory(record);
        this.posterCoverService.updateCover(record);
        return record.getTemplateId();
    }

    @Override
    @Transactional
    public void updateByPrimaryKey(PosterInfo record) {
        if (StringUtils.isNotBlank(record.getLayouts())) {
            PosterLayouts layouts = new PosterLayouts(record.getTemplateId(), record.getLayouts());
            int r = this.posterLayoutsMapper.updateByPrimaryKeySelective(layouts);
            if (r == 0) this.posterLayoutsMapper.insert(layouts);
            this.posterCoverService.updateCover(record);
        }
        this.updateUseCategory(record);
        this.posterInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional
    public void updateByPrimaryKeySelective(PosterInfo record) {
        if (StringUtils.isNotBlank(record.getLayouts())) {
            PosterLayouts layouts = new PosterLayouts(record.getTemplateId(), record.getLayouts());
            int r = this.posterLayoutsMapper.updateByPrimaryKeySelective(layouts);
            if (r == 0) this.posterLayoutsMapper.insert(layouts);
            this.posterCoverService.updateCover(record);
        }
        this.updateUseCategory(record);
        this.posterInfoMapper.updateByPrimaryKeySelective(record);
    }



    private void updateUseCategory(PosterInfo record) {
        if (record.getUseCategorys() == null) return;
        PosterCategoryRelationKey key = new PosterCategoryRelationKey();
        key.setTemplateId(record.getTemplateId());
        this.posterCategoryRelationMapper.delete(key);
        for (Integer uc : record.getUseCategorys()) {
            key = new PosterCategoryRelationKey(record.getTemplateId(), uc);
            this.posterCategoryRelationMapper.insert(key);
        }
    }


    @Override
    public List<PosterSize> queryPosterSize(PosterSize record) {
        return this.posterSizeMapper.query(record);
    }

    @Override
    public List<PosterCategory> queryCategorys(Integer type) {
        PosterCategory record = new PosterCategory();
        if (type != 0) record.setType(type);
        return this.posterCategoryMapper.query(record);
    }

    @Override
    @Transactional
    public PosterInfo newPosterFromTemplateId(Integer templateId) {
        PosterInfo pi = this.posterInfoMapper.selectByPrimaryKey(templateId);
        if (pi != null) {
            pi.setPid(pi.getTemplateId().toString());
            pi.setFrom("newFromTemplate");
            pi.setLayouts(this.posterLayoutsMapper.selectByPrimaryKey(templateId).getLayouts());
        } else {
            pi = new PosterInfo();
            pi.setTitle("新创建海报");
            pi.setFrom("new");
            pi.setHeight(1080);
            pi.setWidth(1920);
        }
        this.insertSelective(pi);
        return pi;
    }

    @Override
    @Transactional
    public void updateLayoutsByPrimaryKey(PosterLayouts layouts) {
        int r = this.posterLayoutsMapper.updateByPrimaryKeySelective(layouts);
        if (r == 0) this.posterLayoutsMapper.insert(layouts);
    }

    @Override
    public List<PosterInfo> searchMetaData(String regex) {
        return this.posterInfoMapper.searchMetaData(regex);
    }

}
