package com.example.curespr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class CureSprApplication {
    public static void main(String[] args) {
        SpringApplication.run(CureSprApplication.class, args);
    }
}
