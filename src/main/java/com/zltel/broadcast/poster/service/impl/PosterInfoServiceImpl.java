package com.zltel.broadcast.poster.service.impl;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    @Override
    public List<PosterInfo> query(PosterInfo posterinfo) {
        return this.posterInfoMapper.query(posterinfo);
    }

    @Override
    public PosterInfo selectAllByPrimaryKey(Integer templateId) {
        PosterInfo pi = this.posterInfoMapper.selectByPrimaryKey(templateId);
        pi.setLayouts(this.posterLayoutsMapper.selectByPrimaryKey(templateId).getLayouts());
        return pi;
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(Integer templateId) {
        this.posterLayoutsMapper.deleteByPrimaryKey(templateId);
        return this.posterInfoMapper.deleteByPrimaryKey(templateId);
    }

    @Override
    @Transactional
    public int insert(PosterInfo record) {
        record.setTemplateId(null);
        this.posterInfoMapper.insert(record);
        PosterLayouts layouts = new PosterLayouts(record.getTemplateId(), record.getLayouts());
        return this.posterLayoutsMapper.insert(layouts);
    }

    @Override
    @Transactional
    public int insertSelective(PosterInfo record) {
        record.setTemplateId(null);
        this.posterInfoMapper.insertSelective(record);
        PosterLayouts layouts = new PosterLayouts(record.getTemplateId(), record.getLayouts());
        return this.posterLayoutsMapper.insertSelective(layouts);
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(PosterInfo record) {
        PosterLayouts layouts = new PosterLayouts(record.getTemplateId(), record.getLayouts());
        this.posterLayoutsMapper.updateByPrimaryKeyWithBLOBs(layouts);
        return this.posterInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(PosterInfo record) {
        if (StringUtils.isNotBlank(record.getLayouts())) {
            PosterLayouts layouts = new PosterLayouts(record.getTemplateId(), record.getLayouts());
            this.posterLayoutsMapper.updateByPrimaryKeySelective(layouts);
        }
        this.updateUseCategory(record);
        return this.posterInfoMapper.updateByPrimaryKeySelective(record);
    }

    private void updateUseCategory(PosterInfo record) {
        if (record.getUseCategorys() == null) return;
        PosterCategoryRelationKey key = new PosterCategoryRelationKey();
        key.setTemplateId(record.getTemplateId());
        this.posterCategoryRelationMapper.delete(key);
        for (Integer uc : record.getUseCategorys()) {
            key = new PosterCategoryRelationKey(record.getTemplateId(),uc);
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


}
