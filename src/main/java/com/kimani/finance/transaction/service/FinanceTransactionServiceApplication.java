package com.kimani.finance.transaction.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@ConfigurationPropertiesScan
public class FinanceTransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FinanceTransactionServiceApplication.class);
        springApplication.setAddCommandLineProperties(false);
        springApplication.run();
    }

}
