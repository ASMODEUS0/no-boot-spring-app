package org.aston.configuration;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@EnableTransactionManagement
@Configuration
public class JpaConfig {


    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("org.aston");
        sessionFactory.setHibernateProperties(hibernateProperties());
        log.info("Init " + sessionFactory);
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource(@Value("${app.datasource.url}") String jdbcUrl,
                                 @Value("${app.datasource.username}") String username,
                                 @Value("${app.datasource.password}") char[] password) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new Driver());
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(String.valueOf(password));
        log.info("Init " + dataSource);
        log.error("us: " + username + " pass: " +String.valueOf(password) + " jdbcUlr: " + jdbcUrl);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager(LocalSessionFactoryBean sessionFactory) {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory.getObject());
        log.info("Init " + transactionManager);
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");
        log.info("Init " + hibernateProperties);

        return hibernateProperties;
    }
}
