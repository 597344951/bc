package com.zltel.broadcast.common.logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 本地日志结构 LogBean class
 *
 * @author Touss
 * @date 2018/5/3
 */
public class LogBean {
    /** 日志等级 */
    private String level;
    /** 日志产生 位置,class + method */
    private String method;
    /** 时间 */
    private Date date;
    /** 日志内容 */
    private String msg;

    /** 用户名 */
    private String username;
    /** 操作名称 */
    private String operation;
    
    /**日志类别**/
    private int type;
    /** 访问ip **/
    private String ip;
    /** 方法执行时间 **/
    private long costTime = -1;
    /**请求参数**/
    private String params;

    public LogBean() {}

    public LogBean(String level, String clazz, Date date, String msg,  
            String username, String operation) {
        this.level = level;
        this.method = clazz;
        this.date = date;
        this.msg = msg; 
        this.username = username;
        this.operation = operation;
    }

    

    /**
     * 获取method  
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置method  
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取operation  
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 设置operation  
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * 获取type  
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * 设置type  
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }



    

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
 

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

     
    /**
     * 获取ip
     * 
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip
     * 
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取costTime  
     * @return the costTime
     */
    public long getCostTime() {
        return costTime;
    }

    /**
     * 设置costTime  
     * @param costTime the costTime to set
     */
    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    /**
     * 获取params  
     * @return the params
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置params  
     * @param params the params to set
     */
    public void setParams(String params) {
        this.params = params;
    }
 

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("level", level);
        map.put("method", method);
        map.put("date", date);
        map.put("msg", msg);
        map.put("username", username);
        map.put("operation", operation);
        map.put("ip", ip);
        map.put("costTime", costTime);
        map.put("type", type);
        map.put("params", params);
        return map;
    }
}
