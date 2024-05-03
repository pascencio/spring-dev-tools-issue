package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class DemoConfiguration {

    public static final String TENANT_ID = "demo";

    @Bean
    public DataSource multiTenantDataSource() {
        Map<Object, Object> resolvedDataSources = new HashMap<>();
        String tenantId = TENANT_ID;
        DataSourceBuilder dataSourceBuilder = getDataSourceBuilder(tenantId);
        resolvedDataSources.put(tenantId, dataSourceBuilder.build());
        AbstractRoutingDataSource multiTenantDataSource = new MultiTenantDataSource();
        multiTenantDataSource.setDefaultTargetDataSource(resolvedDataSources.get(tenantId));
        multiTenantDataSource.setTargetDataSources(resolvedDataSources);
        multiTenantDataSource.afterPropertiesSet();
        return multiTenantDataSource;
    }

    private DataSourceBuilder getDataSourceBuilder(String tenantId) {
        log.info("Creando datasource para el tenant: [tenantId='{}']", tenantId);
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/demo?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false");
        dataSourceBuilder.username("demo");
        dataSourceBuilder.password("demo");
        return dataSourceBuilder;
    }

}
