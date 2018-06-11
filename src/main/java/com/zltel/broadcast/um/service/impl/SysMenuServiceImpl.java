package com.zltel.broadcast.um.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.common.tree.TreeNode;
import com.zltel.broadcast.common.util.Constant;
import com.zltel.broadcast.um.bean.SysMenu;
import com.zltel.broadcast.um.bean.SysMenuTreeNode;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.dao.SysMenuMapper;
import com.zltel.broadcast.um.service.SysMenuService;

@Service
public class SysMenuServiceImpl extends BaseDaoImpl<SysMenu> implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public BaseDao<SysMenu> getInstince() {
        return this.sysMenuMapper;
    }

    @Override
    public List<SysMenu> queryListParentId(Long menuId) {
        SysMenu menu = new SysMenu();
        menu.setParentId(menuId);

        return this.sysMenuMapper.query(menu, null);
    }

    /**
     * 查询菜单
     * 
     * @param sysMenu 条件
     * @return 查询得到的菜单
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R querySysMenus(SysMenu sysMenu, int pageNum, int pageSize) throws Exception {
    	PageHelper.startPage(pageNum, pageSize);
        List<SysMenu> sysMenus = sysMenuMapper.querySysMenus(sysMenu); // 开始查询，没有条件则查询所有菜单
        PageInfo<SysMenu> sysMenusForPageInfo = new PageInfo<>(sysMenus);
        if (sysMenusForPageInfo != null && sysMenusForPageInfo.getList() != null && sysMenusForPageInfo.getList().size() > 0) { // 是否查询到数据
            return R.ok().setData(sysMenusForPageInfo).setMsg("查询菜单成功");
        } else {
            return R.ok().setMsg("没有查询到菜单信息");
        }
    }
    
    /**
     * 查询菜单
     * 
     * @param sysMenu 条件
     * @return 查询得到的菜单
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R querySysMenusNotPage(SysMenu sysMenu) throws Exception {
    	List<TreeNode<SysMenu>> sysMenus = this.queryTreeMenu(); // 开始查询，没有条件则查询所有菜单
        if (sysMenus != null && sysMenus.size() > 0) { // 是否查询到数据
            return R.ok().setData(sysMenus).setMsg("查询菜单成功");
        } else {
            return R.ok().setMsg("没有查询到菜单信息");
        }
    }

    @Override
    public List<SysMenuTreeNode> queryMenuForTreeByPerms() {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        List<SysMenu> datas = this.query(null, null);
        Stream<SysMenu> streams =
                datas.stream().filter(e -> e.getType() != Constant.MenuType.BUTTON.getValue());
        if (!Constant.isSuperAdmin(user.getUserId())) {
            // 非超级用户 ，按照登陆用的权限字符串，过滤没有权限的菜单
            streams = streams.filter(it -> {
                if (StringUtils.isBlank(it.getPerms())) return true;
                for (String p : it.getPerms().split(",")) {
                    if (subject.isPermitted(p)) return true;
                }
                return false;
            });
        }
        List<SysMenu> menus = streams.collect(Collectors.toList());
        return toTree(menus);
    }

    @Override
    public List<SysMenuTreeNode> queryAllMenuForTree() {
        List<SysMenu> datas = this.query(null, null);
        return toTree(datas);
    }

    /** 转换数据成树 **/
    private List<SysMenuTreeNode> toTree(List<SysMenu> menus) {
        List<SysMenuTreeNode> result = new ArrayList<>();
        // 获取第一层
        menus.stream().filter(e -> e.getType() == Constant.MenuType.CATALOG.getValue())
                .forEach(n -> {
                    try {
                        SysMenuTreeNode tn = SysMenuTreeNode.from(n);
                        result.add(tn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        // 递归遍历 子节点
        result.forEach(node -> {
            handleNextNode(node, menus);
        });

        return result;
    }

    /** 递归遍历子节点 **/
    private void handleNextNode(SysMenuTreeNode node, List<SysMenu> datas) {
        // 上一级节点的子节点
        List<SysMenuTreeNode> childs = new ArrayList<>();
        datas.stream().filter(n -> n.getParentId() == node.getMenuId()).forEach(n -> {
            try {
                SysMenuTreeNode tn = SysMenuTreeNode.from(n);
                childs.add(tn);
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
        if (childs.isEmpty()) return;
        node.setChildren(childs);
        childs.forEach(n -> {
            handleNextNode(n, datas);
        });
    }

    @Override
    public List<TreeNode<SysMenu>> queryTreeMenu() {
        List<SysMenu> datas = this.query(null, null);
        List<TreeNode<SysMenu>> result = new ArrayList<>();
        // 获取第一层
        datas.stream().filter(e -> e.getType() == Constant.MenuType.CATALOG.getValue())
                .forEach(n -> {
                    TreeNode<SysMenu> tn = new TreeNode<SysMenu>();
                    tn.setData(n);
                    result.add(tn);
                });
        // 递归遍历 子节点
        result.forEach(node -> {
            nextNode(node, datas);
        });

        return result;
    }

    /** 递归遍历子节点 **/
    private void nextNode(TreeNode<SysMenu> node, List<SysMenu> datas) {
        // 上一级节点的子节点
        List<TreeNode<SysMenu>> childs = new ArrayList<>();
        datas.stream().filter(n -> n.getParentId() == node.getData().getMenuId()).forEach(n -> {
            TreeNode<SysMenu> tn = new TreeNode<SysMenu>();
            tn.setData(n);
            childs.add(tn);
        });
        if (childs.isEmpty()) return;
        node.setChildren(childs);
        childs.forEach(n -> {
            nextNode(n, datas);
        });
    }



}
