package com.zltel.broadcast.publish.controller;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.publish.service.ActivityService;
import com.zltel.broadcast.publish.service.MaterialService;
import com.zltel.broadcast.um.bean.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ActivityController class
 *
 * @author Touss
 * @date 2018/5/25
 */
@Controller
@RequestMapping("/publish")
@PropertySource("classpath:upload.properties")
public class ActivityController extends BaseController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    ActivityService activityService;
    @Autowired
    MaterialService materialService;

    @Value("${upload.file.dir}")
    private String uploadFileDir;

    @GetMapping(value = "/activity/addition/{contentId}")
    @ResponseBody
    public R activityAddition(@PathVariable("contentId") int contentId) {
        R r;
        try {
            r = R.ok();
            r.setData(activityService.getActivityAddition(contentId));
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.getMessage());
        }
        return r;
    }

    @PostMapping(value = "/activity/addition")
    @ResponseBody
    public R activityAddition(@RequestBody Map<String, Object> addition) {
        R r;
        try {
            SysUser user = getSysUser();
            //同步文件
            materialService.transferMaterial(user, (List<Map<String, Object>>) addition.get("material"), request.getSession().getServletContext().getRealPath("/"), uploadFileDir);
            activityService.completeActivityAddition(user, addition);
            r = R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.getMessage());
        }
        return r;
    }

    @GetMapping(value = "/activity/participant/{contentId}")
    @ResponseBody
    public R activityParticipant(@PathVariable("contentId") int contentId) {
        R r;
        try {
            r = R.ok();
            r.setData(activityService.queryParticipant(contentId));
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.getMessage());
        }
        return r;
    }

    @GetMapping(value = "/activity/participate/{contentId}")
    @ResponseBody
    public R participate(@PathVariable("contentId") int contentId) {
        R r;
        try {
            r = R.ok();
            activityService.participate(contentId, getSysUser());
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.getMessage());
        }
        return r;
    }

    @PostMapping(value = "/activity/participant")
    @ResponseBody
    public R activityParticipant(@RequestBody List<Map<String, Object>> sequels) {
        R r;
        try {
            r = R.ok();
            activityService.updateParticipantSequel(sequels);
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.getMessage());
        }
        return r;
    }
}
