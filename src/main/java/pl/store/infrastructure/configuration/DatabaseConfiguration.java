package pl.store.infrastructure.configuration;

import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {

    public static final String CUSTOMER_TABLE = "CUSTOMER";
    public static final String CUSTOMER_TABLE_PKEY = "ID";
    public static final String OPINION_TABLE = "OPINION";
    public static final String OPINION_TABLE_PKEY = "ID";
    public static final String PRODUCER_TABLE = "PRODUCER";
    public static final String PRODUCER_TABLE_PKEY = "ID";
    public static final String PRODUCT_TABLE = "PRODUCT";
    public static final String PRODUCT_TABLE_PKEY = "ID";
    public static final String PURCHASE_TABLE = "PURCHASE";
    public static final String PURCHASE_TABLE_PKEY = "ID";
    @Bean
    public SimpleDriverDataSource simpleDriverDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new Driver());
        dataSource.setUrl("jdbc:postgresql://localhost:5432/zajavka_store");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(simpleDriverDataSource());
    }
}
