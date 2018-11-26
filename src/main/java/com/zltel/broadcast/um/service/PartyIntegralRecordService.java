package com.zltel.broadcast.um.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyIntegralRecord;

public interface PartyIntegralRecordService {
	
	public enum IcChangeType {
	    ADD("加分"),
	    REDUCE("扣分");
		
		private final String name;
	    
	    private IcChangeType(String name)
	    {
	        this.name = name;
	    }

	    public String getName() {
	        return name;
	    }
	}
	
	public enum IcType {
		ACTIVE("按时参加党内活动"),
		MONEY("按时足额缴纳党费"),
		QUESTION_AND_ANSWER("知识问答"),
		WATCH_VIDEO("观看视频");
	    
	    private final String name;
	    
	    private IcType(String name)
	    {
	        this.name = name;
	    }

	    public String getName() {
	        return name;
	    }
	}
	
	int deleteByPrimaryKey(Integer pirId);

    int insert(PartyIntegralRecord record);

    int insertSelective(PartyIntegralRecord record);

    PartyIntegralRecord selectByPrimaryKey(Integer pirId);

    int updateByPrimaryKeySelective(PartyIntegralRecord record);

    int updateByPrimaryKey(PartyIntegralRecord record);
    
    /**
     * 查询积分记录
     * @param conditions
     * @return
     */
    public R queryPartyIntegralRecords(Map<String, Object> conditions, int pageNum, int pageSize);
    
    /**
     * 添加积分变更记录
     * @param ict
     * @return
     */
    public R insertPartyUserIntegralRecord(PartyIntegralRecord pir);
    
    /**
     * 自动动变更积分值
     * @param pir  积分变更信息
     * @param icType  积分变更项
     * @param changeIcType  变更方法（加分，扣分）
     * 组织id，党员id，活动id必须
     * @return
     */
    public boolean automaticIntegralRecord(PartyIntegralRecord pir, IcType icType, IcChangeType icChangeType);
}
