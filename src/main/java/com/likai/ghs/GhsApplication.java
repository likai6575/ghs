package com.likai.ghs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.likai.ghs.mapper")
public class GhsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GhsApplication.class, args);
    }

}
