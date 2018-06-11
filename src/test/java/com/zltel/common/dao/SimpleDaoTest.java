package com.zltel.common.dao;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.dao.SimpleDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Created by Touss on 2018/5/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleDaoTest {
    @Autowired
    private SimpleDao simpleDao;
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void get() throws Exception {
        System.out.println(simpleDao.get("log", new HashMap<String, Object>()));
    }

    @Test
    public void query() throws Exception {
        List<Map<String, Object>> rets = simpleDao.query("log", null, 1, 10);
        PageInfo page = new PageInfo(rets);
        System.out.println(page.getTotal());
        for(Object ret : page.getList()) {
            System.out.println(ret);
        }
    }

    @Test
    public void add() throws Exception {
        Map<String, Object> contentType = new HashMap<String, Object>();
        contentType.put("name", "article");
        contentType.put("description", "文章内容");
        contentType.put("add_date", new Date());
        contentType.put("update_date", new Date());
        simpleDao.add("publish_content_type", contentType);
        System.out.println(contentType.get("id"));
    }

    @Test
    public void adds() throws Exception {
        List<Map<String, Object>> contentTypes = new ArrayList<Map<String, Object>>();
        Map<String, Object> contentType = new HashMap<String, Object>();
        contentType.put("name", "article");
        contentType.put("description", "文章内容");
        contentType.put("add_date", new Date());
        contentType.put("update_date", new Date());
        contentTypes.add(contentType);
        contentType = new HashMap<String, Object>();
        contentType.put("name", "tip");
        contentType.put("description", "通知内容");
        contentType.put("add_date", new Date());
        contentType.put("update_date", new Date());
        contentTypes.add(contentType);
        simpleDao.adds("publish_content_type", contentTypes);
    }

}