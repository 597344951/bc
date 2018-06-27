package com.zltel.broadcast.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.ReflectUtils;

import com.zltel.broadcast.common.tree.TreeNode;

public class TreeNodeCreateUtil {

    private static final Logger log = LoggerFactory.getLogger(TreeNodeCreateUtil.class);

    private TreeNodeCreateUtil() {}

    /**
     * 生成树型结构
     * 
     * @param c
     * @param list 数据
     * @param pId 主键字段名
     * @param parentId 上一级信息字段
     * @return
     */
    public static <T> List<TreeNode<T>> toTree(Class<T> c, List<T> list, String pId, String parentId) {
        List<TreeNode<T>> result = new ArrayList<>();
        Method pIdMethod = null;
        Method parentIdMethod = null;
        for (PropertyDescriptor pd : ReflectUtils.getBeanGetters(c)) {
            if (pd.getName().equalsIgnoreCase(pId)) {
                pIdMethod = pd.getReadMethod();
            } else if (pd.getName().equalsIgnoreCase(parentId)) {
                parentIdMethod = pd.getReadMethod();
            }
        }
        final Method pidM = pIdMethod;
        final Method parentM = parentIdMethod;

        // 生成第一层
        list.stream().filter(t -> {
            Object v = getValue(parentM, t);
            return v == null || "0".equals(v.toString());
        }).forEach(td -> {
            TreeNode<T> tn = new TreeNode<>();
            tn.setData(td);
            result.add(tn);
        });
        result.forEach(tn -> handleNextNode(tn, list, pidM, parentM));
        return result;
    }

    private static <T> Object getValue(Method pIdMethod, T t) {
        try {
            return pIdMethod.invoke(t);
        } catch (Exception e) {
            log.error("读取getter失败:{}", e.getMessage());
        }
        return null;
    }

    /** 递归遍历子节点 **/
    private static <T> void handleNextNode(TreeNode<T> node, List<T> datas, final Method pidM, final Method parentM) {
        // 上一级节点的子节点
        List<TreeNode<T>> childs = new ArrayList<>();
        datas.stream().filter(n -> {
            // 当前值 和上一级值对比
            // n -> n.getParent() == node.getData().getCostType()
            // 父级 主id
            Object pt = getValue(pidM, node.getData());
            // 子级父id
            Object ft = getValue(parentM, n);

            return pt.equals(ft);

        }).forEach(n -> {
            TreeNode<T> tn = new TreeNode<>();
            tn.setData(n);
            childs.add(tn);
        });
        if (childs.isEmpty()) return;
        node.setChildren(childs);
        childs.forEach(n -> handleNextNode(n, datas, pidM, parentM));
    }


}
