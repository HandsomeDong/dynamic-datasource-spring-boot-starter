package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by HandsomeDong on 2021/9/29 0:56
 */
@Data
@ConfigurationProperties(prefix = "handsomedong.test")
public class DynamicDataSourceProperties {
    private String hello = "world";
}
