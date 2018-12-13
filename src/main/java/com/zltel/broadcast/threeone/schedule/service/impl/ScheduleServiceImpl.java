package com.zltel.broadcast.threeone.schedule.service.impl;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.threeone.schedule.bean.Schedule;
import com.zltel.broadcast.threeone.schedule.dao.ScheduleMapper;
import com.zltel.broadcast.threeone.schedule.service.ScheduleService;
import com.zltel.broadcast.um.bean.SysUser;
import io.swagger.models.auth.In;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private static final Log log = LogFactory.getLog(ScheduleServiceImpl.class);
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void addSchedule(Schedule schedule) {
        scheduleMapper.insertSelective(schedule);
        addMembers(schedule, schedule.getMembers());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public int updateSchedule(Schedule schedule) {
        return scheduleMapper.updateByPrimaryKeySelective(schedule);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public int deleteSchedule(int id) {
        scheduleMapper.deleteMemberLinkBySchedule(id);
        return scheduleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Schedule getSchedule(int id) {
        return scheduleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Schedule> queryEnableSchedule(SysUser user) {
        Date timeStartFrom = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
        Integer orgId = user == null ? null : user.getOrgId();
        return scheduleMapper.selectByTime(orgId, timeStartFrom, null, null, null, null, null, 1, Integer.MAX_VALUE);
    }

    @Override
    public List<Schedule> queryThreeoneEnableSchedule(SysUser user) {
        Date timeStartFrom = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
        Integer orgId = user == null ? null : user.getOrgId();
        List<Integer> types = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
        }};
        return scheduleMapper.selectByTime(orgId, timeStartFrom, null, null, null, types, null, 1, Integer.MAX_VALUE);
    }

    @Override
    public List<Schedule> queryThreeoneCompletedSchedule(SysUser user, int pageNum, int pageSize) {
        Date timeEndTo = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
        Integer orgId = user == null ? null : user.getOrgId();
        List<Integer> types = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
        }};
        return scheduleMapper.selectByTime(orgId, null, null, null, timeEndTo, types, "desc", pageNum, pageSize);
    }

    @Override
    public Map<Integer, Object> countCompletedSchedule(SysUser user) {
        List<Schedule> schedules = queryThreeoneCompletedSchedule(user, 1, Integer.MAX_VALUE);
        Map<Integer, Object> count = new HashMap<>();
        schedules.stream().forEach(schedule -> {
            LocalDate ld = schedule.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Integer year = ld.getYear();
            Integer month = ld.getMonthValue();
            Integer type = schedule.getType();
            Map<Integer, Object> countYear = (Map<Integer, Object>) count.get(year);
            if (countYear == null) {
                countYear = new HashMap<>();
                count.put(year, countYear);
            }
            Map<Integer, Integer> countType = (Map<Integer, Integer>) countYear.get(type);
            if (countType == null) {
                countType = new HashMap<>();
                countYear.put(type, countType);
            }
            //按类型月度次数统计
            Integer c = countType.get(month);
            c = c == null ? 1 : c + 1;
            countType.put(month, c);
            //按类型年度次数统计
            c = countType.get(0);
            c = c == null ? 1 : c + 1;
            countType.put(0, c);
            //年度每月次数统计
            c = (Integer) countYear.get(month * -1);
            c = c == null ? 1 : c + 1;
            countYear.put(month * -1, c);
            //年度次数统计
            c = (Integer) countYear.get(0);
            c = c == null ? 1 : c + 1;
            countYear.put(0, c);
            //累计次数统计
            c = (Integer) count.get(0);
            c = c == null ? 1 : c + 1;
            count.put(0, c);

        });

        return count;
    }

    @Override
    public R importSchedules(MultipartFile file, SysUser user) {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (Exception e) {
            return R.error("上传文件有误: " + e.getMessage());
        }
        R r = R.ok();
        Sheet sheet = workbook.getSheetAt(0);
        int rowNum = -1;
        int correctCount = 0;
        List<Integer> errorRows = new ArrayList<>();
        for (Row row : sheet) {
            rowNum++;
            if (rowNum == 0) {
                //第一行跳过
                continue;
            }
            try {
                Schedule schedule = new Schedule();
                schedule.setName(row.getCell(0).getStringCellValue());
                schedule.setDescription(row.getCell(1).getStringCellValue());
                schedule.setType(Double.valueOf(row.getCell(2).getNumericCellValue()).intValue());
                schedule.setPlace(row.getCell(3).getStringCellValue());
                schedule.setStartTime(row.getCell(4).getDateCellValue());
                schedule.setEndTime(row.getCell(5).getDateCellValue());

                schedule.setOrgId(user.getOrgId());
                schedule.setState(1);
                scheduleMapper.insertSelective(schedule);
                correctCount++;
            } catch (Exception e) {
                //添加错误
                log.warn("添加记录失败, row: " + (rowNum + 1) + ", err: " + e.toString());
                errorRows.add(rowNum + 1);
            }

        }
        r.put("total", rowNum);
        r.put("correct", correctCount);
        r.put("errorRow", errorRows);
        return r;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void addMembers(Schedule schedule, List<Map<String, Object>> members) {
        Map<String, Object> memberLink = new HashMap<>();
        Date now = new Date(System.currentTimeMillis());
        memberLink.put("participatedDate", now);
        memberLink.put("addDate", now);
        memberLink.put("updateDate", now);
        members.stream().forEach(member -> {
            memberLink.put("userId", member.get("id"));
            memberLink.put("scheduleId", schedule.getId());
            scheduleMapper.inertMemberLink(memberLink);
        });
    }

    @Override
    public List<Map<String, Object>> queryScheduleMembers(Integer scheduleId) {
        return scheduleMapper.selectMembers(null, scheduleId);
    }

    @Override
    public List<Map<String, Object>> queryOrgMembers(Integer orgId) {
        return scheduleMapper.selectMembers(orgId, null);
    }

    @Override
    public List<Map<String, Object>> queryThreeoneParticipantSchedule(String username, int pageNum, int pageSize) {
        List<Integer> types = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
        }};
        return scheduleMapper.selectByUsername(username, types, pageNum, pageSize);
    }

    @Override
    public List<Schedule> queryLifeEnableSchedule(SysUser user) {
        Date timeStartFrom = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
        Integer orgId = user == null ? null : user.getOrgId();
        List<Integer> types = new ArrayList<Integer>() {{
            add(5);
        }};
        return scheduleMapper.selectByTime(orgId, timeStartFrom, null, null, null, types, null, 1, Integer.MAX_VALUE);
    }

    @Override
    public List<Schedule> queryLifeCompletedSchedule(SysUser user, int pageNum, int pageSize) {
        Date timeEndTo = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
        Integer orgId = user == null ? null : user.getOrgId();
        List<Integer> types = new ArrayList<Integer>() {{
            add(5);
        }};
        return scheduleMapper.selectByTime(orgId, null, null, null, timeEndTo, types, "desc", pageNum, pageSize);
    }

    @Override
    public List<Map<String, Object>> queryLifeParticipantSchedule(String username, int pageNum, int pageSize) {
        List<Integer> types = new ArrayList<Integer>() {{
            add(5);
        }};
        return scheduleMapper.selectByUsername(username, types, pageNum, pageSize);
    }
}
