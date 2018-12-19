package com.zhoubi.graindepot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by Administrator on 2018-12-5.
 */
@EnableEurekaServer  //����һ������ע�������ṩ������Ӧ�ý��жԻ�
@SpringBootApplication
public class GraindeportEurakaApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(GraindeportEurakaApplication.class, args);
    }
}
