package com.zltel.broadcast.template.bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zltel.broadcast.common.validator.group.GroupDelete;
import com.zltel.broadcast.common.validator.group.GroupSave;
import com.zltel.broadcast.common.validator.group.GroupUpdate;

public class TemplateType {
    @NotNull(message="分类id不能为空",groups={GroupUpdate.class,GroupDelete.class})
    private Integer tpTypeId;
    @NotBlank(message="分类名称不能为空",groups={GroupSave.class})
    private String name;
    @NotBlank(message="分类描述不能为空",groups={GroupSave.class})
    private String remark;
    
    private Integer orgid;
    @NotNull(message="分类上一级信息不能为空",groups={GroupSave.class})
    private Integer parent;
    @NotNull(message="分类排序序号不能为空",groups={GroupSave.class})
    private Integer orderNum;

    private Boolean builtin;
    /** 对应文章数量 **/
    private Integer count;
    
    private String keyword;

    public Integer getTpTypeId() {
        return tpTypeId;
    }

    public void setTpTypeId(Integer tpTypeId) {
        this.tpTypeId = tpTypeId;
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

    /**
     * 获取对应文章数量
     * 
     * @return the count
     */
    public Integer getCount() {
        return count;
    }


    /**
     * 设置对应文章数量
     * 
     * @param count the 对应文章数量 to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


}
