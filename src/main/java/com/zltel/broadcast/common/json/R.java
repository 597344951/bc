package com.zltel.broadcast.common.json;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /** 正常请求代码 **/
    public static final int CODE_OK = 200;
    /** 请求失败 **/
    public static final int CODE_FAIL = 500;
    /** 未登陆 **/
    public static final int CODE_UNAUTH = 0;
    /** 权限不足 **/
    public static final int CODE_AUTH_FAIL = 2;

    public R() {
        put("status", true);
        put("code", 200);
    }

    /** 请求成功 **/
    public static R ok() {
        return ok("请求成功");
    }

    public static R ok(String msg) {
        R r = new R();
        return r.setMsg(msg);
    }



    /** 请求错误 **/
    public static R error() {
        return error("未知异常，请联系管理员");
    }

    public static R error(String msg) {
        R r = new R();
        r.setCode(CODE_FAIL);
        return r.setStatus(false).setMsg(msg);
    }

    /** 设置请求状态 **/
    public R setStatus(boolean status) {
        this.put("status", status);
        return this;
    }

    /** 设置请求消息 **/
    public R setMsg(String msg) {
        this.put("msg", msg);
        return this;
    }

    /** 设置 返回数据 **/
    public R setData(Object data) {
        this.put("data", data);
        return this;
    }

    /**
     * 批量设置返回数据
     * 
     * @param datas {返回数据名:返回数据}
     * @return
     */
    public R putData(Map<String, Object> datas) {
        this.putAll(datas);
        return this;
    }

    /** 设置请求代码 **/
    public R setCode(int code) {
        this.put("code", code);
        return this;
    }

    public R setPager(Object v) {
        this.put("pager", v);
        return this;
    }
    /**
     * 设置数据
     * @param key 
     * @param data
     * @return
     */
    public R set(String key,Object data) {
        this.put(key, data);
        return this;
    }


}
