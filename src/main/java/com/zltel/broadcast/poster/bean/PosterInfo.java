package com.zltel.broadcast.poster.bean;

public class PosterInfo {
    private Integer templateId;

    private String pid;


    private String title;

    private Integer width;

    private Integer height;

    private String preview;

    private String from;


    private String layouts;

    private Integer sizeType;

    /** 搜索关键字 **/
    private String keyword;
    private PosterSize posterSize;
    /**查询分类**/
    private Integer useCategory;
    /**分类信息**/
    private Integer[] useCategorys;


    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }



    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
 

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview == null ? null : preview.trim();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from == null ? null : from.trim();
    }

    public String getLayouts() {
        return layouts;
    }

    public void setLayouts(String layouts) {
        this.layouts = layouts;
    }

    public Integer getSizeType() {
        return sizeType;
    }

    public void setSizeType(Integer sizeType) {
        this.sizeType = sizeType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public PosterSize getPosterSize() {
        return posterSize;
    }

    public void setPosterSize(PosterSize posterSize) {
        this.posterSize = posterSize;
    }

    public Integer getUseCategory() {
        return useCategory;
    }

    public void setUseCategory(Integer useCategory) {
        this.useCategory = useCategory;
    }

    public Integer[] getUseCategorys() {
        return useCategorys;
    }

    public void setUseCategorys(Integer[] useCategorys) {
        this.useCategorys = useCategorys;
    }

}
