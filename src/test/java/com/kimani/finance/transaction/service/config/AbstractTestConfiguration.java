//package com.kimani.finance.transaction.service.config;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.test.context.TestConfiguration;
//import redis.embedded.RedisServer;
//
//@Slf4j
//@TestConfiguration
//public class AbstractTestConfiguration {
//    private final RedisServer redisServer;
//
//    public AbstractTestConfiguration() {
//        this.redisServer = new RedisServer(6379);
//    }
//
//    @PostConstruct
//    public void postConstruct() {
//        redisServer.start();
//        log.info("Started Embedded Redis Server");
//    }
//
//    @PreDestroy
//    public void preDestroy() {
//        redisServer.stop();
//        log.info("Stopped Embedded Redis Server");
//    }
//}
