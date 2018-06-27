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

    @Test
    public void testToTree() throws Exception {
        List<Category> list = getCategory();

        String pk = "pkId";
        String pt = "parentId";
        List<TreeNode<Category>> data = TreeNodeCreateUtil.toTree(Category.class, list, pk, pt);
        logout.info("生成目录树: {}", JSON.toJSONString(data));
    }

    private List<Category> getCategory() {
        String str =
                "[{\"name\":\"党建模板\",\"parentId\":\"0\",\"pkId\":\"9\",\"showOrder\":\"0\"},{\"name\":\"劳动节模板\",\"parentId\":\"9\",\"pkId\":\"10\",\"showOrder\":\"0\"},{\"name\":\"国庆节模板\",\"parentId\":\"9\",\"pkId\":\"11\",\"showOrder\":\"0\"}]\r\n";
        return JSON.parseArray(str).stream().map(jo -> JSON.toJavaObject((JSONObject) jo, Category.class))
                .collect(Collectors.toList());
    }
}
