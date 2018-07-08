package com.zltel.broadcast.message.controller;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/pending")
    @ResponseBody
    public R pending() {
        return R.ok().setData(messageService.queryPending(getSysUser()));
    }
    @GetMapping("/notice/{pageNum}/{pageSize}")
    @ResponseBody
    public R notice(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return R.ok().setData(messageService.queryNotice(getSysUser(), pageNum, pageSize));
    }
}
