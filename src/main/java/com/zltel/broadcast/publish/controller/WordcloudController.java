package com.zltel.broadcast.publish.controller;

import com.zltel.broadcast.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wordcloud")
public class WordcloudController extends BaseController {
    @Value("${zltel.mediaserve}")
    private String mediaServe;

    @GetMapping("/")
    public String wordcloud(Model model) {
        model.addAttribute("mediaServe", mediaServe);
        return "/view/wordcloud/index";
    }

}
