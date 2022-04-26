package com.techgeeknext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
 
@SpringBootApplication
@EnableEurekaClient
public class SpringBootWebclientExampleApp {
 
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebclientExampleApp.class, args);
    }
} 