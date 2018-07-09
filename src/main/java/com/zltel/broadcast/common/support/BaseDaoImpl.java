package com.zltel.broadcast.common.support;

import java.util.List;

import com.github.pagehelper.PageRowBounds;

/**
 * 基础默认查询方法实现
 * 
 * @author Wangch
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    public static final PageRowBounds DEFAULT =
            new PageRowBounds(PageRowBounds.NO_ROW_OFFSET, PageRowBounds.NO_ROW_LIMIT);

    /** 获取dao实例 **/
    public abstract BaseDao<T> getInstince();

    @Override
    public int deleteByPrimaryKey(Long deptId) {
        return this.getInstince().deleteByPrimaryKey(deptId);
    }

    @Override
    public int deleteByPrimaryKey(Integer pk) {
        return this.getInstince().deleteByPrimaryKey(pk);
    }

    @Override
    public int deleteByPrimaryKey(String pk) {
        return this.getInstince().deleteByPrimaryKey(pk);
    }

    @Override
    public T selectByPrimaryKey(Integer pk) {
        return this.getInstince().selectByPrimaryKey(pk);
    }

    @Override
    public T selectByPrimaryKey(String pk) {
        return this.getInstince().selectByPrimaryKey(pk);
    }

    @Override
    public int insert(T record) {
        return this.getInstince().insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return this.getInstince().insertSelective(record);
    }

    @Override
    public T selectByPrimaryKey(Long deptId) {
        return this.getInstince().selectByPrimaryKey(deptId);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return this.getInstince().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return this.getInstince().updateByPrimaryKey(record);
    }

    public List<T> query(T record, PageRowBounds prb) {
        if (prb == null) prb = DEFAULT;

        return this.getInstince().query(record, prb);
    }

    @Override
    public List<T> queryForList(PageRowBounds prb) {
        if (prb == null) prb = DEFAULT;
        return this.getInstince().queryForList(prb);
    }

    @Override
    public int delete(T record) {
        return this.getInstince().delete(record);
    }


}
