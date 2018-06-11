package com.zltel.broadcast.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.logger.Level;
import com.zltel.broadcast.common.logger.LogBean;
import com.zltel.broadcast.common.logger.LogFactory;
import com.zltel.broadcast.common.logger.LogQueue;
import com.zltel.broadcast.common.util.HttpContextUtils;
import com.zltel.broadcast.common.util.IPUtils;
import com.zltel.broadcast.um.bean.SysUser;


/**
 * LogPoint 拦截日志记录
 * 
 * @author wangch
 */
@Aspect
@Component
public class LogPointAspect {

    public static final Logger logout = LoggerFactory.getLogger(LogPointAspect.class);


    @Pointcut("@annotation(com.zltel.broadcast.common.annotation.LogPoint)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveSysLog(point, time);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogBean logBean = new LogBean();
        logBean.setCostTime(time);
        logBean.setLevel(Level.LEVEL_INFO);
        LogPoint lp = method.getAnnotation(LogPoint.class);
        if (lp != null) {
            // 注解上的描述
            logBean.setOperation(lp.value());
            logBean.setType(lp.type());
            logBean.setMsg("记录" + lp.value() + " 执行");
        }

        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        String fmn = className + "." + methodName + "()";
        logBean.setMethod(fmn);

        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            logBean.setParams(JSON.toJSONString(args));
        } catch (Exception e) {}

        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if (request != null) {
            String ip = IPUtils.getIpAddr(request);
            logBean.setIp(ip);
        }
        // 用户名
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (null != user) logBean.setUsername(user.getUsername());

        logBean.setDate(new Date());
        LogQueue.add(logBean);
    }
}
