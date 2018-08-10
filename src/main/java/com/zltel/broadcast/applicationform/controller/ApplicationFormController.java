package com.zltel.broadcast.applicationform.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.applicationform.bean.ApplicationForm;
import com.zltel.broadcast.applicationform.service.ApplicationFormService;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/application-form")
public class ApplicationFormController extends BaseController {

    @Resource
    private ApplicationFormService formService;

    @PostMapping("/form/query")
    @ApiOperation("查询申请表数据")
    public R query(@RequestBody ApplicationForm form) {
        List<ApplicationForm> forms = this.formService.queryFull(form);
        return R.ok().setData(forms);
    }

    @GetMapping("/form/{formId}")
    @ApiOperation("加载指定申请表数据")
    public R getForms(@PathVariable("formId") Integer formId) {
        ApplicationForm data = this.formService.selectFullByPrimaryKey(formId);
        return R.ok().setData(data);
    }



}
