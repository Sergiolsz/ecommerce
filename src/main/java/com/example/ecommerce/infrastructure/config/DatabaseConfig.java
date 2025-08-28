package com.example.ecommerce.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.ecommerce.infrastructure.persistence.repository")
@EnableTransactionManagement
public class DatabaseConfig {

}