package com.fred.graindepot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2018-12-5.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class UserApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
