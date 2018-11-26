package com.zltel.broadcast.poster.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.poster.bean.PosterCategory;
import com.zltel.broadcast.poster.bean.PosterCategoryRelationKey;
import com.zltel.broadcast.poster.dao.PosterCategoryMapper;
import com.zltel.broadcast.poster.dao.PosterCategoryRelationMapper;
import com.zltel.broadcast.poster.service.PosterCategoryService;

@Service
public class PosterCategoryServiceImpl implements PosterCategoryService {

    @Resource
    private PosterCategoryMapper mapper;
    @Resource
    private PosterCategoryRelationMapper categoryRelationMapper;

    @Override
    public List<PosterCategory> query(PosterCategory record) {
        return this.mapper.query(record);
    }

    @Override
    public PosterCategory selectByPrimaryKey(Integer categoryId) {
        return this.mapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public void deleteByPrimaryKey(Integer categoryId) {
        this.mapper.deleteByPrimaryKey(categoryId);
        // 刪除 海報关系
        PosterCategoryRelationKey rk = new PosterCategoryRelationKey();
        rk.setCategoryId(categoryId);
        this.categoryRelationMapper.delete(rk);
    }

    @Override
    public int insert(PosterCategory record) {
        return this.mapper.insert(record);
    }

    @Override
    public int insertSelective(PosterCategory record) {
        return this.mapper.insertSelective(record);
    }

    @Override
    public void updateByPrimaryKey(PosterCategory record) {
        this.mapper.updateByPrimaryKey(record);
    }

    @Override
    public void updateByPrimaryKeySelective(PosterCategory record) {
        this.mapper.updateByPrimaryKeySelective(record);
    }

}
