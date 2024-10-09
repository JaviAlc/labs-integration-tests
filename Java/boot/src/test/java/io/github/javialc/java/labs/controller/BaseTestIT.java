package io.github.javialc.java.labs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.javialc.java.labs.authorizeserver.entity.AuthUserEntity;
import io.github.javialc.java.labs.authorizeserver.jpa.AuthUserJpaRepository;
import io.github.javialc.java.labs.mongodb.entity.UserEntity;
import io.github.javialc.java.labs.mongodb.jpa.UserJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Slf4j
public abstract class BaseTestIT {


    public static final String TEST_IT_MAIL = "test-it@email.com";
    public static final String TEST_PASSWORD_IT = "password-it";

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules();

    @Container
    @ClassRule
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0").withStartupTimeout(Duration.ofMinutes(5));

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthUserJpaRepository authUserJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;


    @BeforeEach
    void setUp() {
        // Clear all data before each test
        authUserJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
        // Add test user
        authUserJpaRepository.save(AuthUserEntity.builder()
            .email(TEST_IT_MAIL)
            .password(TEST_PASSWORD_IT).build());
        userJpaRepository.save(UserEntity.builder()
            .email(TEST_IT_MAIL)
            .name("Test User")
            .build());


    }

    @AfterEach
    void tearDown() {
        // Clear all data after each test
        authUserJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }


    @BeforeAll
    static void setUpAll() {
        mongoDBContainer.start(); // Init container before all tests
    }

    @AfterAll
    static void tearDownAll() {
        // Stop container after all tests
        if (mongoDBContainer != null) {
            mongoDBContainer.stop();
        }
    }

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        // Register dynamic properties so Spring uses the container's database
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

}
