package com.zltel.broadcast.meeting.room.service;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.meeting.room.bean.MeetingRoom;
import com.zltel.broadcast.meeting.room.bean.MeetingRoomOrder;

import java.util.Date;
import java.util.List;

public interface MeetingRoomService {
    public void addMeetingRoom(MeetingRoom room);
    public void updateMeetingRoom(MeetingRoom room);
    public void deleteMeetingRoom(int meetingRoomId);
    public List<MeetingRoom> queryEnableMeetingRoom(int orgId, Date startDate, Date endDate);
    public PageInfo<MeetingRoom> queryOrgMeetingRoom(int orgId, int pageNum, int pageSize);
    public void orderMeetingRoom(MeetingRoomOrder order);
    public List<MeetingRoomOrder> queryMeetingRoomOrder(int meetingRoomId);
    public void deleteMeetingRoomOrder(int meetingId);
}
