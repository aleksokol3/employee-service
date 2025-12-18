package su.aleksokol3.employeeservice;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@Testcontainers // вместо Методы @BeforeAll/@AfterAll для старта и останова контейнера
public abstract class BaseIT {
    @Container
    @ServiceConnection // Автоматически связывает JDBC URL, login и password со Spring (вместо DynamicPropertySource)
    protected static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:17-alpine");

//    static {
//        container = new PostgreSQLContainer<>("postgres:17-alpine")
//                .withUsername("test")
//                .withPassword("test")
//                .withDatabaseName("employeeservice");
//    }

//    @BeforeAll
//    static void beforeAll() {
//        container.start();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        container.stop();
//    }
//
//    @DynamicPropertySource
//    static void postgresProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.username", container::getUsername);
//        registry.add("spring.datasource.password", container::getPassword);
//        registry.add("spring.datasource.url", container::getJdbcUrl);
//    }
}
