package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties;

import com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Properties;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by HandsomeDong on 2021/10/1 0:23
 */
@Data
@Slf4j
public class HikariCpConfig {
    private static final long CONNECTION_TIMEOUT = SECONDS.toMillis(30);
    private static final long VALIDATION_TIMEOUT = SECONDS.toMillis(5);
    private static final long IDLE_TIMEOUT = MINUTES.toMillis(10);
    private static final long MAX_LIFETIME = MINUTES.toMillis(30);
    private static final int DEFAULT_POOL_SIZE = 10;

    private String username;
    private String password;
    private String driverClassName;
    private String jdbcUrl;

    private String catalog;
    private Long connectionTimeout;
    private Long validationTimeout;
    private Long idleTimeout;
    private Long leakDetectionThreshold;
    private Long maxLifetime;
    private Integer maxPoolSize;
    private Integer minIdle;

    private Long initializationFailTimeout;
    private String connectionInitSql;
    private String connectionTestQuery;
    private String dataSourceClassName;
    private String dataSourceJndiName;
    private String schema;
    private String transactionIsolationName;
    private Boolean isAutoCommit;
    private Boolean isReadOnly;
    private Boolean isIsolateInternalQueries;
    private Boolean isRegisterMbeans;
    private Boolean isAllowPoolSuspension;
    private Properties dataSourceProperties;
    private Properties healthCheckProperties;

    /**
     * 创建 Hikari 数据源
     *
     * @return Hikari 数据源
     */
    public HikariDataSource createHikariDataSource(DynamicDataSourceProperties properties, String poolName) {
        HikariConfig config = this.toHikariConfig(properties.getHikariDefaultProperty());
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setJdbcUrl(this.jdbcUrl);
        config.setPoolName(poolName);
        String driverClassName = StringUtils.hasText(this.driverClassName) ? this.driverClassName : properties.getHikariDefaultProperty().getDriverClassName();
        if (!StringUtils.hasText(driverClassName)) {
            driverClassName = properties.getDefaultDriverClassName();
        }
        if (StringUtils.hasText(driverClassName)) {
            config.setDriverClassName(driverClassName);
        }
        return new HikariDataSource(config);
    }

    /**
     * 转换为HikariCP配置
     *
     * @return HikariCP配置
     */
    public HikariConfig toHikariConfig(HikariDefaultProperty defaultProperty) {
        HikariConfig config = new HikariConfig();

        String tempSchema = schema == null ? this.getSchema() : schema;
        if (tempSchema != null) {
            try {
                Field schemaField = HikariConfig.class.getDeclaredField("schema");
                schemaField.setAccessible(true);
                schemaField.set(config, tempSchema);
            } catch (NoSuchFieldException e) {
                log.warn("动态数据源-设置了Hikari的schema属性，但当前Hikari版本不支持");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String tempCatalog = catalog == null ? this.getCatalog() : catalog;
        if (tempCatalog != null) {
            config.setCatalog(tempCatalog);
        }

        Long tempConnectionTimeout = this.connectionTimeout != null ? this.getConnectionTimeout() : defaultProperty.getConnectionTimeout();
        if (tempConnectionTimeout != null && !tempConnectionTimeout.equals(CONNECTION_TIMEOUT)) {
            config.setConnectionTimeout(tempConnectionTimeout);
        }

        Long tempValidationTimeout = this.validationTimeout != null ? this.getValidationTimeout() : defaultProperty.getValidationTimeout();
        if (tempValidationTimeout != null && !tempValidationTimeout.equals(VALIDATION_TIMEOUT)) {
            config.setValidationTimeout(tempValidationTimeout);
        }

        Long tempIdleTimeout = this.idleTimeout != null ? this.getIdleTimeout() : defaultProperty.getIdleTimeout();
        if (tempIdleTimeout != null && !tempIdleTimeout.equals(IDLE_TIMEOUT)) {
            config.setIdleTimeout(tempIdleTimeout);
        }

        Long tempMaxLifetime = this.maxLifetime != null ? this.getMaxLifetime() : defaultProperty.getMaxLifetime();
        if (tempMaxLifetime != null && !tempMaxLifetime.equals(MAX_LIFETIME)) {
            config.setMaxLifetime(tempMaxLifetime);
        }

        Integer tempMaxPoolSize = this.maxPoolSize != null ? this.getMaxPoolSize() : defaultProperty.getMaxPoolSize();
        if (tempMaxPoolSize != null && !tempMaxPoolSize.equals(-1)) {
            config.setMaximumPoolSize(tempMaxPoolSize);
        }

        Integer tempMinIdle = this.minIdle != null ? this.getMinIdle() : defaultProperty.getMinIdle();
        if (tempMinIdle != null && !tempMinIdle.equals(-1)) {
            config.setMinimumIdle(tempMinIdle);
        }

        String tempConnectionInitSql = this.connectionInitSql != null ? this.getConnectionInitSql() : defaultProperty.getConnectionInitSql();
        if (tempConnectionInitSql != null) {
            config.setConnectionInitSql(tempConnectionInitSql);
        }

        String tempConnectionTestQuery = this.connectionTestQuery != null ? this.getConnectionTestQuery() : defaultProperty.getConnectionTestQuery();
        if (tempConnectionTestQuery != null) {
            config.setConnectionTestQuery(tempConnectionTestQuery);
        }

        if (this.leakDetectionThreshold != null) {
            config.setLeakDetectionThreshold(leakDetectionThreshold);
        }


        if (this.transactionIsolationName != null) {
            config.setTransactionIsolation(transactionIsolationName);
        }

        if (this.dataSourceClassName != null) {
            config.setDataSourceClassName(dataSourceClassName);
        }

        if (this.dataSourceJndiName != null) {
            config.setDataSourceJNDI(this.dataSourceJndiName);
        }

        if (this.initializationFailTimeout != null) {
            config.setInitializationFailTimeout(initializationFailTimeout);
        }

        if (this.dataSourceJndiName != null) {
            config.setDataSourceJNDI(dataSourceJndiName);
        }

        if (isAutoCommit != null && isAutoCommit.equals(Boolean.FALSE)) {
            config.setAutoCommit(false);
        }

        if (this.isReadOnly != null) {
            config.setReadOnly(this.isReadOnly);
        }

        if (this.isIsolateInternalQueries != null) {
            config.setIsolateInternalQueries(this.isIsolateInternalQueries);
        }

        if (this.isRegisterMbeans != null) {
            config.setRegisterMbeans(this.isRegisterMbeans);
        }

        if (this.isAllowPoolSuspension != null) {
            config.setAllowPoolSuspension(this.isAllowPoolSuspension);
        }

        if (this.dataSourceProperties != null) {
            config.setDataSourceProperties(this.dataSourceProperties);
        }

        if (this.healthCheckProperties != null) {
            config.setHealthCheckProperties(this.healthCheckProperties);
        }
        return config;
    }
}
