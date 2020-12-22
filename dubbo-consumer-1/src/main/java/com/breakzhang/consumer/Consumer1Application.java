package com.breakzhang.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:dubbo/dubbo-ref.xml")
public class Consumer1Application {

    public static void main(String[] args) {
        SpringApplication.run(Consumer1Application.class, args);
    }

}
