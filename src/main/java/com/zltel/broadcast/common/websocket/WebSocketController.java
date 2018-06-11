package com.zltel.broadcast.common.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * WebSocketController class
 *
 * @author Touss
 * @date 2018/5/18
 */
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @GetMapping("/notification/send")
    @ResponseBody
    public String send(@RequestParam("user") String user, @RequestParam("msg") String msg) {
        messagingTemplate.convertAndSend("/notification/" + user, new Message("System", msg));
        return "SUCCESS";
    }
}
