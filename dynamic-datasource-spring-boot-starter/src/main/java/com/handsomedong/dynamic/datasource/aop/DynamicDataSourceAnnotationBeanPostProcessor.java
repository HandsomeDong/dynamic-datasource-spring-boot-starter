package com.handsomedong.dynamic.datasource.aop;

import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Created by HandsomeDong on 2021/9/30 22:35
 */
public class DynamicDataSourceAnnotationBeanPostProcessor extends AbstractAdvisingBeanPostProcessor implements BeanFactoryAware {

    private DynamicDataSourceAnnotationInterceptor interceptor;


    public DynamicDataSourceAnnotationBeanPostProcessor(DynamicDataSourceAnnotationInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        setBeforeExistingAdvisors(true);
        DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor(interceptor);
        advisor.setBeanFactory(beanFactory);
        this.advisor = advisor;
    }
}
