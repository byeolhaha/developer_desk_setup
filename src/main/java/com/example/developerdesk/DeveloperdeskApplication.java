package com.example.developerdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DeveloperdeskApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeveloperdeskApplication.class, args);
    }

}
