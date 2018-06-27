package com.zltel.broadcast.incision.sola.bean;

/**
 * 分类信息
 * 
 * @author wangch
 *
 */
public class Category {
    /****/
    private String pkId;
    private String name;
    private String parentId;
    /** 显示顺序 **/
    private String showOrder;
    
    
    
    public String getPkId() {
        return pkId;
    }
    public void setPkId(String pkId) {
        this.pkId = pkId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getShowOrder() {
        return showOrder;
    }
    public void setShowOrder(String showOrder) {
        this.showOrder = showOrder;
    }

     

}
