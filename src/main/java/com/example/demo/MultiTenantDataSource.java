package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MultiTenantDataSource extends AbstractRoutingDataSource implements DisposableBean {
    public static final String TENANT_ID = "demo";

    public MultiTenantDataSource() {
        Map<Object, Object> resolvedDataSources = new HashMap<>();
        DataSourceBuilder dataSourceBuilder = getDataSourceBuilder(TENANT_ID);
        resolvedDataSources.put(TENANT_ID, dataSourceBuilder.build());
        this.setDefaultTargetDataSource(resolvedDataSources.get(TENANT_ID));
        this.setTargetDataSources(resolvedDataSources);
        this.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant();
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

    @Override
    public void destroy() throws Exception {
        log.info("Cerrando datasource");
        try {
            this.getResolvedDataSources().values().forEach(dataSource -> {
                try {
                    dataSource.getConnection().close();
                } catch (Exception e) {
                    log.error("Error al cerrar la conexión", e);
                }
            });
            this.getResolvedDefaultDataSource().getConnection().close();
        } catch (Exception e) {
            log.error("Error al cerrar el datasource", e);
        }
    }
}
