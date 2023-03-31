package project.goorm.queryserver.common.configuration.rdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import project.goorm.queryserver.common.configuration.redis.RedisInitialization;

@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractContainerTestBase {

    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest")
                .withUsername("username")
                .withPassword("password")
                .withDatabaseName("goormtest");
        MY_SQL_CONTAINER.start(); // MYSQL컨테이너 명시적으로 시작
    }

    @Autowired
    private RDBInitialization rdbInitialization;

    @Autowired
    private RedisInitialization redisInitialization;

    @Autowired
    protected ObjectMapper objectMapper;

    @AfterEach
    void setUP() {
        redisInitialization.init();
        rdbInitialization.truncateAllEntity();
    }
}
