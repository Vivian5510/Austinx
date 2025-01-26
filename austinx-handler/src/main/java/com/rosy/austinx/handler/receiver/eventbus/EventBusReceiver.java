package com.rosy.austinx.handler.receiver.eventbus;

import com.alibaba.fastjson2.JSON;
import com.google.common.eventbus.Subscribe;
import com.rosy.austinx.common.constant.MessageQueueConstant;
import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.handler.service.ConsumeService;
import com.rosy.austinx.support.mq.eventbus.EventBusListener;
import com.rosy.main.domain.entity.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "austinx.mq.pipeline", havingValue = MessageQueueConstant.EVENT_BUS)
@Slf4j
public class EventBusReceiver implements EventBusListener {
    @Autowired
    private ConsumeService consumeService;

    @Override
    @Subscribe
    public void consume(List<TaskInfo> lists) {
        log.error(JSON.toJSONString(lists));
        consumeService.consume2Send(lists);
    }

    @Override
    @Subscribe
    public void recall(MessageTemplate messageTemplate) {

    }
}