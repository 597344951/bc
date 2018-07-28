package com.zltel.broadcast.common.exception;

/**
 * Reset Runtime Exception 自定义异常,定义用于返回异常业务逻辑消息。 <br/>
 * <b>注意</b>: 本异常的处理函数不会记录错误日志! {@link RestControllerExceptionHandlerAdvice#RRExceptionHandler}
 * 
 * @author Wangch
 */
public class RRException extends RuntimeException {

    /**
     * serialVersionUID:
     */
    private static final long serialVersionUID = 1L;
    private String msg;
    private int code = 500;

    /** 创建异常信息 并抛出 **/
    public static RRException makeThrow(String msg) {
        throw new RRException(msg);
    }

    public static RRException makeThrow(Exception e) {
        throw new RRException(e.getMessage());
    }

    public RRException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RRException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RRException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RRException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
