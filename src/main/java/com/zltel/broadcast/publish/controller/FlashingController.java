package com.zltel.broadcast.publish.controller;

import com.alibaba.fastjson.JSON;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.publish.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/flashing")
public class FlashingController {
    @Autowired
    private ActivityService activityService;

    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody Map<String, Object> body) {
        String title = (String) body.get("title");
        String words = JSON.toJSONString(body.get("words"));
        Map<String, Object> record = new HashMap<>();
        record.put("title", title);
        record.put("activity_id", -2);
        record.put("material", words);
        activityService.addSilhouette(record);
        return R.ok();
    }

    @GetMapping("/preview/{id}")
    @ResponseBody
    public R preview(@PathVariable("id") int id) {
        R r = R.ok();
        Map<String, Object> record = activityService.mapGetSilhouette(id);
        r.put("title", record.get("title"));
        r.put("words", JSON.parse((String) record.get("material")));
        return r;
    }


    @GetMapping("/list/{pageNum}/{pageSize}")
    @ResponseBody
    public R list(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        R r = R.ok();
        r.setData(activityService.queryFlashing(pageNum, pageSize));
        return r;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable("id") int id) {
        activityService.delete(id);
        return R.ok();
    }
}
