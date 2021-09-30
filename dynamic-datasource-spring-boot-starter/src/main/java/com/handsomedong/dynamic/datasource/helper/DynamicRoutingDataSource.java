package com.handsomedong.dynamic.datasource.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HandsomeDong on 2021/9/30 23:48
 * 路由到对应的数据源
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> targetDataSources = new HashMap<>();

    public DynamicRoutingDataSource() {
        setTargetDataSources(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("Route datasource: {}", DynamicDataSourceContextHolder.getDSKey());
        return DynamicDataSourceContextHolder.getDSKey();
    }

    public void addDataSource(String dataSourceKey, DataSource dataSource) {
        targetDataSources.put(dataSourceKey, dataSource);
        log.info("Added datasource {} successfully ", dataSource);
    }
}
