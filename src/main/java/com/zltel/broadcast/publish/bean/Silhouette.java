package com.zltel.broadcast.publish.bean;

import com.zltel.broadcast.resource.bean.ResourceMaterial;

import java.util.Date;
import java.util.List;

public class Silhouette {
    private Integer silhouetteId;

    private Integer activityId;

    private String material;

    private List<ResourceMaterial> materials;

    private String title;

    private Date addDate;

    private Date updateDate;

    public Integer getSilhouetteId() {
        return silhouetteId;
    }

    public void setSilhouetteId(Integer silhouetteId) {
        this.silhouetteId = silhouetteId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material == null ? null : material.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<ResourceMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<ResourceMaterial> materials) {
        this.materials = materials;
    }
}