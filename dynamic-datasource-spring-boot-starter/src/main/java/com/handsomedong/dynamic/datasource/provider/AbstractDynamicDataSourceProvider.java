package com.handsomedong.dynamic.datasource.provider;

import com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties.DruidCpConfig;
import com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties.HikariCpConfig;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by HandsomeDong on 2021/10/1 0:56
 */
@Slf4j
public abstract class AbstractDynamicDataSourceProvider implements DynamicDataSourceProvider {
    public Map<String, DataSource> loadDataSources(DynamicDataSourceProperties properties) throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        Iterator<Map.Entry<String, DruidCpConfig>> druidCpConfigIterator = properties.getDynamicDataSource().getDruid().entrySet().iterator();
        while (druidCpConfigIterator.hasNext()) {
            Map.Entry<String, DruidCpConfig> entry = druidCpConfigIterator.next();
            dataSourceMap.put(entry.getKey(), entry.getValue().createDruidDataSource(properties, entry.getKey()));
        }

        Iterator<Map.Entry<String, HikariCpConfig>> hikariCpConfigIterator = properties.getDynamicDataSource().getHikari().entrySet().iterator();
        while (druidCpConfigIterator.hasNext()) {
            Map.Entry<String, HikariCpConfig> entry = hikariCpConfigIterator.next();
            dataSourceMap.put(entry.getKey(), entry.getValue().createHikariDataSource(properties, entry.getKey()));
        }
        return dataSourceMap;
    }
}
