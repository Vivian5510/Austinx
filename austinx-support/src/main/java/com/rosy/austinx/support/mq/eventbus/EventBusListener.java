package com.rosy.austinx.support.mq.eventbus;

import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.main.domain.entity.MessageTemplate;

import java.util.List;

/**
 * 监听器
 */
public interface EventBusListener {


    /**
     * 消费消息
     */
    void consume(List<TaskInfo> lists);

    /**
     * 撤回消息
     */
    void recall(MessageTemplate messageTemplate);
}