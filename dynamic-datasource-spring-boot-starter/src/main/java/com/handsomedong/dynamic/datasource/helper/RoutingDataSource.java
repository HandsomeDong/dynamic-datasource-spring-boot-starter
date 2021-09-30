package com.handsomedong.dynamic.datasource.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import java.util.Map;

/**
 * Created by HandsomeDong on 2021/9/30 23:48
 * 路由到对应的数据源
 */
@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> targetDataSources;

    public RoutingDataSource() {
        setDefaultTargetDataSource(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("route datasource: {}", DynamicDataSourceContextHolder.getDSKey());
        return DynamicDataSourceContextHolder.getDSKey();
    }
}
