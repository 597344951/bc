package com.zltel.broadcast.common.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.logger.Level;
import com.zltel.broadcast.common.logger.LogBean;
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
        logout.debug("execute method");
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        try {
            saveSysLog(point, time);
        } catch (Exception e) {
            logout.error("记录日志出错!", e);
        }
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
        logBean.setParams(JSON.toJSONString(filterArgs(args)));


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

    /**
     * 过滤参数<br>
     * <ul>
     * <li>HttpServlet</li>
     * <li>HttpServletRequest</li>
     * <li>HttpServletResponse</li>
     * <li>ModelAndView</li>
     * </ul>
     * 
     * @param args
     * @return
     */
    public Object[] filterArgs(Object[] args) {
        // 过滤 request, response 之类的参数
        return Arrays.stream(args).filter(arg -> {
            if (arg instanceof HttpServlet) return false;
            if (arg instanceof HttpServletRequest) return false;
            if (arg instanceof HttpServletResponse) return false;
            if (arg instanceof ModelAndView) return false;
            return true;
        }).toArray();
    }
}
