package com.zltel.broadcast.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 默认主页跳转
 * IndexController class
 *
 * @author Touss
 * @date 2018/5/7
 */
@Controller
public class IndexController {
    
    @GetMapping("/")
    public String index() {
        return "redirect:/view/index.jsp";
    }
}
