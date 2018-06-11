package com.zltel.broadcast.eventplan.bean;

public class EventList extends EventUser{
    private Integer eventId;
  
    private String source;
    private Integer status;
    
    private String[] froms;
    private String[] prioritys;
    private String[] statuss;
    
    public static EventList from(EventUser eu) {
        EventList el = new EventList();
        el.setTitle(eu.getTitle());
        el.setStime(eu.getStime());
        el.setEtime(eu.getEtime());
        el.setPriority(eu.getPriority());
        el.setRemark(eu.getRemark());
        el.setOrgId(eu.getOrgId());
        el.setSource("user");
        el.setStatus(0);
        return el;
    }
    

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    
 

    /**
     * 获取source  
     * @return the source
     */
    public String getSource() {
        return source;
    }


    /**
     * 设置source  
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取froms  
     * @return the froms
     */
    public String[] getFroms() {
        return froms;
    }

    /**
     * 设置froms  
     * @param froms the froms to set
     */
    public void setFroms(String[] froms) {
        this.froms = froms;
    }

    /**
     * 获取prioritys  
     * @return the prioritys
     */
    public String[] getPrioritys() {
        return prioritys;
    }

    /**
     * 设置prioritys  
     * @param prioritys the prioritys to set
     */
    public void setPrioritys(String[] prioritys) {
        this.prioritys = prioritys;
    }

    /**
     * 获取statuss  
     * @return the statuss
     */
    public String[] getStatuss() {
        return statuss;
    }

    /**
     * 设置statuss  
     * @param statuss the statuss to set
     */
    public void setStatuss(String[] statuss) {
        this.statuss = statuss;
    }

    
    
}