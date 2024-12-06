package com.seongjun.ordermodule.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class OrderController {

    @GetMapping
    public String test(){
        log.info("test11");
        return "";
    }
}
