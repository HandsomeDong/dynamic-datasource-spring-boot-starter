package com.handsomedong.dynamic.datasource.aop;

import com.handsomedong.dynamic.datasource.annotation.DataSource;
import com.sun.istack.internal.NotNull;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Created by HandsomeDong on 2021/9/30 0:24
 */
public class DynamicDataSourceAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private final Advice advice;

    private final Pointcut pointcut;

    public DynamicDataSourceAnnotationAdvisor(@NotNull Advice advice) {
        this.advice = advice;
        this.pointcut = buildPointCut();
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }

    private Pointcut buildPointCut() {
        Pointcut annotationMethodPointCut = new AnnotationMatchingPointcut(DataSource.class, true);
        Pointcut methodAnnotation = AnnotationMatchingPointcut.forMethodAnnotation(DataSource.class);
        return new ComposablePointcut(annotationMethodPointCut).union(methodAnnotation);
    }
}
