package com.zltel.broadcast.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统功能项名称 (供系统日志功能使用)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogPoint {
    /**系统运行日志**/
    public static int TYPE_SYSTEM_LOG = 0;
    /** 终端日志 **/
    public static int TYPE_TERMINAL_LOG = 1;
    /** 节目管理日志 **/
    public static int TYPE_PUBLISH_LOG = 2;
    /** 素材管理日志 **/
    public static int TYPE_RESOURCE_MANAGE_LOG = 3;
    /** 权限管理日志 **/
    public static int TYPE_PERMS_LOG = 4;
    /** 基本设置日志 **/
    public static int TYPE_SYSTEM_SETTING_LOG = 5;
    /** 计划活动日志 **/
    public static int TYPE_TASK_LOG = 6;
    /** 文档管理日志 **/
    public static int TYPE_DOC_MANAGE_LOG = 7;


    int type() default TYPE_SYSTEM_LOG;

    String value() default "";
}
