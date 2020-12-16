package com.breakzhang.web.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 18:22 2020/12/10
 * @description:
 */

@MapperScan("com.breakzhang.web.test.dao")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }



}
