package configuration;

import org.junit.jupiter.api.BeforeAll;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
public abstract class IntegrationTest {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.0");

    @BeforeAll
    public static void setUp(){
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("app.datasource.url", container::getJdbcUrl);
        registry.add("app.datasource.password", container::getPassword);
        registry.add("app.datasource.username", container::getUsername);
    }
}
