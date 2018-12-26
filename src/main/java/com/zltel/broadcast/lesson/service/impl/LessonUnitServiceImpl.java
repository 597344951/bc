package com.zltel.broadcast.lesson.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.util.TreeNodeCreateUtil;
import com.zltel.broadcast.lesson.bean.LessonCategory;
import com.zltel.broadcast.lesson.bean.LessonSection;
import com.zltel.broadcast.lesson.bean.LessonUnit;
import com.zltel.broadcast.lesson.dao.LessonCategoryDAO;
import com.zltel.broadcast.lesson.dao.LessonUnitDAO;
import com.zltel.broadcast.lesson.service.LessonUnitService;

@Service
public class LessonUnitServiceImpl implements LessonUnitService {

    @Resource
    private LessonUnitDAO lessonUnitDao;

    @Resource
    private LessonCategoryDAO categoryDao;

    @Transactional
    @Override
    public int insertSelective(LessonUnit record) {
        return this.lessonUnitDao.insertSelective(record);
    }

    @Override
    public LessonUnit selectByPrimaryKey(Integer id) {
        return this.lessonUnitDao.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(LessonUnit record) {
        return this.lessonUnitDao.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Integer pk) {
        return this.lessonUnitDao.deleteByPrimaryKey(pk);
    }

    @Override
    public List<LessonUnit> query(LessonUnit record) {
        return this.lessonUnitDao.query(record);
    }

    @Override
    public List<LessonUnit> queryCategoryRelatedData(LessonUnit record,Pager pager) {
        Integer categoryId = record.getCategoryId();
        record.setCategoryId(null);
        if (categoryId != null) {
            List<LessonCategory> categorys = this.categoryDao.query(null);
            // 过滤出此目录下所有的 目录
            LessonCategory target = new LessonCategory();
            target.setCategoryId(categoryId);
            List<LessonCategory> cl = TreeNodeCreateUtil.getAllChildrenList(target, categorys,
                    LessonCategory::getCategoryId, LessonCategory::getParent);
            List<Integer> categoryIds = cl.stream().map(LessonCategory::getCategoryId).collect(Collectors.toList());
            record.setCategoryChildrenIds(categoryIds);
        }
        Page<LessonUnit> page = PageHelper.startPage(pager.getPageIndex(), pager.getLimit());
        List<LessonUnit> list = this.lessonUnitDao.queryRelatedData(record);
        list.forEach(lu -> {
            List<LessonSection> tree = TreeNodeCreateUtil.toTree(lu.getLessonList(), LessonSection::getLessonId,
                    LessonSection::getParent, LessonSection::setChildren);
            lu.setLessonTree(tree);
        });
        pager.setTotal(page.getTotal());
        return list;
    }

}
