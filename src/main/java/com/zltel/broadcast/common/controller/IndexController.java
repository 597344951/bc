package com.zltel.broadcast.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 默认主页跳转
 * IndexController class
 *
 * @author Touss
 * @date 2018/5/7
 */
@Controller
public class IndexController {
    
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String index() {
        return "redirect:/view/index.jsp";
    }
}
