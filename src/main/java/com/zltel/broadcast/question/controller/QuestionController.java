package com.zltel.broadcast.question.controller;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.util.FileUtil;
import com.zltel.broadcast.question.bean.Category;
import com.zltel.broadcast.question.bean.Question;
import com.zltel.broadcast.question.bean.Subject;
import com.zltel.broadcast.question.service.QuestionService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/subject")
    @ResponseBody
    public R addSubject(@RequestBody Subject subject) {
        questionService.addSubject(subject.getName(), subject.getDescription());
        return R.ok();
    }

    @GetMapping("/subject")
    @ResponseBody
    public R subjects() {
        R r = R.ok();
        r.setData(questionService.querySubject());
        return r;
    }

    @PostMapping("/category")
    @ResponseBody
    public R addSubject(@RequestBody Category category) {
        questionService.addCategory(category.getName(), category.getDescription());
        return R.ok();
    }

    @GetMapping("/category")
    @ResponseBody
    public R categories() {
        R r = R.ok();
        r.setData(questionService.queryCategory());
        return r;
    }

    @PostMapping("/delete")
    @ResponseBody
    public R removeQuestion(@RequestBody Question question) {
        questionService.removeQuestion(question);
        return R.ok();
    }

    @PostMapping("/add")
    @ResponseBody
    public R addQuestion(@RequestBody Question question) {
        question.setOrgId(getSysUser().getOrgId());
        questionService.addQuestion(question);
        return R.ok();
    }

    @PostMapping("/query")
    @ResponseBody
    public R questions(@RequestBody Map<String, Object> filter) {
        R r = R.ok();
        long count = questionService.count(getSysUser().getOrgId(),
                (String) filter.get("keyword"),
                (List<Integer>) filter.get("type"),
                (List<Integer>) filter.get("subject"),
                (List<Integer>) filter.get("category"));
        List<Question> questions = questionService.query(getSysUser().getOrgId(),
                (String) filter.get("keyword"),
                (List<Integer>) filter.get("type"),
                (List<Integer>) filter.get("subject"),
                (List<Integer>) filter.get("category"),
                (Integer) filter.get("pageNum"),
                (Integer) filter.get("pageSize"));
        r.setData(questions);
        r.put("total", count);
        return r;
    }

    @PostMapping("/rand/{orgId}")
    @ResponseBody
    public R rand(@RequestBody Map<String, Object> filter, @PathVariable("orgId") int orgId) {
        R r = R.ok();
        r.setData(questionService.randomQuestion(orgId,
                (String) filter.get("keyword"),
                (List<Integer>) filter.get("type"),
                (List<Integer>) filter.get("subject"),
                (List<Integer>) filter.get("category"),
                (Integer) filter.get("size")));
        return r;
    }

    @PostMapping("/file")
    @ResponseBody
    public R questionFile(@RequestParam("file") MultipartFile file) {
        R r;
        try {
            r = R.ok();
            String fileName = file.getOriginalFilename();
            if (!fileName.endsWith(".xlsx")) {
                throw new RuntimeException("导入失败：请上传指定类型文件（xlsx）");
            }
            String save = UUID.randomUUID().toString();
            file.transferTo(new File(FileUtils.getTempDirectoryPath() + "/" + save));
            r.put("fileName", save);
        } catch (Exception e) {
            r = R.error(e.getMessage());
        }
        return r;
    }

    @PostMapping("/import")
    @ResponseBody
    public R importQuestion(@RequestBody Map<String, Object> importFile) {
        String subject = String.valueOf(importFile.get("subject"));
        String category = String.valueOf(importFile.get("category"));
        String type = String.valueOf(importFile.get("type"));
        String fileName = String.valueOf(importFile.get("importFile"));

        return questionService.importQuestion(getSysUser().getOrgId(),
                Integer.valueOf(type), Integer.valueOf(subject), Integer.valueOf(category),
                new File(FileUtils.getTempDirectoryPath() + "/" + fileName));
    }

}
