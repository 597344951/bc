package com.zltel.broadcast.common.support;

import java.util.List;

import com.github.pagehelper.PageRowBounds;

/**
 * 基础默认查询方法
 * 
 * @author Wangch
 */
public interface BaseDao<T> {
    int deleteByPrimaryKey(Long pk);
    int deleteByPrimaryKey(Integer pk);
    int deleteByPrimaryKey(String pk);
    
    T selectByPrimaryKey(Long pk);
    T selectByPrimaryKey(Integer pk);
    T selectByPrimaryKey(String pk);

    int insert(T record);

    int insertSelective(T record);


    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
    /**按给定条件查询**/
    List<T> query(T record,PageRowBounds prb);

    List<T> queryForList(PageRowBounds prb);
    /**根据对象参数进行删除**/
    int delete(T record);
    
    List<T> query(T record);

}
