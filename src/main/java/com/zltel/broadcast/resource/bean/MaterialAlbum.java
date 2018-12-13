package com.zltel.broadcast.resource.bean;

import com.zltel.broadcast.um.bean.SysUser;

public class MaterialAlbum {
    private Integer albumId;

    private String name;

    private String remark;

    private Integer orgid;

    private Integer uid;

    private Integer parent;

    private Integer orderNum;

    private Boolean builtin;
    private String icon;
    /**用作学习中心素材**/
    private Boolean learnResource;
    
    /**关键词**/
    private String keyword;
    /** 对应数量 **/
    private Integer count;
    /**审核通过，未通过**/
    private Boolean verify;
    /**未审核**/
    private Boolean noVerify;
    /**类型**/
    private String type;

    public MaterialAlbum() {}

    public MaterialAlbum(Integer orgid, Integer uid) {
        super();
        this.orgid = orgid;
        this.uid = uid;
    }

    public MaterialAlbum(SysUser user) {
        super();
        this.orgid = user.getOrgId();
        this.uid = user.getUserId();
    }



    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getOrgid() {
        return orgid;
    }

    public void setOrgid(Integer orgid) {
        this.orgid = orgid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Boolean getBuiltin() {
        return builtin;
    }

    public void setBuiltin(Boolean builtin) {
        this.builtin = builtin;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }

    public Boolean getNoVerify() {
        return noVerify;
    }

    public void setNoVerify(Boolean noVerify) {
        this.noVerify = noVerify;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getLearnResource() {
        return learnResource;
    }

    public void setLearnResource(Boolean learnResource) {
        this.learnResource = learnResource;
    }

}
