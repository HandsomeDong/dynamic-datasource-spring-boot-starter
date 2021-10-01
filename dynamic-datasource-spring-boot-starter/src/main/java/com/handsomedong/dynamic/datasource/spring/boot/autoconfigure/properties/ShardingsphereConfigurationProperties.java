package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Created by HandsomeDong on 2021/10/1 16:54
 */
@ConfigurationProperties(prefix = "spring.dynamic.datasource.shardingsphere")
@Data
public class ShardingsphereConfigurationProperties {
    private Map<String, ShardingRuleConfigurationProperties> sharding;
}
