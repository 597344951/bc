package com.zltel.broadcast.common.tree;

import java.util.List;

public class TreeNode<T> {
    private T data;
    private List<TreeNode<T>> children;
    /**
     * 获取data  
     * @return the data
     */
    public T getData() {
        return data;
    }
    /**
     * 设置data  
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }
    /**
     * 获取children  
     * @return the children
     */
    public List<TreeNode<T>> getChildren() {
        return children;
    }
    /**
     * 设置children  
     * @param children the children to set
     */
    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }
  
    
    
}
