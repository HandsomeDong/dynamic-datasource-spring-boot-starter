package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure;

import com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties.DataSourceConfig;
import com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties.DruidDefaultProperty;
import com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties.HikariDefaultProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by HandsomeDong on 2021/9/29 0:56
 * 多数据源配置
 */
@Data
@ConfigurationProperties(prefix = "spring.dynamic.datasource")
public class DynamicDataSourceProperties {
    /**
     * 默认数据源名称
     */
    private String defaultDatasourceKey = "default";


    /**
     * jdbc驱动类
     */
    private String defaultDriverClassName = "com.mysql.jdbc.Driver";

    /**
     * druid数据源默认配置项
     */
    private DruidDefaultProperty druidDefaultProperty = new DruidDefaultProperty();

    /**
     * hikari数据源默认配置项
     */
    private HikariDefaultProperty hikariDefaultProperty = new HikariDefaultProperty();

    /**
     * 所有数据源配置
     */
    private DataSourceConfig dynamicDataSource = new DataSourceConfig();

}
