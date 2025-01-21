package com.rosy.austinx.support.mq.eventbus;

import com.alibaba.fastjson2.JSON;
import com.google.common.eventbus.EventBus;
import com.rosy.austinx.common.constant.MessageQueueConstant;
import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.support.mq.SendMqService;
import com.rosy.main.domain.entity.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author 3y
 * EventBus 发送实现类
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "austinx.mq.pipeline", havingValue = MessageQueueConstant.EVENT_BUS)
public class EventBusSendMqServiceImpl implements SendMqService {
    private final EventBus eventBus = new EventBus();

    @Autowired
    private EventBusListener eventBusListener;

    @Value("${austinx.business.topic.name}")
    private String sendTopic;
    @Value("${austinx.business.recall.topic.name}")
    private String recallTopic;

    /**
     * 单机 队列默认不支持 tagId过滤（单机无必要）
     */
    @Override
    public void send(String topic, String jsonValue, String tagId) {
        eventBus.register(eventBusListener);
        if (topic.equals(sendTopic)) {
            eventBus.post(JSON.parseArray(jsonValue, TaskInfo.class));
        } else if (topic.equals(recallTopic)) {
            eventBus.post(JSON.parseObject(jsonValue, MessageTemplate.class));
        }
    }

    @Override
    public void send(String topic, String jsonValue) {
        send(topic, jsonValue, null);
    }
}