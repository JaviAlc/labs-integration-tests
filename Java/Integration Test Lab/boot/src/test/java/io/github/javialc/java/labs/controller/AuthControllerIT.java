package io.github.javialc.java.labs.controller;

import io.github.javialc.java.labs.api.model.LoginDto;
import io.github.javialc.java.labs.api.model.LoginResponseDto;
import io.github.javialc.java.labs.api.model.UserRequestDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import java.time.Duration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class AuthControllerIT extends BaseTestIT {


    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0").withStartupTimeout(Duration.ofMinutes(5));

    @AfterAll
    static void tearDownAll() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        // Register dynamic properties so Spring uses the container's database
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    @SneakyThrows
    void testLogin() {
        // Given
        final LoginDto loginDto = new LoginDto();
        loginDto.setEmail(TEST_IT_MAIL);
        loginDto.setPassword(TEST_PASSWORD_IT);


        checkLogin(loginDto);
    }


    @Test
    @SneakyThrows
    void testRegisterUser() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("new_test_it@email.com");
        userRequestDto.setName("New Test User");
        userRequestDto.setPassword("password_it");

        // When - Then
        mockMvc.perform(post("/authentication/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userRequestDto)))
            .andExpect(status().isNoContent());


        // Check login with new user
        final LoginDto loginDto = new LoginDto();
        loginDto.setEmail(userRequestDto.getEmail());
        loginDto.setPassword(userRequestDto.getPassword());

        checkLogin(loginDto);

    }


    private void checkLogin(LoginDto loginDto) throws Exception {
        // When - Then
        mockMvc.perform(post("/authentication/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(loginDto)))
            .andExpect(status().isOk())
            .andExpect(mvcResult -> {
                LoginResponseDto loginResponseDto = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), LoginResponseDto.class);
                log.info("Login response: {}", loginResponseDto);
                assert loginResponseDto.getToken() != null;
            });
    }


}
