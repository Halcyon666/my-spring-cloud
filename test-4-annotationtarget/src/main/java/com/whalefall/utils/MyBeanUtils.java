package com.whalefall.utils;

import com.whalefall.annotation.InvokeMethod;
import com.whalefall.domain.Info;
import com.whalefall.service.IService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Method;


/**
 * @Author: WhaleFall541
 * @Date: 2020/9/9 21:45
 */
@Component
public class MyBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MyBeanUtils.applicationContext = applicationContext;
    }

    public static IService getBeansByInfo(Info info) {
        Assert.notNull(info,"入参info为空");

        IService service = null;
        String bankType = info.getBankType();
        System.out.println("applicationContext >>> " + applicationContext);;

        return  (IService) applicationContext.getBean(bankType);
    }

    public void invoke(IService service,Info info) {
        Class<? extends IService> clazz = null;
        clazz = service.getClass();
        String tradeCode = info.getTradeCode();

        Method targetMethod;

        if (clazz != null) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                InvokeMethod annotation = method.getAnnotation(InvokeMethod.class);
                boolean annotationPresent = method.isAnnotationPresent(InvokeMethod.class);
                Object defaultValue = AnnotationUtils.getDefaultValue(InvokeMethod.class);
                System.out.println("defaultValue = " + defaultValue);
            }
        }

    }
}
