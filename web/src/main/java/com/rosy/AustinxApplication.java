package com.rosy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ConfigurationPropertiesScan("com.rosy.framework.config")
@EnableAspectJAutoProxy
@MapperScan("com.rosy.**.mapper")
public class AustinxApplication {
    public static void main(String[] args) {
        /**
         * 如果你需要启动Apollo动态配置
         * 1、启动apollo
         * 2、将application.properties配置文件的 austin.apollo.enabled 改为true
         * 3、下方的property替换真实的ip和port
         */
        System.setProperty("apollo.config-service", "http://austin-apollo-config:8080");

        SpringApplication.run(AustinxApplication.class, args);
    }
}
