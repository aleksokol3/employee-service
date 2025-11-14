package su.aleksokol3.employeeservice;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@DataJpaTest            // использ. для тестирования репозиторного слоя: загружает @Entity, @Repository и JPARepository; НЕ подгружает в контекст компоненты @Component, @Service, @Controller / @RestController, @Bean
@ActiveProfiles("test")
//@Sql({"classpath:sql/data.sql"})
public abstract class UnitTestBase {
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:17");
    @BeforeAll
    static void runContainer() {
        container.start();
    }
    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
