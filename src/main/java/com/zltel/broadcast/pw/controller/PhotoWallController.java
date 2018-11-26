package com.zltel.broadcast.pw.controller;

import com.alibaba.fastjson.JSON;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.publish.service.ActivityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pw")
public class PhotoWallController extends BaseController {
    @Value("${zltel.mediaserve}")
    private String mediaServe;

    @Autowired
    private ActivityService activityService;

    @GetMapping("/creator")
    public String creator(Model model, String module) {
        model.addAttribute("mediaServe", mediaServe);
        model.addAttribute("module", module);
        return "view/pw/index";
    }

    @GetMapping("/faker")
    public String faker(Model model, String module) {
        model.addAttribute("mediaServe", mediaServe);
        model.addAttribute("module", module);
        return "view/xjdx/index";
    }
    @GetMapping("/prePreview")
    public String previewByPhotos(Model model, String wrapJsonStr) {
        Map<String, Object> body = (Map<String, Object>) JSON.parse(wrapJsonStr);
        String url = (String) body.get("url");
        String effect = (String) body.get("effect");
        String backgroundImage = (String) body.get("backgroundImage");
        String backgroundColor = (String) body.get("backgroundColor");
        String backgroundMusic = (String) body.get("backgroundMusic");
        String delay = (String) body.get("delay");
        String titleTheme = (String) body.get("titleTheme");

        if (StringUtils.isNotEmpty(titleTheme)) {
            model.addAttribute("title", body.get("title"));
            body.remove("titleTheme");
            model.addAttribute("wrapJsonStr", JSON.toJSON(body));
            return titleTheme;
        } else {
            List<Map<String, String>> ps = (List<Map<String, String>>) body.get("photos");
            List<String> photos = ps.stream().map(photo -> mediaServe + photo.get("url")).collect(Collectors.toList());
            model.addAttribute("photos", photos);
            if(body.get("xx")!=""){
                Map<String, Object> xx = (Map<String, Object>) body.get("xx");
                model.addAttribute("xx",xx);
            }     
            if (StringUtils.isNotEmpty(effect)) model.addAttribute("effect", effect);
            if (StringUtils.isNotEmpty(backgroundImage))
                model.addAttribute("backgroundImage", mediaServe + backgroundImage);
            if (StringUtils.isNotEmpty(backgroundColor)) model.addAttribute("backgroundColor", backgroundColor);
            if (StringUtils.isNotEmpty(backgroundMusic))
                model.addAttribute("backgroundMusic", mediaServe + backgroundMusic);
            if (StringUtils.isNotEmpty(delay)) model.addAttribute("delay", delay);
            return url;
        }

    }

    @GetMapping("/demo")
    public String effectDemo(Model model, String url, String effect, int count) {
        List<String> photos = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            photos.add("/assets/img/num/" + i + ".png");
        }
        if (StringUtils.isNotEmpty(effect)) model.addAttribute("effect", effect);
        model.addAttribute("photos", photos);
        return url;
    }

    @PostMapping("/addxjdx")
    @ResponseBody
    public R addXjdx(@RequestBody Map<String, Object> body) {
        String title = (String) body.get("title");
        Map<String, Object> record = new HashMap<>();
        record.put("title", title);
        record.put("activity_id", 666);
        record.put("material", JSON.toJSONString(body));
        activityService.addSilhouette(record);
        return R.ok();
    }
    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody Map<String, Object> body) {
        String title = (String) body.get("title");
        Map<String, Object> record = new HashMap<>();
        record.put("title", title);
        record.put("activity_id", -1);
        record.put("material", JSON.toJSONString(body));
        activityService.addSilhouette(record);
        return R.ok();
    }
    @GetMapping("/preview/{id}")
    public String previewById(Model model, @PathVariable("id") int id) {
        Map<String, Object> record = activityService.mapGetSilhouette(id);
        Map<String, Object> body = (Map<String, Object>) JSON.parse(String.valueOf(record.get("material")));
        String url = (String) body.get("url");
        String effect = (String) body.get("effect");
        String backgroundImage = (String) body.get("backgroundImage");
        String backgroundColor = (String) body.get("backgroundColor");
        String backgroundMusic = (String) body.get("backgroundMusic");
        String delay = (String) body.get("delay");
        String titleTheme = (String) body.get("titleTheme");

        if (StringUtils.isNotEmpty(titleTheme)) {
            model.addAttribute("title", body.get("title"));
            body.remove("titleTheme");
            model.addAttribute("wrapJsonStr", JSON.toJSON(body));
            return titleTheme;
        } else {
            List<Map<String, String>> ps = (List<Map<String, String>>) body.get("photos");          
            List<String> photos = ps.stream().map(photo -> mediaServe + photo.get("url")).collect(Collectors.toList());
            model.addAttribute("photos", photos);
            if(body.get("xx")!=""){
                Map<String, Object> xx = (Map<String, Object>) body.get("xx");
                model.addAttribute("xx",xx);
            }        
            if (StringUtils.isNotEmpty(effect)) model.addAttribute("effect", effect);
            if (StringUtils.isNotEmpty(backgroundImage))
                model.addAttribute("backgroundImage", mediaServe + backgroundImage);
            if (StringUtils.isNotEmpty(backgroundColor)) model.addAttribute("backgroundColor", backgroundColor);
            if (StringUtils.isNotEmpty(backgroundMusic))
                model.addAttribute("backgroundMusic", mediaServe + backgroundMusic);
            if (StringUtils.isNotEmpty(delay)) model.addAttribute("delay", delay);
            return url;
        }
    }


    @GetMapping("/list/{pageNum}/{pageSize}")
    @ResponseBody
    public R pwList(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        R r = R.ok();
        r.setData(activityService.queryGallery(pageNum, pageSize));
        return r;
    }
    @GetMapping("/listXjdx/{pageNum}/{pageSize}")
    @ResponseBody
    public R pwList2(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        R r = R.ok();
        r.setData(activityService.queryXjdx(pageNum, pageSize));
        return r;
    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable("id") int id) {
        activityService.delete(id);
        return R.ok();
    }
}
