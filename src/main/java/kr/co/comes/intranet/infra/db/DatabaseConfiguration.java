package kr.co.comes.intranet.infra.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kr.co.comes.intranet.infra.properties.DbProperties;
import kr.co.comes.intranet.infra.properties.HikariProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableJpaAuditing(dateTimeProviderRef = JpaConfiguration.TRUNCATE_FRACTIONAL_SECONDS)
@EnableTransactionManagement
@Configuration
@EnableJpaRepositories(
        basePackages = DatabaseConfiguration.BASE_PACKAGE,
        transactionManagerRef = "transactionManager",
        entityManagerFactoryRef = "entityManager"
)
@EntityScan(basePackages = DatabaseConfiguration.BASE_PACKAGE)
@AllArgsConstructor
public class DatabaseConfiguration {

    static final String BASE_PACKAGE = "kr.co.comes.intranet.api";
    private final DbProperties dbProperties;
    private final HikariProperties hikariProperties;

    @Primary
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        return getDataSource(hikariProperties.getMaster(), dbProperties.getMaster());
    }

    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource() {
        return getDataSource(hikariProperties.getSlave(), dbProperties.getSlave());
    }

    private DataSource getDataSource(HikariConfig hikariConfig, DbProperties.DbProperty dbProperty) {
        hikariConfig.setJdbcUrl(dbProperty.getUrl());
        hikariConfig.setUsername(dbProperty.getUsername());
        hikariConfig.setPassword(dbProperty.getPassword());
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public DataSource routingDataSource(
            @Qualifier("masterDataSource") DataSource masterDataSource,
            @Qualifier("slaveDataSource") DataSource slaveDataSource
    ) {

        RoutingDataSource routingDataSource = new RoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();

        dataSourceMap.put(DataSourceType.READ_WRITE, masterDataSource);
        dataSourceMap.put(DataSourceType.READ_ONLY, slaveDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(slaveDataSource);
        return routingDataSource;
    }

    @Bean
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean entityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource
    ) {
        return builder.dataSource(dataSource)
                .packages(BASE_PACKAGE)
                .persistenceUnit(dbProperties.getMaster().getDatabase())
                .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManager") EntityManagerFactory entityManager
    ) {
        return new JpaTransactionManager(entityManager);
    }
}
