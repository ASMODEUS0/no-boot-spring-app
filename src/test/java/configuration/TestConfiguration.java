package configuration;

import jakarta.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@ComponentScan(basePackages = "org.aston")
public class TestConfiguration {

    @Bean
    public EntityManager entityManager(SessionFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager){
        return new TransactionTemplate(transactionManager);
    }
}
