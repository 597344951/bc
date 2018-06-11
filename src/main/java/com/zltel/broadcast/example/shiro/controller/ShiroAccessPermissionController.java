package com.zltel.broadcast.example.shiro.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;

/**
 * Reason: 权限校验测试 date: 2018年1月19日 上午10:59:27 <br/>
 * 
 * @author Wangch
 * @junit {@link ShiroAccessPermissionControllerTest}
 */
@RestController
@RequestMapping(value = {"/shiroTest"})
public class ShiroAccessPermissionController {

    public static final Logger log = LoggerFactory.getLogger(ShiroAccessPermissionController.class);

    /** 日志输出对象 **/
    public static final Log logout = LogFactory.getLog(ShiroAccessPermissionController.class);

    @GetMapping("/log")
    @LogPoint(value="测试日志",type=LogPoint.TYPE_PERMS_LOG)
    public R testLog(String msg) {

        return R.ok().setData(msg);
    }

    /**
     * 测试访问
     */
    @RequestMapping(value = {"/org_add"}, method = RequestMethod.GET)
    @RequiresPermissions(value = {"sys:role:save"})
    @ResponseBody
    @LogPoint("角色添加")
    public R org_add() {

        return R.ok().setData("add success");
    }

    @RequestMapping(value = {"/org_update"}, method = RequestMethod.GET)
    @RequiresPermissions(value = {"sys:role:update"})
    @ResponseBody
    public R org_update() {
        Subject subject = SecurityUtils.getSubject();
        log.info("是否登陆：" + subject.isAuthenticated());
        return R.ok().setData(subject.getPrincipal());
    }

    @RequestMapping(value = {"/org_delete"}, method = RequestMethod.GET)
    @RequiresPermissions(value = {"sys:role:delete"})
    @ResponseBody
    public R org_delete() {

        return R.ok().setData("delete success");
    }

    @RequestMapping(value = {"/org_list"}, method = RequestMethod.GET)
    @RequiresPermissions(value = {"sys:role:list"})
    @ResponseBody
    public R org_list() {

        return R.ok().setData("list success");
    }

    @RequestMapping(value = {"/org_other"}, method = RequestMethod.GET)
    @RequiresPermissions(value = {"sys:role:other"})
    @ResponseBody
    public R org_other() {

        return R.ok().setData("other");
    }
}
