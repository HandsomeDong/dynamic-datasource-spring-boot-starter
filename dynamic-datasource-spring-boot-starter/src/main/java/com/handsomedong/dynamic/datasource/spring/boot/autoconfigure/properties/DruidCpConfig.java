package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;

/**
 * Created by HandsomeDong on 2021/10/1 0:21
 */
@Data
@Slf4j
public class DruidCpConfig {
    /**
     * 连接池类型，如果不设置自动查找 Druid > HikariCp
     */
    private Class<? extends DataSource> type;
    /**
     * JDBC driver
     * 这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName
     */
    private String driverClassName;
    /**
     * JDBC url 地址
     */
    private String jdbcUrl;
    /**
     * JDBC 用户名
     */
    private String username;
    /**
     * JDBC 密码
     */
    private String password;

    /**
     * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
     */
    private Integer initialSize;

    /**
     * 最大连接池数量
     */
    private Integer maxActive;

    /**
     * 最小连接池数量
     */
    private Integer minIdle;

    /**
     * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，
     * 缺省启用公平锁，并发效率会有所下降，
     * 如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
     */
    private Long maxWait;

    /**
     * 是否缓存preparedStatement，也就是PSCache。 
     * PSCache对支持游标的数据库性能提升巨大，比如说oracle。 
     * 在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。
     * 作者在5.5版本中使用PSCache，通过监控界面发现PSCache有缓存命中率记录， 
     * 该应该是支持PSCache。
     */
    private Boolean poolPreparedStatements;

    /**
     * 要启用PSCache，必须配置大于0，当大于0时，
     * poolPreparedStatements自动触发修改为true。
     * 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，
     * 可以把这个数值配置大一些，比如说100
     */
    private Integer maxOpenPreparedStatements;

    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句。
     * 如果validationQuery为null，testOnBorrow、testOnReturn、
     * testWhileIdle都不会起作用。
     */
    private String validationQuery;


    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    private Boolean testOnBorrow;

    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    private Boolean testOnReturn;

    /**
     * 建议配置为true，不影响性能，并且保证安全性。
     * 申请连接的时候检测，如果空闲时间大于
     * timeBetweenEvictionRunsMillis，
     * 执行validationQuery检测连接是否有效。
     */
    private Boolean testWhileIdle;

    /**
     * 在空闲连接回收器线程运行期间休眠的时间值,以毫秒为单位.
     * 有两个含义：
     * 1) Destroy线程会检测连接的间隔时间
     * 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
     */
    private Long timeBetweenEvictionRunsMillis;

    /**
     * 连接在池中保持空闲而不被空闲连接回收器线程,以毫秒为单位.
     */
    private Long minEvictableIdleTimeMillis;

    /**
     * 是否开启连接时输出错误日志，这样出现连接泄露时可以通过错误日志定位忘记关闭连接的位置
     */
    private Boolean logAbandoned;

    /**
     * 是否自动回收超时连接
     */
    private Boolean removeAbandoned;

    /**
     * 超时时间(以秒数为单位)
     */
    private Integer removeAbandonedTimeout;


    /**
     * 属性类型是字符串，通过别名的方式配置扩展插件，
     * 常用的插件有：
     * 监控统计用的filter:stat
     * 日志用的filter:log4j
     * 防御sql注入的filter:wall
     */
    private String filters;

