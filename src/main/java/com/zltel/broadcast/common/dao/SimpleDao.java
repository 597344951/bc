package com.zltel.broadcast.common.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 基础查询结构
 * BaseDao interface
 *
 * @author Touss
 * @date 2018/5/2
 */
@Repository
public interface SimpleDao {

    /**
     * 查询单条记录
     * @param tableName 表名
     * @param queryParam 查询条件
     * @return 返回结果
     */
    public Map<String, Object> get(@Param("tableName") String tableName, @Param("queryParam") Map<String, Object> queryParam);

    /**
     * 按条件查询
     * @param tableName
     * @param queryParam
     * @return 查询结果
     */
    public List<Map<String, Object>> query(@Param("tableName") String tableName,
                                           @Param("queryParam") Map<String, Object> queryParam);

    /**
     * 按条件分页查询记录
     * @param tableName 表名
     * @param queryParam 查询条件
     * @param pageNum 页数
     * @param pageSize 页面大小
     * @return 返回结果
     */
    public List<Map<String, Object>> query(@Param("tableName") String tableName,
                                           @Param("queryParam") Map<String, Object> queryParam,
                                           @Param("pageNum") int pageNum,
                                           @Param("pageSize") int pageSize);

    /**
     * 添加数据
     * @param tableName 表名
     * @param data 添加记录
     * @return
     */
    public int add(@Param("tableName") String tableName, @Param("data") Map<String, Object> data);

    /**
     * 更新数据
     * @param tableName 表名
     * @param data 数据
     * @param queryParam 条件
     * @return
     */
    public int update(@Param("tableName") String tableName, @Param("data") Map<String, Object> data, @Param("queryParam") Map<String, Object> queryParam);

    /**
     * 删除数据
     * @param tableName 表名
     * @param queryParam 条件
     * @return
     */
    public int delete(@Param("tableName") String tableName, @Param("queryParam") Map<String, Object> queryParam);

    /**
     * 添加多条记录
     * @param tableName 表名
     * @param data 记录
     * @return
     */
    public int adds(@Param("tableName") String tableName, @Param("data") List<Map<String, Object>> data);
}
