package com.handsomedong.dynamic.datasource.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(excludeName = "org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration")
public class DynamicDatasourceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicDatasourceDemoApplication.class, args);
    }

}
