package com.rosy.austinx.cron.xxl.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 3y
 * 配置类
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "austinx.xxl-job.enabled", havingValue = "true")
public class XxlJobConfig {

    @Value("${xxl-job.admin.addresses}")
    private String adminAddresses;
    @Value("${xxl-job.executor.app-name}")
    private String appName;
    @Value("${xxl-job.executor.ip}")
    private String ip;
    @Value("${xxl-job.executor.port}")
    private int port;
    @Value("${xxl-job.access-token}")
    private String accessToken;
    @Value("${xxl-job.executor.log-path}")
    private String logPath;
    @Value("${xxl-job.executor.log-retention-days}")
    private int logRetentionDays;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        // 创建 XxlJobSpringExecutor 执行器
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppname(appName);
        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
        // 返回
        return xxlJobSpringExecutor;
    }

}