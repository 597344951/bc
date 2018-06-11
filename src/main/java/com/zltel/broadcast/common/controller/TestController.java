package com.zltel.broadcast.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zltel.broadcast.common.dao.SimpleDao;

/**
 * TestController class
 *
 * @author Touss
 * @date 2018/5/7
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController{
    @Autowired
    private SimpleDao simpleDao;


    public TestController() {
        super(TestController.class, "Test");
    }

    @RequestMapping("/get")
    @ResponseBody
    public List<Map<String,Object>> get(HttpSession session) {
        info(session, "write a test msg ..");
        return simpleDao.query("log", new HashMap<String, Object>(), 1, 10);
    }
}
