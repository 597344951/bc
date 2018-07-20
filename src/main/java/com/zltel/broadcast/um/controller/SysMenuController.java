package com.zltel.broadcast.um.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.um.bean.SysMenu;
import com.zltel.broadcast.um.bean.SysMenuTreeNode;
import com.zltel.broadcast.um.service.SysMenuService;

import io.swagger.annotations.ApiOperation;


@RequestMapping(value = {"/sys/menu"})
@RestController
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public R list() {
        List<SysMenu> menuList = sysMenuService.queryForList(null);

        return R.ok().setData(menuList);
    }


    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public R info(@PathVariable("menuId") Long menuId) {
        SysMenu menu = sysMenuService.selectByPrimaryKey(menuId);
        return R.ok().setData(menu);
    }

    /**
     * 保存
     */
    @LogPoint("保存菜单")
    @PostMapping("/menu")
    @RequiresPermissions("sys:menu:save")
    public R save(@RequestBody SysMenu menu) {
        // 数据校验

        sysMenuService.insert(menu);
        return R.ok();
    }

    /**
     * 修改
     */
    @LogPoint("修改菜单")
    @PutMapping("/menu")
    @RequiresPermissions("sys:menu:update")
    public R update(@RequestBody SysMenu menu) {
        // 数据校验

        sysMenuService.updateByPrimaryKeySelective(menu);
        return R.ok();
    }

    /**
     * 删除
     */
    @LogPoint("删除菜单")
    @DeleteMapping("/menu/{menuId}")
    @RequiresPermissions("sys:menu:delete")
    public R delete(@PathVariable("menuId") long menuId) {

        // 判断是否有子菜单或按钮
        List<SysMenu> menuList = sysMenuService.queryListParentId(menuId);
        if (!menuList.isEmpty()) {
            return R.error("请先删除子菜单或按钮");
        }

        sysMenuService.deleteByPrimaryKey(menuId);
        return R.ok();
    }

    /**
     * 查询菜单信息
     * 
     * @param sysMenu 条件
     * @return
     */
    @RequestMapping(value = "/querySysMenus", method = RequestMethod.POST)
    @LogPoint("查询菜单信息")
    @RequiresPermissions(value = {"sys:menu:query"})
    @ApiOperation(value = "查询菜单信息")
    public R querySysMenus(SysMenu sysMenu, int pageNum, int pageSize) {
        try {
            return sysMenuService.querySysMenus(sysMenu, pageNum, pageSize);
        } catch (Exception e) {
            return R.error().setMsg("查询菜单信息失败");
        }
    }

    /**
     * 查询菜单信息
     * 
     * @param sysMenu 条件
     * @return
     */
    @RequestMapping(value = "/querySysMenusNotPage", method = RequestMethod.POST)
    @LogPoint("查询菜单信息")
    @RequiresPermissions(value = {"sys:menu:query"})
    @ApiOperation(value = "查询菜单信息")
    public R querySysMenusNotPage(SysMenu sysMenu) {
        try {
            return sysMenuService.querySysMenusNotPage(sysMenu);
        } catch (Exception e) {
            return R.error().setMsg("查询菜单信息失败");
        }
    }

    @GetMapping("/tree")
    @ApiOperation(value = "查询菜单成树")
    public R queryMenuTree() {
        List<SysMenuTreeNode> datas = sysMenuService.queryMenuForTreeByPerms();
        return R.ok().setData(datas);
    }

    @GetMapping("/treeNode")
    @ApiOperation(value = "查询权限树")
    public R queryTreeNode() {
        List<SysMenuTreeNode> datas = this.sysMenuService.queryAllMenuForTree();
        return R.ok().setData(datas);
    }

    @GetMapping("/tree-node")
    @ApiOperation(value = "查询所有菜单树,结果为TreeNode")
    public R queryMenuTreeNode() {
        List<TreeNode<SysMenu>> datas = this.sysMenuService.queryAllMenuInfo();
        return R.ok().setData(datas);
    }
}
