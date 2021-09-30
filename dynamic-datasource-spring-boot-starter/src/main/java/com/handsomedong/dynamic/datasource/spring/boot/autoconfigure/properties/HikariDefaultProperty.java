package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties;

import lombok.Data;

/**
 * Created by HandsomeDong on 2021/10/1 0:18
 * hikari配置
 */
@Data
public class HikariDefaultProperty {

    /**
     *  最小空闲连接，默认值10
     */
    private Integer minIdle = 10;

    /**
     * 最大连接数, 默认值为15
     */
    private Integer maxPoolSize = 15;

    /**
     * 连接获取超时时间
     *  默认 10000ms
     */
    private Long connectionTimeout = 10000L;

    /**
     * validationTimeout用来指定验证连接有效性的超时时间
     *  默认 5s
     */
    private Long validationTimeout = 5000L;

    /**
     * 空闲连接超时时间，默认值 600000 (10 分钟)
     *  idleTimeout 一定要小于  maxLifetime，否则会出现连接失活
     *  https://ayonel.github.io/2020/08/18/hikari-keeplive/
     */
    private Long idleTimeout = 600000L;

    /**
     * 连接最大生命周期, 默认值 1800000（30 分钟）
     */
    private Long maxLifetime = 1800000L;

    /**
     *  用于测试连接是否可用的查询语句
     */
    private String connectionTestQuery = "SELECT 1";

    /**
     *  用于新建连接收的执行语句
     */
    private String connectionInitSql = "SELECT 1";

    /**
     * jdbc驱动类
     */
    private String driverClassName = "";
}
