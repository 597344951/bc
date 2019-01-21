package com.zltel.broadcast.meeting.room.dao;

import com.zltel.broadcast.meeting.room.bean.MeetingRoom;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MeetingRoomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MeetingRoom record);

    int insertSelective(MeetingRoom record);

    MeetingRoom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MeetingRoom record);

    int updateByPrimaryKey(MeetingRoom record);

    List<MeetingRoom> selectByOrg(@Param("orgId") Integer orgId, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<MeetingRoom> selectEnabled(@Param("orgId") Integer orgId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}