package com.rosy.austinx.handler.pending;

import com.dtp.core.thread.DtpExecutor;
import com.rosy.austinx.common.utils.GroupIdMappingUtils;
import com.rosy.austinx.handler.config.HandlerThreadPoolConfig;
import com.rosy.austinx.support.utils.ThreadPoolUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 存储 每种消息类型 与 TaskPending 的关系
 */
@Component
public class TaskPendingHolder {
    @Autowired
    private ThreadPoolUtils threadPoolUtils;

    private Map<String, ExecutorService> taskPendingHolder = new HashMap<>(32);

    /**
     * 获取得到所有的groupId
     */
    private static List<String> groupIds = GroupIdMappingUtils.getAllGroupIds();

    /**
     * 给每个渠道，每种消息类型初始化一个线程池
     */
    @PostConstruct
    public void init() {
        /**
         * example ThreadPoolName:austin.im.notice
         * <p>
         * 可以通过apollo配置：dynamic-tp-apollo-dtp.yml  动态修改线程池的信息
         */
        for (String groupId : groupIds) {
            DtpExecutor executor = HandlerThreadPoolConfig.getExecutor(groupId);
            threadPoolUtils.register(executor);

            taskPendingHolder.put(groupId, executor);
        }
    }

    /**
     * 得到对应的线程池
     */
    public ExecutorService route(String groupId) {
        return taskPendingHolder.get(groupId);
    }
}