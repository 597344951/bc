package com.zltel.broadcast.meeting.room.dao;

import com.zltel.broadcast.meeting.room.bean.MeetingRoomOrder;

import java.util.List;

public interface MeetingRoomOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MeetingRoomOrder record);

    int insertSelective(MeetingRoomOrder record);

    MeetingRoomOrder selectByPrimaryKey(Integer id);

    List<MeetingRoomOrder> selectByRoomId(Integer meetingRoomId);

    int updateByPrimaryKeySelective(MeetingRoomOrder record);

    int updateByPrimaryKey(MeetingRoomOrder record);

    int deleteByMeetingId(Integer meetingId);
}