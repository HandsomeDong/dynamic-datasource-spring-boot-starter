package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties;

import lombok.Data;

/**
 * Created by HandsomeDong on 2021/10/1 0:16
 * druid配置
 */
@Data
public class DruidDefaultProperty {
    /**
     * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
     */
    private int initialSize = 0;

    /**
     * 最大连接池数量
     */
    private int maxActive = 8;

    /**
     * 最小连接池数量
     */
    private int minIdle = 0;

    /**
     * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，
     * 缺省启用公平锁，并发效率会有所下降，
     * 如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
     */
    private long maxWait = -1L;

    /**
     * 是否缓存preparedStatement，也就是PSCache。 
     * PSCache对支持游标的数据库性能提升巨大，比如说oracle。 
     * 在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。
     * 作者在5.5版本中使用PSCache，通过监控界面发现PSCache有缓存命中率记录， 
     * 该应该是支持PSCache。
     */
    private boolean poolPreparedStatements = false;

    /**
     * 要启用PSCache，必须配置大于0，当大于0时，
     * poolPreparedStatements自动触发修改为true。
     * 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，
     * 可以把这个数值配置大一些，比如说100
     */
    private  Integer maxOpenPreparedStatements = -1;

    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句。
     * 如果validationQuery为null，testOnBorrow、testOnReturn、
     * testWhileIdle都不会起作用。
     */
    private String validationQuery;


    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    private boolean testOnBorrow = false;

    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    private boolean testOnReturn = false;

    /**
     *
     * 建议配置为true，不影响性能，并且保证安全性。
     * 申请连接的时候检测，如果空闲时间大于
     * timeBetweenEvictionRunsMillis，
     * 执行validationQuery检测连接是否有效。
     */
    private boolean testWhileIdle = true;

    /**
     * 在空闲连接回收器线程运行期间休眠的时间值,以毫秒为单位.
     * 有两个含义：
     * 1) Destroy线程会检测连接的间隔时间
     * 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
     */
    private long timeBetweenEvictionRunsMillis = 60000L;

    /**
     * 连接在池中保持空闲而不被空闲连接回收器线程,以毫秒为单位.
     */
    private long minEvictableIdleTimeMillis = 1800000L;

    /**
     * 是否开启连接时输出错误日志，这样出现连接泄露时可以通过错误日志定位忘记关闭连接的位置
     *
     */
    private boolean logAbandoned = true;

    /**
     * 是否自动回收超时连接
     */
    private boolean removeAbandoned = true;

    /**
     *  超时时间(以秒数为单位)
     */
    private int removeAbandonedTimeout = 300;

    /**
     * 属性类型是字符串，通过别名的方式配置扩展插件，
     * 常用的插件有：
     * 监控统计用的filter:stat
     * 日志用的filter:log4j
     * 防御sql注入的filter:wall
     */
    private String filters = "";

    /**
     * jdbc驱动类
     */
    private String driverClassName = "";
}
