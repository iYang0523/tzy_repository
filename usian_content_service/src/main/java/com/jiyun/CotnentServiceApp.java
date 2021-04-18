package com.jiyun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.jiyun.mapper")
public class CotnentServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(CotnentServiceApp.class, args);
    }
}
