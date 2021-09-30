package com.handsomedong.dynamic.datasource.provider;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by HandsomeDong on 2021/10/1 0:02
 * 多数据源加载接口
 */
public interface DynamicDataSourceProvider {

    /**
     * 加载所有数据源
     *
     * @return 返回数据源映射。key是数据源名称
     * @throws SQLException 初始化连接时有可能会抛出异常
     */
    Map<String, DataSource> loadDataSources() throws SQLException;
}
