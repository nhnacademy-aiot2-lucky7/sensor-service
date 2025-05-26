package com.nhnacademy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SensorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorServiceApplication.class, args);
    }

}
