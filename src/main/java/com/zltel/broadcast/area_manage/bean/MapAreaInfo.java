package com.zltel.broadcast.area_manage.bean;

import java.util.Date;

public class MapAreaInfo {
    private Integer id;

    private Integer typeId;

    private String shape;

    private String name;

    private String fillColor;

    private String lineColor;

    private String describes;
    
    private Date createTime;
    
    private Integer areaLevel;

    public Integer getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(Integer areaLevel) {
		this.areaLevel = areaLevel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape == null ? null : shape.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor == null ? null : fillColor.trim();
    }

    public String getLineColor() {
        return lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor == null ? null : lineColor.trim();
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes == null ? null : describes.trim();
    }
}