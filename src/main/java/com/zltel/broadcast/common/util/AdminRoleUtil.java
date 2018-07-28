package com.zltel.broadcast.common.util;

import java.util.Set;
import java.util.function.Consumer;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 管理角色判断
 * 
 * @author wangch
 *
 */
public class AdminRoleUtil {
    private AdminRoleUtil() {}

    /**
     * 是否组织管理员
     * 
     * @return
     */
    public static boolean isOrgAdmin() {
        Subject subject = SecurityUtils.getSubject();
        // 组织管理员
        return subject.hasRole(Constant.AdministratorRole.ORG_ADMIN.getName());
    }

    /**
     * 是否平台管理员
     * 
     * @return
     */
    public static boolean isPlantAdmin() {
        Subject subject = SecurityUtils.getSubject();
        // 组织管理员
        return subject.hasRole(Constant.AdministratorRole.PLANT_ADMIN.getName());
    }
    /**
     * 是否组织管理员
     * @param roles 用户角色列表
     * @return 
     */
    public static boolean isOrgAdmin(Set<String> roles) {
        return roles.stream().anyMatch(str -> Constant.AdministratorRole.ORG_ADMIN.getName().equals(str));
    }
    /**
     * 是否平台管理员
     * @param roles 用户角色列表
     * @return
     */
    public static boolean isPlantAdmin(Set<String> roles) {
        return roles.stream().anyMatch(str -> Constant.AdministratorRole.PLANT_ADMIN.getName().equals(str));
    }

    /**
     * 对不同管理员角色 做不同的处理
     * 
     * @param ma 查询对象
     * @param orgHandler 组织管理员时处理
     * @param plantHandler 平台管理员时处理
     */
    public static <T> void handleAdminRole(T item, Consumer<T> orgHandler, Consumer<T> plantHandler) {
        // 平台管理员
        if (isPlantAdmin()) {
            plantHandler.accept(item);
        } else
        // 组织管理员
        if (isOrgAdmin()) {
            orgHandler.accept(item);
        }
    }

}
