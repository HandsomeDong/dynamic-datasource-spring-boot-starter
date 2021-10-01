package com.handsomedong.dynamic.datasource.spring.boot.autoconfigure;

import com.google.common.base.Preconditions;
import com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties.ShardingsphereConfigurationProperties;
import com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties.ShardingspherePropsMapProperties;
import com.handsomedong.dynamic.datasource.spring.boot.autoconfigure.properties.ShardingspherePropsProperties;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.yaml.swapper.ShardingRuleConfigurationYamlSwapper;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.spring.boot.datasource.DataSourcePropertiesSetterHolder;
import org.apache.shardingsphere.spring.boot.util.DataSourceUtil;
import org.apache.shardingsphere.spring.boot.util.PropertyUtil;
import org.apache.shardingsphere.underlying.common.config.inline.InlineExpressionParser;
import org.apache.shardingsphere.underlying.common.exception.ShardingSphereException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by HandsomeDong on 2021/10/1 16:20
 * 集成shardingsphere，自动配置类
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ShardingsphereConfigurationProperties.class, ShardingspherePropsMapProperties.class})
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
public class DynamicShardingDataSourceConfiguration implements EnvironmentAware {
    private final Map<String, Map<String, DataSource>> shardingMap = new LinkedHashMap<>();
    private final ShardingsphereConfigurationProperties shardingsphereConfigurationProperties;
    private final ShardingspherePropsMapProperties shardingspherePropsMapProperties;

    @Bean
    @Qualifier
    public Map<String, DataSource> shardingDataSourceMap() throws SQLException {
        Map<String, DataSource> shardingDataSourceMap = new LinkedHashMap<>();
        for (String dataSourceName : shardingMap.keySet()) {
            shardingDataSourceMap.put(
                    dataSourceName,
                    ShardingDataSourceFactory.createDataSource(
                            shardingMap.get(dataSourceName),
                            (new ShardingRuleConfigurationYamlSwapper()).swap(shardingsphereConfigurationProperties.getSharding().get(dataSourceName)),
                            shardingspherePropsMapProperties.getProps().getOrDefault(
                                    dataSourceName, new ShardingspherePropsProperties()
                            ).getProps()
                    )
            );
        }
        return shardingDataSourceMap;
    }

    @Override
    public void setEnvironment(Environment environment) {
        String prefix = "spring.dynamic.datasource.shardingsphere.";
        // 多个shardingDataSource
        Iterator<String> shardingNameIterator = this.getDataSourceNames(environment, prefix).iterator();

        // 遍历shardingDataSource名
        while (shardingNameIterator.hasNext()) {
            String shardingName = shardingNameIterator.next();

            // 这个是sharding里的数据源名称
            Iterator<String> datasourceNameIterator = this.getDataSourceNames(environment, prefix + shardingName + ".datasource.").iterator();
            Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();
            while (datasourceNameIterator.hasNext()) {
                String dataSourcePrefix = prefix + shardingName + ".datasource.";
                String each = datasourceNameIterator.next();
                try {
                    dataSourceMap.put(each, this.getDataSource(environment, dataSourcePrefix, each));
                } catch (ReflectiveOperationException var6) {
                    throw new ShardingSphereException("Can't find datasource type!", var6);
                } catch (NamingException var7) {
                    throw new ShardingSphereException("Can't find JNDI datasource!", var7);
                }
                this.shardingMap.put(shardingName, dataSourceMap);
            }
        }
    }

    private List<String> getDataSourceNames(Environment environment, String prefix) {
        StandardEnvironment standardEnv = (StandardEnvironment) environment;
        standardEnv.setIgnoreUnresolvableNestedPlaceholders(true);
        return null == standardEnv.getProperty(prefix + "name") ? (new InlineExpressionParser(standardEnv.getProperty(prefix + "names"))).splitAndEvaluate() : Collections.singletonList(standardEnv.getProperty(prefix + "name"));
    }

    private DataSource getDataSource(Environment environment, String prefix, String dataSourceName) throws ReflectiveOperationException, NamingException {
        Map<String, Object> dataSourceProps = (Map) PropertyUtil.handle(environment, prefix + dataSourceName.trim(), Map.class);
        Preconditions.checkState(!dataSourceProps.isEmpty(), "Wrong datasource properties!");
        if (dataSourceProps.containsKey("jndi-name")) {
            return this.getJndiDataSource(dataSourceProps.get("jndi-name").toString());
        } else {
            DataSource result = DataSourceUtil.getDataSource(dataSourceProps.get("type").toString(), dataSourceProps);
            DataSourcePropertiesSetterHolder.getDataSourcePropertiesSetterByType(dataSourceProps.get("type").toString()).ifPresent((dataSourcePropertiesSetter) -> {
                dataSourcePropertiesSetter.propertiesSet(environment, prefix, dataSourceName, result);
            });
            return result;
        }
    }

    private DataSource getJndiDataSource(String jndiName) throws NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setResourceRef(true);
        bean.setJndiName(jndiName);
        bean.setProxyInterface(DataSource.class);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }

    @Generated
    public DynamicShardingDataSourceConfiguration(
            ShardingsphereConfigurationProperties shardingsphereConfigurationProperties,
            ShardingspherePropsMapProperties shardingspherePropsMapProperties
    ) {
        this.shardingsphereConfigurationProperties = shardingsphereConfigurationProperties;
        this.shardingspherePropsMapProperties = shardingspherePropsMapProperties;
    }
}
