package com.breakzhang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan("com.breakzhang.dao")
@ImportResource("classpath:dubbo/dubbo-service.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
