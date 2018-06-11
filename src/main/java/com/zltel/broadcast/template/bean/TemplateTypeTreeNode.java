package com.zltel.broadcast.template.bean;

import java.util.List;

/** 分类树形节点 **/
public class TemplateTypeTreeNode {
    private Integer id;
    private String label;
    private String name;
    private TemplateType data;
    private List<TemplateTypeTreeNode> children;

    public void fromTemplateType(TemplateType n) {
        this.id = n.getTpTypeId();
        this.name = n.getName();
        if (n.getCount() > 0) {
            this.label = n.getName() + "(" + n.getCount() + ")";
        } else {
            this.label = n.getName();
        }
        this.data = n;
    }


    /**
     * 获取id
     * 
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     * 
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取label
     * 
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 设置label
     * 
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 获取children
     * 
     * @return the children
     */
    public List<TemplateTypeTreeNode> getChildren() {
        return children;
    }

    /**
     * 设置children
     * 
     * @param children the children to set
     */
    public void setChildren(List<TemplateTypeTreeNode> children) {
        this.children = children;
    }


    /**
     * 获取data
     * 
     * @return the data
     */
    public TemplateType getData() {
        return data;
    }


    /**
     * 设置data
     * 
     * @param data the data to set
     */
    public void setData(TemplateType data) {
        this.data = data;
    }


    /**
     * 获取name
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }



    /**
     * 设置name
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }



}
