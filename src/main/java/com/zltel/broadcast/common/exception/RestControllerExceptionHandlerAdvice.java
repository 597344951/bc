package com.zltel.broadcast.common.exception;

import java.util.Map;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zltel.broadcast.common.json.R;

/** 添加@RestControllerAdvice 注解 **/
@RestControllerAdvice
public class RestControllerExceptionHandlerAdvice {

    public static final Logger log =
            LoggerFactory.getLogger(RestControllerExceptionHandlerAdvice.class);

    /** 访问权限不足 **/
    @ExceptionHandler(value = {UnauthorizedException.class})
    public Map<String, Object> UnauthorizedException(UnauthorizedException ue) {
        return R.error("访问权限不足:" + ue.getMessage()).setCode(R.CODE_AUTH_FAIL);
    }

    /** 没有登陆系统 **/
    @ExceptionHandler(value = {UnauthenticatedException.class})
    public Map<String, Object> UnauthenticatedException(UnauthenticatedException ue) {
        return R.error("验证信息失效,请重新登陆系统!").setCode(R.CODE_UNAUTH);
    }

    /** 自定义 业务异常 处理**/
    @ExceptionHandler(RRException.class)
    public Map<String, Object> RRExceptionHandler(RRException ex) {
        R r = R.error(ex.getMessage());
        return r;
    }

    @ExceptionHandler(Exception.class)
    public Map<String, Object> myErrorHandler(Exception ex) {
        R r = R.error("系统出错,请联系管理员。");
        log.error(ex.getMessage(),ex);
        return r;
    }

}
