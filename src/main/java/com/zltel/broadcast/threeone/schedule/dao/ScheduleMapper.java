package com.zltel.broadcast.threeone.schedule.dao;

import com.zltel.broadcast.threeone.schedule.bean.Schedule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Schedule record);

    int insertSelective(Schedule record);

    Schedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Schedule record);

    int updateByPrimaryKey(Schedule record);

    List<Schedule> selectByTime(@Param("orgId") Integer orgId,
                                @Param("timeStartFrom") Date timeStartFrom,
                                @Param("timeStartTo") Date timeStartTo,
                                @Param("timeEndFrom") Date timeEndFrom,
                                @Param("timeEndTo") Date timeEndTo,
                                @Param("types") List<Integer> types,
                                @Param("turn") String turn,
                                @Param("pageNum") int pageNum,
                                @Param("pageSize") int pageSize);

    List<Map<String, Object>> selectByUsername(@Param("username") String username,
                                               @Param("types") List<Integer> types,
                                               @Param("pageNum") int pageNum,
                                               @Param("pageSize") int pageSize);

    List<Map<String, Object>> selectMembers(@Param("orgId") Integer orgId, @Param("scheduleId") Integer scheduleId);

    int inertMemberLink(Map<String, Object> memberLink);

    int updateMemberLink(Map<String, Object> memberLink);

    int deleteMemberLinkBySchedule(Integer scheduleId);

    Map<String, Object> selectSignInfo(@Param("scheduleId") int scheduleId, @Param("userId") int userId);
}