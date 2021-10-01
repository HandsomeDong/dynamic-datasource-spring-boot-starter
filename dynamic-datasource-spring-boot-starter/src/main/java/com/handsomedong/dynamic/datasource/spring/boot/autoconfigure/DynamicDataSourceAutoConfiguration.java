package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure;

import com.handsomedong.dynamic.datasource.aop.DynamicDataSourceAnnotationBeanPostProcessor;
import com.handsomedong.dynamic.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import com.handsomedong.dynamic.datasource.helper.DynamicDataSourceContextHolder;
import com.handsomedong.dynamic.datasource.helper.DynamicRoutingDataSource;
import com.handsomedong.dynamic.datasource.provider.DefaultDynamicDataSourceProvider;
import com.handsomedong.dynamic.datasource.provider.DynamicDataSourceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by HandsomeDong on 2021/9/29 0:50
 * 自动配置类
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(value = DataSourceAutoConfiguration.class, name = "com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure")
@Import(DruidDynamicDataSourceConfiguration.class)
public class DynamicDataSourceAutoConfiguration {
    private final DynamicDataSourceProperties properties;

    @Autowired
    @Qualifier("shardingDataSourceMap")
    private Map<String, DataSource> shardingDataSourceMap;

    public DynamicDataSourceAutoConfiguration(DynamicDataSourceProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new DefaultDynamicDataSourceProvider(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DynamicDataSourceProvider provider) throws SQLException {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();

        // 设置多数据源
        Map<String, DataSource> dataSourceMap = provider.loadDataSources();
        // 设置sharding-jdbc数据源
        dataSourceMap.putAll(shardingDataSourceMap);

        // 加载数据源
        dataSourceMap.forEach(dynamicRoutingDataSource::addDataSource);

        //设置默认数据源
        dynamicRoutingDataSource.setDefaultTargetDataSource(dataSourceMap.get(properties.getDefaultDatasourceKey()));
        DynamicDataSourceContextHolder.DEFAULT_KEY = properties.getDefaultDatasourceKey();
        return dynamicRoutingDataSource;
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
