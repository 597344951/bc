package com.zltel.broadcast.um.bean;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 包含节点信息的菜单树 **/
public class SysMenuTreeNode extends SysMenu {
    
    private static final Logger log = LoggerFactory.getLogger(SysMenuTreeNode.class);


    private List<SysMenuTreeNode> children;

    public static SysMenuTreeNode from(SysMenu n) {
        if (n == null) return null;
        SysMenuTreeNode st = new SysMenuTreeNode();
        Stream.of(SysMenu.class.getDeclaredMethods()).filter(mn -> mn.getName().startsWith("get"))
                .forEach(mn -> {
                    try {
                        String name = mn.getName();
                        Object v = mn.invoke(n);
                        if (v != null) {
                            Method target = SysMenuTreeNode.class.getMethod("s" + name.substring(1),
                                    v.getClass());
                            target.invoke(st, v);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                });

        return st;
    }

    /**
     * 获取children
     * 
     * @return the children
     */
    public List<SysMenuTreeNode> getChildren() {
        return children;
    }

    /**
     * 设置children
     * 
     * @param children the children to set
     */
    public void setChildren(List<SysMenuTreeNode> children) {
        this.children = children;
    }



}
