package com.zltel.broadcast.publish.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.publish.service.MaterialService;
import com.zltel.broadcast.publish.service.PublishService;
import com.zltel.broadcast.um.bean.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * PublishController class
 *
 * @author Touss
 * @date 2018/5/8
 */
@Controller
@RequestMapping("/publish")
@PropertySource("classpath:upload.properties")
public class PublishController extends BaseController{
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private PublishService publishService;
    @Autowired
    private MaterialService materialService;
    @Value("${upload.file.dir}")
    private String uploadFileDir;

    @RequestMapping(value = "/new")
    public String create(Model model) {
        model.addAttribute("userId", getSysUser().getUserId());
        return "view/publish/new";
    }

    @GetMapping(value = "/template/{id}")
    public String template(Model model, @PathVariable("id") int id) {
        model.addAttribute("contentId", id);
        return "view/publish/template";
    }

    @GetMapping(value = "/process")
    public String process() {
        return "view/publish/process";
    }

    @GetMapping(value = "/publishing")
    public String publishing() {
        return "view/publish/publishing";
    }

    @GetMapping(value = "/published")
    public String published() {
        return "view/publish/published";
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public R contentAdd(@RequestBody Map<String, Object> content) {
        R r;
        try {
            SysUser user = getSysUser();
            //同步文件
            materialService.transferMaterial(user, (List<Map<String, Object>>) content.get("material"), request.getSession().getServletContext().getRealPath("/"), uploadFileDir);
            Map<String, Object> detail = publishService.create(user, content);
            materialService.saveUeditorMaterial(user, detail, request.getSession().getServletContext().getRealPath("/"), uploadFileDir);
            r = R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.toString());
        }
        return r;
    }

    @RequestMapping(value = "/process/content")
    @ResponseBody
    public R processContent() {
        R r;
        try {
            r = R.ok();
            r.setData(publishService.queryProcessContent(getSysUser()));
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.toString());
        }
        return r;
    }

    @RequestMapping(value = "/process/start_me/{id}")
    @ResponseBody
    public R startMoreEdit(@PathVariable("id") int id) {
        R r;
        try {
            r = R.ok();
            int editId = publishService.moreEditStart(getSysUser(), id);
            r.put("editId", editId);
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.toString());
        }
        return r;
    }

    @RequestMapping(value = "/process/commit_me/{id}")
    @ResponseBody
    public R commitMoreEdit(@PathVariable("id") int id, @RequestParam("snapshot") String snapshot) {
        R r;
        try {
            r = R.ok();
            publishService.moreEditCommit(getSysUser(), id, snapshot);
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.toString());
        }
        return r;
    }

    @RequestMapping(value = "/process/verify/{id}")
    @ResponseBody
    public R verify(@PathVariable("id") int id, @RequestParam("type") int type, @RequestParam("opinion") String opinion, @RequestParam("isAdopt") boolean isAdopt) {
        R r;
        try {
            r = R.ok();
            publishService.verify(getSysUser(), isAdopt, opinion, id, type);
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.toString());
        }
        return r;
    }

    @RequestMapping(value = "/process/publish/{id}")
    @ResponseBody
    public R publish(@PathVariable("id") int id) {
        R r;
        try {
            r = R.ok();
            publishService.publish(getSysUser(), id);
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.toString());
        }
        return r;
    }

    @RequestMapping(value = "/process/offline/{id}")
    @ResponseBody
    public R offline(@PathVariable("id") int id) {
        R r;
        try {
            r = R.ok();
            publishService.offline(getSysUser(), id);
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.toString());
        }
        return r;
    }

    @RequestMapping(value = "/process/discard/{id}")
    @ResponseBody
    public R discard(@PathVariable("id") int id) {
        R r;
        try {
            r = R.ok();
            publishService.discard(getSysUser(), id);
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.toString());
        }
        return r;
    }

    @GetMapping(value="/process/state/{type}/{id}")
    @ResponseBody
    public R processState(@PathVariable("type") int type, @PathVariable("id") int id) {
        R r;
        try {
            r = R.ok();
            r.setData(publishService.getShowProcessState(type, id));
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.toString());
        }
        return r;
    }

    @GetMapping(value = "/content/{id}")
    @ResponseBody
    public R getContent(@PathVariable("id") int id) {
        R r;
        try {
            r = R.ok();
            Map<String, Object> content = publishService.getContent(id);
            content.put("material", materialService.queryMaterial(id));
            r.setData(content);
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.toString());
        }
        return r;
    }

    @GetMapping(value = "/publishing/content/{pageNum}/{pageSize}")
    @ResponseBody
    public R publishingContent(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        R r;
        try {
            r = R.ok();
            PageInfo page = new PageInfo(publishService.queryPublishingContent(pageNum, pageSize));
            r.setData(page);
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.toString());
        }
        return r;
    }

    @GetMapping(value = "/published/content/{pageNum}/{pageSize}")
    @ResponseBody
    public R publishedContent(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        R r;
        try {
            r = R.ok();
            PageInfo page = new PageInfo(publishService.queryPublishedContent(pageNum, pageSize));
            r.setData(page);
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error(e.toString());
        }
        return r;
    }
}
