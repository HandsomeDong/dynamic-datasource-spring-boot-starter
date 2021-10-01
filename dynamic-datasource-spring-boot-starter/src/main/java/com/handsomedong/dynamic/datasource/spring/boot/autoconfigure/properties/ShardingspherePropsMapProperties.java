package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Created by HandsomeDong on 2021/10/1 16:55
 */
@ConfigurationProperties(
        prefix = "spring.dynamic.datasource"
)
@Data
public class ShardingspherePropsMapProperties {
    private Map<String, ShardingspherePropsProperties> props;
}
