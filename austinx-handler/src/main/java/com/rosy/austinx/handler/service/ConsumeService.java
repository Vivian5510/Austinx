package com.rosy.austinx.handler.service;

import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.main.domain.entity.MessageTemplate;

import java.util.List;

/**
 * 消费消息服务
 */
public interface ConsumeService {

    /**
     * 从MQ拉到消息进行消费，发送消息
     */
    void consume2Send(List<TaskInfo> taskInfoLists);


    /**
     * 从MQ拉到消息进行消费，撤回消息
     */
    void consume2recall(MessageTemplate messageTemplate);


}