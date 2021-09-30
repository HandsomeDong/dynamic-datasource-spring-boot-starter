package com.handsomedong.dynamic.datasource.provider;

import com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by HandsomeDong on 2021/10/1 1:05
 */
@AllArgsConstructor
public class DefaultDynamicDataSourceProvider extends AbstractDynamicDataSourceProvider {
    private DynamicDataSourceProperties properties;

    @Override
    public Map<String, DataSource> loadDataSources() throws SQLException {
        return super.loadDataSources(properties);
    }
}
