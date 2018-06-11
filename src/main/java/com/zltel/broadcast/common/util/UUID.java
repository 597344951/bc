package com.zltel.broadcast.common.util;

/**
 * 生成随机ID
 * UUID class
 *
 * @author Touss
 * @date 2018/5/10
 */
public class UUID {
    public static final String get() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }
}
