package com.zltel.broadcast.lesson.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.lesson.bean.LessonCategory;
import com.zltel.broadcast.lesson.dao.LessonCategoryDAO;
import com.zltel.broadcast.lesson.service.LessonCategoryService;

@Service
public class LessonCategoryServiceImpl implements LessonCategoryService {

    @Resource
    private LessonCategoryDAO categoryDao;

    @Override
    public int insertSelective(LessonCategory record) {
        return this.categoryDao.insertSelective(record);
    }

    @Override
    public LessonCategory selectByPrimaryKey(Integer id) {
        return this.categoryDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(LessonCategory record) {
        return this.categoryDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer pk) {
        return this.categoryDao.deleteByPrimaryKey(pk);
    }

    @Override
    public List<LessonCategory> query(LessonCategory record) {
        return this.categoryDao.query(record);
    }

}
