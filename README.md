# dynamic-datasource-spring-boot-starter

# 简介
dynamic-datasource-spring-boot-starter 是一个基于springboot的快速集成多数据源的启动器，并且集成shardingsphere，支持sharding数据源。

# 特性
- 支持多数据源切换，适用于读写分离、一主多从、纯粹多库、混合模式
- 支持shardingsphere的sharding，暂不支持shardingsphere的读写分离（这个不需要用shardingsphere的读写分离了，直接用注解动态切换数据源，更灵活）、数据加密、影子库
- 提供并简化对Druid，HikariCp的快速集成

# 使用

1. 拉取仓库
2. 打包安装到本地maven库。cd dynamic-datasource-spring-boot-starter，在项目根目录运行 mvn clean install -Dmaven.test.skip=true
3. 通过maven引入依赖
```xml
    <dependency>
      <groupId>com.handsomedong</groupId>
      <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>
```
4. 启动类排除加载类，@SpringBootApplication(excludeName = "org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration")
5. 在配置文件添加数据源相关配置
```properties
debug=true

# 普通数据源配置 spring.dynamic.datasource.dynamicDatasource.{dsKey}.xxx
# 配置default数据源，这个是主数据源，如果没使用注解切换数据源，就是使用这个默认的数据源
spring.dynamic.datasource.dynamicDatasource.druid.default.driverClassName=com.mysql.cj.jdbc.Driver
spring.dynamic.datasource.dynamicDatasource.druid.default.jdbc-url=jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT
spring.dynamic.datasource.dynamicDatasource.druid.default.username=root
spring.dynamic.datasource.dynamicDatasource.druid.default.password=123456

# 配置其它数据源，比如我这里配置了个HandsomeDong数据源
spring.dynamic.datasource.dynamicDatasource.druid.HandsomeDong.driverClassName=com.mysql.cj.jdbc.Driver
spring.dynamic.datasource.dynamicDatasource.druid.HandsomeDong.jdbc-url=jdbc:mysql://localhost:3306/handsomedong?characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT
spring.dynamic.datasource.dynamicDatasource.druid.HandsomeDong.username=root
spring.dynamic.datasource.dynamicDatasource.druid.HandsomeDong.password=123456

# 配置sharding数据源。spring.dynamic.datasource.shardingsphere.names=dsKey1,dsKey2,dsKey3 然后再配置对应的sharding规则。具体可参考下面的配置

# 后面的配置，key和shardingsphere官网所描述的是一样的  https://shardingsphere.apache.org/document/4.1.1/cn/manual/sharding-jdbc/configuration/config-spring-boot/

# 这个是多个sharding数据源的配置，我们可以配置一个主数据源，一个从数据源。然后通过注解动态切换到不同的sharding数据源。这样就能先通过注解动态切换到不同的sharding数据源，再使用shardingsphere的分片规则路由到具体的库和表
spring.dynamic.datasource.shardingsphere.names=test1
# 下面是数据库连接配置，我这里的只是和官方的前缀不一样，我的是 spring.dynamic.datasource.shardingsphere.{dsKey} ，官方的是 spring.shardingsphere
spring.dynamic.datasource.shardingsphere.test1.datasource.names=handsomedongsharding1
spring.dynamic.datasource.shardingsphere.test1.datasource.handsomedongsharding1.type=com.alibaba.druid.pool.DruidDataSource
spring.dynamic.datasource.shardingsphere.test1.datasource.handsomedongsharding1.driver-class-name=com.mysql.jdbc.Driver
spring.dynamic.datasource.shardingsphere.test1.datasource.handsomedongsharding1.url=jdbc:mysql://localhost:3306/handsomedong?characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT
spring.dynamic.datasource.shardingsphere.test1.datasource.handsomedongsharding1.username=root
spring.dynamic.datasource.shardingsphere.test1.datasource.handsomedongsharding1.password=123456
# 下面是分片配置，我的key前缀是 spring.dynamic.datasource.shardingsphere.sharding.{dsKey}，官方的是 spring.shardingsphere.sharding
spring.dynamic.datasource.shardingsphere.sharding.test1.tables.user.actual-data-nodes=handsomedongsharding1.user_${0..1}
spring.dynamic.datasource.shardingsphere.sharding.test1.tables.user.table-strategy.inline.sharding-column=id
spring.dynamic.datasource.shardingsphere.sharding.test1.tables.user.table-strategy.inline.algorithm-expression=user_$->{id % 2}
# 下面是其它，我的key前缀是 spring.dynamic.datasource.props.{dsKey}.props，官方的是 spring.shardingsphere.props
spring.dynamic.datasource.props.test1.props.sql.show=true
```
6. 使用 @DataSource 切换数据源，以下是我的demo里复制过来的，可以看一下demo跑一下
```java
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> getUserList();

    @DataSource("HandsomeDong")
    List<User> getUserListFromHandsomeDong();

    @DataSource("test1")
    List<User> getShardingUser(Integer id);
}
```

