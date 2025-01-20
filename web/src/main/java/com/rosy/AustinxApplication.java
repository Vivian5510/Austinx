package com.rosy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ConfigurationPropertiesScan("com.rosy.framework.config")
@EnableAspectJAutoProxy
@MapperScan("com.rosy.**.mapper")
public class AustinxApplication {
    public static void main(String[] args) {
        SpringApplication.run(AustinxApplication.class, args);
    }
}
