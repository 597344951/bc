package com.zltel.broadcast.incision.sola.bean;

public class ResultStatus {
    private int code;
    private String msg;

    /** 操作是否执行成功 **/
    public boolean isSuccess() {
        return code == 1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultStatus [code=" + code + ", msg=" + msg + "]";
    }

}
