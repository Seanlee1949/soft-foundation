package com.example.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootApplication {
private static final Logger LOGGER = LoggerFactory.getLogger(BootApplication.class);
    public static void main(String[] args) {
        LOGGER.debug("………………开始项目启动………………");
        SpringApplication.run(BootApplication.class, args);
        LOGGER.debug("………………项目启动完成………………");
    }
}
