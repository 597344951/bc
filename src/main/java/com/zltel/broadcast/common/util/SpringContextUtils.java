package com.zltel.broadcast.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * Spring Context 工具类
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
    /**日志输出对象**/
    public static final Logger logger = LoggerFactory.getLogger(SpringContextUtils.class);
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("Spring 容器加载成功-----------------");
        SpringContextUtils.applicationContext = applicationContext;
    }

    // -------------------------获取实例-------------------------
    /**
     * 根据实现类型查找SpringBean</br>
     * <b>注意: 这种方式如果获取实例 如果同一个接口有多个实现类被管理于Spring的情况, 是无法成功获取的.</b>
     * 
     * @param requiredType 被管理Bean的接口或者实现类
     * @return 实例对象
     */
    public static <T> T getBean(Class<T> requiredType) {
        return (T) applicationContext.getBean(requiredType);
    }


    /**
     * 根据SpringBean Id获取SpringBean
     * 
     * @param beanId Spring Bean Id
     * @return 实例对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        return (T) applicationContext.getBean(beanId);
    }

    /**
     * 根据实现类型查找SpringBean
     * 
     * @param requiredType 被管理Bean的接口或者实现类
     * @param args 如果有参数，则代表在找到这个bean定义后，通过构造方法或工厂方法或其他方法传入args参数来改变这个bean实例
     * @return 实例对象
     */
    public static <T> T getBean(Class<T> requiredType, Object... args) {
        return (T) applicationContext.getBean(requiredType, args);
    }

    /**
     * 根据SpringBean Id获取SpringBean
     * 
     * @param beanId Spring Bean Id
     * @param requiredType 被管理Bean的接口或者实现类
     * @return 实例对象
     */
    public static <T> T getBean(String beanId, Class<T> requiredType) {
        return (T) applicationContext.getBean(beanId, requiredType);
    }

    /**
     * 根据SpringBean Id获取SpringBean
     * 
     * @param beanId Spring Bean Id
     * @param args 如果有参数，则代表在找到这个bean定义后，通过构造方法或工厂方法或其他方法传入args参数来改变这个bean实例
     * @return 实例对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId, Object... args) {
        return (T) applicationContext.getBean(beanId, args);
    }

    // -------------------------获取实例-------------------------

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

    public static Class<? extends Object> getType(String name) {
        return applicationContext.getType(name);
    }

}
