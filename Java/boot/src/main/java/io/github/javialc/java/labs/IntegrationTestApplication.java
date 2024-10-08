package io.github.javialc.java.labs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class IntegrationTestApplication {

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {
        SpringApplication.run(IntegrationTestApplication.class, args);
    }

}
