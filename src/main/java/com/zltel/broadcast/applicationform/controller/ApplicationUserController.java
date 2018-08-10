package com.zltel.broadcast.applicationform.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zltel.broadcast.applicationform.bean.ApplicationUser;
import com.zltel.broadcast.applicationform.service.ApplicationFormService;
import com.zltel.broadcast.applicationform.service.ApplicationUserService;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.pager.PagerHelper;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/application-user")
public class ApplicationUserController extends BaseController {

    @Resource
    private ApplicationFormService formService;
    @Resource
    private ApplicationUserService userService;

    @PostMapping("/form-data/{formId}")
    @ApiOperation("表存用户提交申请表数据")
    public R saveFormData(@PathVariable("formId") Integer formId, @RequestBody Map<String, String> data) {
        this.userService.saveUserFormData(formId, data);
        return R.ok();
    }


    @PostMapping("/form-data/query/{pageNum}-{pageSize}")
    public R query(@RequestBody ApplicationUser au, @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize) {
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<ApplicationUser> aus = this.userService.queryFull(au);
        Pager pager = PagerHelper.toPager(page);
        
        return R.ok().setData(aus).setPager(pager);
    }


}
