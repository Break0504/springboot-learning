package com.breakzhang.web.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 18:31 2020/12/10
 * @description:
 */
@Controller
public class HelloController {


    @GetMapping("hello")
    @ResponseBody
    public String hello(String name) {
        return name;
    }





}
