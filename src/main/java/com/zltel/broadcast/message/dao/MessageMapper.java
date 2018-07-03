package com.zltel.broadcast.message.dao;

import com.zltel.broadcast.message.bean.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    public List<Message> queryPendingByUser(@Param("userId") int userId, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
    public List<Message> queryNoticeByUser(@Param("userId") int userId, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
    public int updateStateBySource(Message message);

}