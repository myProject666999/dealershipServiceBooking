package com.dealership;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dealership.mapper")
public class DealershipApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealershipApplication.class, args);
    }
}
