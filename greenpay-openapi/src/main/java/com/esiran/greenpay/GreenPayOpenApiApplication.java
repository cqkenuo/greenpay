package com.esiran.greenpay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.esiran.greenpay.**.mapper")
public class GreenPayOpenApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GreenPayOpenApiApplication.class,args);
    }
}
