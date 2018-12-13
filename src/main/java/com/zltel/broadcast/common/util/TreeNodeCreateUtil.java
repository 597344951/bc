package com.zltel.broadcast.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zltel.broadcast.common.tree.TreeNode;

/**
 * 树结构创建工具类
 * 
 * @author wangch
 * @junit com.zltel.broadcast.common.util.TreeNodeCreateUtilTest
 */
public class TreeNodeCreateUtil {

    public static final Logger log = LoggerFactory.getLogger(TreeNodeCreateUtil.class);

    private TreeNodeCreateUtil() {}

    /**
     * 搜索指定目标的所有子节点数据,包括子节点的子节点
     * 
     * @param target 搜索目标
     * @param list 数据
     * @param primaryKey 获取主id
     * @param parentKey 获取上一级id, 上一级id为0 或者 null 则判断是顶级目录
     * @return
     * {@link TreeNodeCreateUtilTest#testGetAllChildrenList()}
     */
    public static <T, R> List<T> getAllChildrenList(T target, List<T> list, Function<T, R> primaryKey,
            Function<T, R> parentKey) {
        R pk = primaryKey.apply(target);
        List<T> result = new ArrayList<>();
        list.forEach(t -> {
            R v = parentKey.apply(t);
            R mk = primaryKey.apply(t);
            if (pk.equals(v) || pk.equals(mk)) {
                result.add(t);
            }
        });
        handleNext(list, primaryKey, parentKey, result);
        return result;
    }

    /**
     * 使用原有对象中的 children生成树形结构
     * 
     * @param list 数据
     * @param primaryKey 获取主id
     * @param parentKey 获取上一级id, 上一级id为0 或者 null 则判断是顶级目录
     * @param setChildren 设置chidren的方法
     * @return
     * @junit {@link TreeNodeCreateUtilTest#testToTree2()}
     */
    public static <T, R> List<T> toTree(List<T> list, Function<T, R> primaryKey, Function<T, R> parentKey,
            BiConsumer<T, List<T>> setChildren) {
        List<T> result = new ArrayList<>();
        // 生成第一层
        list.stream().filter(t -> {
            R v = parentKey.apply(t);
            return v == null || "0".equals(v.toString());
        }).forEach(result::add);

        // 遍历剩余节点
        result.forEach(tn -> handleNextNode(tn, list, primaryKey, parentKey, setChildren));
        return result;
    }


    /**
     * 生成树结构
     * 
     * @param list 数据
     * @param primaryKey 获取主id
     * @param parentKey 获取上一级id, 上一级id为0 或者 null 则判断是顶级目录
     * @return
     * @junit {@link TreeNodeCreateUtilTest#testToTree3()}
     */
    public static <T, R> List<TreeNode<T>> toTree(List<T> list, Function<T, R> primaryKey, Function<T, R> parentKey) {
        List<TreeNode<T>> result = new ArrayList<>();
        // 生成第一层
        list.stream().filter(t -> {
            R v = parentKey.apply(t);
            return v == null || "0".equals(v.toString());
        }).forEach(td -> {
            TreeNode<T> tn = new TreeNode<>();
            tn.setData(td);
            result.add(tn);
        });
        result.forEach(tn -> handleNextNode2(tn, list, primaryKey, parentKey));
        return result;
    }

    private static <T, R> void handleNextNode2(TreeNode<T> node, List<T> datas, Function<T, R> primaryKey,
            Function<T, R> parentKey) {
        // 上一级节点的子节点
        List<TreeNode<T>> childs = new ArrayList<>();
        datas.stream().filter(n -> {
            // 当前值 和上一级值对比
            // n -> n.getParent() == node.getData().getCostType()
            // 父级 主id
            R pt = primaryKey.apply(node.getData());
            // 子级父id
            R ft = parentKey.apply(n);

            return pt.equals(ft);

        }).forEach(n -> {
            TreeNode<T> tn = new TreeNode<>();
            tn.setData(n);
            childs.add(tn);
        });
        if (childs.isEmpty()) return;
        node.setChildren(childs);
        childs.forEach(n -> handleNextNode2(n, datas, primaryKey, parentKey));
    }

    private static <T, R> void handleNextNode(T node, List<T> datas, Function<T, R> primaryKey,
            Function<T, R> parentKey, BiConsumer<T, List<T>> setChildren) {
        // 上一级节点的子节点
        List<T> childs = new ArrayList<>();
        datas.stream().filter(n -> {
            // 当前值 和上一级值对比
            // 父级 主id
            R pt = primaryKey.apply(node);
            // 子级父id
            R ft = parentKey.apply(n);

            return pt.equals(ft);

        }).forEach(childs::add);
        if (childs.isEmpty()) return;
        setChildren.accept(node, childs);
        childs.forEach(n -> handleNextNode(n, datas, primaryKey, parentKey, setChildren));
    }

    private static <T, R> void handleNext(List<T> list, Function<T, R> primaryKey, Function<T, R> parentKey,
            List<T> result) {
        if (result.isEmpty()) return;
        int bs = result.size();
        T target = result.get(result.size() - 1);
        R pk = primaryKey.apply(target);
        list.forEach(t -> {
            R v = parentKey.apply(t);
            if (pk.equals(v)) {
                result.add(t);
            }
        });
        if (result.size() > bs) handleNext(list, primaryKey, parentKey, result);
    }

}
