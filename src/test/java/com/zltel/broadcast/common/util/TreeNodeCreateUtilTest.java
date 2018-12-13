package com.zltel.broadcast.common.util;


import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zltel.BaseTests;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.incision.sola.bean.Category;

public class TreeNodeCreateUtilTest extends BaseTests {


    private List<Category> getCategory() {
        String str =
                "[{\"name\":\"党建模板\",\"parentId\":\"0\",\"pkId\":\"9\",\"showOrder\":\"0\"},{\"name\":\"劳动节模板\",\"parentId\":\"9\",\"pkId\":\"10\",\"showOrder\":\"0\"},{\"name\":\"国庆节模板\",\"parentId\":\"10\",\"pkId\":\"11\",\"showOrder\":\"0\"}]\r\n";
        return JSON.parseArray(str).stream().map(jo -> JSON.toJavaObject((JSONObject) jo, Category.class))
                .collect(Collectors.toList());
    }

    @Test
    public void testToTree2() {
        List<Category> list = getCategory();
        List<TreeNode<Category>> data = TreeNodeCreateUtil.toTree(list, Category::getPkId, Category::getParentId);
        logout.info("生成目录树2: {}", JSON.toJSONString(data));
    }

    @Test
    public void testToTree3() {
        List<Category> list = getCategory();
        List<Category> data = TreeNodeCreateUtil.toTree(list, Category::getPkId, Category::getParentId, Category::setChildren);
        logout.info("生成目录树3: {}", JSON.toJSONString(data));
    }

    @Test
    public void testGetAllChildrenList() {
        Category target = new Category();
        target.setPkId("9");
        List<Category> list = getCategory();
        List<Category> data = TreeNodeCreateUtil.getAllChildrenList(target, list, Category::getPkId, Category::getParentId);
        logout.info("查找子项: {}", JSON.toJSONString(data));
    }

}
