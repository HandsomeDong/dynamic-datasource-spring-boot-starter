package com.handsomedong.dynamic.datasource.aop;

import com.handsomedong.dynamic.datasource.annotation.DataSource;
import com.handsomedong.dynamic.datasource.helper.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import java.lang.reflect.Method;

/**
 * Created by HandsomeDong on 2021/9/30 0:35
 * 核心拦截器
 */
@Slf4j
public class DynamicDataSourceAnnotationInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        String value = null;
        AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(method, DataSource.class);
        if (attributes != null) {
            value = attributes.getString("value");
        }
        if (value == null) {
            log.error("Cannot get the annotation DataSource value, please check your annotation!");
        }
        log.info("Get the annotation DataSource value: {}", value);
        try {
            DynamicDataSourceContextHolder.setDSKey(value);
            return methodInvocation.proceed();
        } finally {
            DynamicDataSourceContextHolder.clearDSKey();
        }
    }
}
