package com.luxinx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.luxinx.strategy","com.luxinx.service.impl","com.luxinx.db","com.luxinx.controller","com.luxinx.task"})
public class BootApplication {
    public static void main(String[] args){
        SpringApplication.run(BootApplication.class,args);
    }
}
