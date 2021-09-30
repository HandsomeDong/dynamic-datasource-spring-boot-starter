package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HandsomeDong on 2021/10/1 0:23
 */
@Data
public class DataSourceConfig {
    /**
     * druid数据源
     */
    private Map<String, DruidCpConfig> druid = new HashMap<>();

    /**
     * hikari数据源
     */
    private Map<String, HikariCpConfig> hikari = new HashMap<>();
}
