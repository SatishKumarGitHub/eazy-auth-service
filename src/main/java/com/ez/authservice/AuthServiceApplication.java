package com.ez.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScans({
                @ComponentScan("com.ez.authservice.controller"),
                @ComponentScan("com.ez.authservice.config")})
//@EnableJdbcRepositories("com.ez.authservice.repository")
@EnableJpaRepositories("com.ez.authservice.repository")
@EntityScan("com.ez.authservice.model")
@EnableWebSecurity(debug = true)
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
