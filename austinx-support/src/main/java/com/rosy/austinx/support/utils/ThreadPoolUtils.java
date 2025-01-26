package com.rosy.austinx.support.utils;

import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import com.rosy.austinx.support.config.ThreadPoolExecutorShutdownDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 线程池工具类
 */
@Component
public class ThreadPoolUtils {

    @Autowired
    private ThreadPoolExecutorShutdownDefinition shutdownDefinition;

    private static final String SOURCE_NAME = "austin";


    /**
     * 1. 将当前线程池 加入到 动态线程池内
     * 2. 注册 线程池 被Spring管理，优雅关闭
     */
    public void register(DtpExecutor dtpExecutor) {
        DtpRegistry.registerDtp(dtpExecutor, SOURCE_NAME);
        shutdownDefinition.registryExecutor(dtpExecutor);
    }
}
