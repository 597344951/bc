package com.zltel.broadcast.um.service;


import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.um.bean.SysMenu;
import com.zltel.broadcast.um.bean.SysMenuTreeNode;

public class SysMenuServiceTest extends BroadcastApplicationTests {

    private Logger logout = Logger.getLogger(SysMenuServiceTest.class.toString());
    
    @Resource
    private SysMenuService service;
 
    @Test
    public void testQueryAllMenuForTree()throws Exception  {
        List<SysMenuTreeNode> datas = this.service.queryAllMenuForTree();
        logout.info(JSON.toJSONString(datas));
    }
    @Test
    public void testQueryTreeMenu() throws Exception {
        List<TreeNode<SysMenu>> result  = service.queryTreeMenu();
        
        logout.info(JSON.toJSONString(result));
    }
}