    /**
     * 创建DRUID数据源
     *
     * @return 数据源
     */
    public DataSource createDruidDataSource(DynamicDataSourceProperties properties, String poolName) throws SQLException {

        this.checkProperty(this, properties.getDruidDefaultProperty());

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setName(poolName);
        dataSource.setUrl(this.getJdbcUrl());
        dataSource.setUsername(this.getUsername());
        dataSource.setPassword(this.getPassword());
        String driverClassName = StringUtils.hasText(this.driverClassName) ? this.driverClassName : properties.getDruidDefaultProperty().getDriverClassName();
        if (!StringUtils.hasText(driverClassName)) {
            driverClassName = properties.getDefaultDriverClassName();
        }
        dataSource.setDriverClassName(driverClassName);

        dataSource.setInitialSize(this.getInitialSize());
        dataSource.setMaxActive(this.getMaxActive());
        dataSource.setMinIdle(this.getMinIdle());
        dataSource.setMaxWait(this.getMaxWait());
        dataSource.setConnectionInitSqls(Collections.singletonList("set names utf8mb4"));
        dataSource.setPoolPreparedStatements(this.getPoolPreparedStatements());
        dataSource.setMaxOpenPreparedStatements(this.getMaxOpenPreparedStatements());

        dataSource.setValidationQuery(this.getValidationQuery());
        dataSource.setTestOnBorrow(this.getTestOnBorrow());
        dataSource.setTestOnReturn(this.getTestOnReturn());
        dataSource.setTestWhileIdle(this.getTestWhileIdle());

        dataSource.setTimeBetweenEvictionRunsMillis(this.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(this.getMinEvictableIdleTimeMillis());

        dataSource.setLogAbandoned(this.getLogAbandoned());
        dataSource.setRemoveAbandoned(this.getRemoveAbandoned());
        dataSource.setRemoveAbandonedTimeout(this.getRemoveAbandonedTimeout());


        try {
            dataSource.init();
        } catch (SQLException e) {
            log.error("druid数据源启动失败", e);
            throw e;
        }
        return dataSource;
    }

    private void checkProperty(DruidCpConfig druidCpConfig, DruidDefaultProperty defaultProperty) {

        if (druidCpConfig.getInitialSize() == null) {
            druidCpConfig.setInitialSize(defaultProperty.getInitialSize());
        }
        if (druidCpConfig.getMaxActive() == null) {
            druidCpConfig.setMaxActive(defaultProperty.getMaxActive());
        }
        if (druidCpConfig.getMinIdle() == null) {
            druidCpConfig.setMinIdle(defaultProperty.getMinIdle());
        }
        if (druidCpConfig.getMaxWait() == null) {
            druidCpConfig.setMaxWait(defaultProperty.getMaxWait());
        }
        if (druidCpConfig.getPoolPreparedStatements() == null) {
            druidCpConfig.setPoolPreparedStatements(defaultProperty.isPoolPreparedStatements());
        }
        if (druidCpConfig.getMaxOpenPreparedStatements() == null) {
            druidCpConfig.setMaxOpenPreparedStatements(defaultProperty.getMaxOpenPreparedStatements());
        }
        if (druidCpConfig.getValidationQuery() == null) {
            druidCpConfig.setValidationQuery(defaultProperty.getValidationQuery());
        }
        if (druidCpConfig.getTestOnBorrow() == null) {
            druidCpConfig.setTestOnBorrow(defaultProperty.isTestOnBorrow());
        }
        if (druidCpConfig.getTestOnReturn() == null) {
            druidCpConfig.setTestOnReturn(defaultProperty.isTestOnReturn());
        }
        if (druidCpConfig.getTestWhileIdle() == null) {
            druidCpConfig.setTestWhileIdle(defaultProperty.isTestWhileIdle());
        }
        if (druidCpConfig.getTimeBetweenEvictionRunsMillis() == null) {
            druidCpConfig.setTimeBetweenEvictionRunsMillis(defaultProperty.getTimeBetweenEvictionRunsMillis());
        }
        if (druidCpConfig.getMinEvictableIdleTimeMillis() == null) {
            druidCpConfig.setMinEvictableIdleTimeMillis(defaultProperty.getMinEvictableIdleTimeMillis());
        }
        if (druidCpConfig.getLogAbandoned() == null) {
            druidCpConfig.setLogAbandoned(defaultProperty.isLogAbandoned());
        }
        if (druidCpConfig.getRemoveAbandoned() == null) {
            druidCpConfig.setRemoveAbandoned(defaultProperty.isRemoveAbandoned());
        }
        if (druidCpConfig.getRemoveAbandonedTimeout() == null) {
            druidCpConfig.setRemoveAbandonedTimeout(defaultProperty.getRemoveAbandonedTimeout());
        }
        if (druidCpConfig.getFilters() == null) {
            druidCpConfig.setFilters(defaultProperty.getFilters());
        }
    }
}
