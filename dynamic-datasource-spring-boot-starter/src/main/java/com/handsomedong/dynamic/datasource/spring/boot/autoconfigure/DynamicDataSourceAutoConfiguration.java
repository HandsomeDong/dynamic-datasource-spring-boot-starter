package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure;

import com.handsomedong.dynamic.datasource.aop.DynamicDataSourceAnnotationAdvisor;
import com.handsomedong.dynamic.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by HandsomeDong on 2021/9/29 0:50
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
    public DynamicDataSourceAnnotationAdvisor dynamicDataSourceAnnotationAdvisor() {
        return new DynamicDataSourceAnnotationAdvisor(new DynamicDataSourceAnnotationInterceptor());
    }
}
