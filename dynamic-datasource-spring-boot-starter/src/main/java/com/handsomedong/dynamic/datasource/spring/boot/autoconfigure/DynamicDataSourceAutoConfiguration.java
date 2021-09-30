package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure;

import com.handsomedong.dynamic.datasource.aop.DynamicDataSourceAnnotationBeanPostProcessor;
import com.handsomedong.dynamic.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by HandsomeDong on 2021/9/29 0:50
 * 自动配置类
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class DynamicDataSourceAutoConfiguration {
    private final DynamicDataSourceProperties properties;

    public DynamicDataSourceAutoConfiguration(DynamicDataSourceProperties properties) {
        this.properties = properties;
        System.out.println("DynamicDataSourceProperties: " + properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceAnnotationInterceptor dynamicDataSourceAnnotationInterceptor() {
        return new DynamicDataSourceAnnotationInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceAnnotationBeanPostProcessor dynamicDataSourceAnnotationBeanPostProcessor(DynamicDataSourceAnnotationInterceptor interceptor) {
        return new DynamicDataSourceAnnotationBeanPostProcessor(interceptor);
    }
}
