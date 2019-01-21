package com.zltel.broadcast.meeting.room.service.impl;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.util.AdminRoleUtil;
import com.zltel.broadcast.meeting.room.bean.MeetingRoom;
import com.zltel.broadcast.meeting.room.bean.MeetingRoomOrder;
import com.zltel.broadcast.meeting.room.dao.MeetingRoomMapper;
import com.zltel.broadcast.meeting.room.dao.MeetingRoomOrderMapper;
import com.zltel.broadcast.meeting.room.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {
    @Autowired
    private MeetingRoomMapper meetingRoomMapper;
    @Autowired
    private MeetingRoomOrderMapper meetingRoomOrderMapper;

    @Override
    @Transactional
    public void addMeetingRoom(MeetingRoom room) {
        meetingRoomMapper.insertSelective(room);
    }

    @Override
    @Transactional
    public void updateMeetingRoom(MeetingRoom room) {
        meetingRoomMapper.updateByPrimaryKeySelective(room);
    }

    @Override
    @Transactional
    public void deleteMeetingRoom(int meetingRoomId) {
        meetingRoomMapper.deleteByPrimaryKey(meetingRoomId);
    }

    @Override
    public List<MeetingRoom> queryEnableMeetingRoom(int orgId, Date startDate, Date endDate) {
        if(AdminRoleUtil.isPlantAdmin()) return meetingRoomMapper.selectEnabled(null, startDate, endDate);
        return meetingRoomMapper.selectEnabled(orgId, startDate, endDate);
    }

    @Override
    public PageInfo<MeetingRoom> queryOrgMeetingRoom(int orgId, int pageNum, int pageSize) {
        if(AdminRoleUtil.isPlantAdmin()) return new PageInfo<MeetingRoom>(meetingRoomMapper.selectByOrg(null, pageNum, pageSize));
        return new PageInfo<MeetingRoom>(meetingRoomMapper.selectByOrg(orgId, pageNum, pageSize));
    }

    @Override
    @Transactional
    public void orderMeetingRoom(MeetingRoomOrder order) {
        meetingRoomOrderMapper.insertSelective(order);
    }

    @Override
    public List<MeetingRoomOrder> queryMeetingRoomOrder(int meetingRoomId) {
        return meetingRoomOrderMapper.selectByRoomId(meetingRoomId);
    }

    @Override
    @Transactional
    public void deleteMeetingRoomOrder(int meetingId) {
        meetingRoomOrderMapper.deleteByMeetingId(meetingId);
    }
}
