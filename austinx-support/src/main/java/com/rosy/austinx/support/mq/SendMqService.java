package com.rosy.austinx.support.mq;

/**
 * 发送数据至消息队列
 */
public interface SendMqService {
    /**
     * 发送消息
     */
    void send(String topic, String jsonValue, String tagId);


    /**
     * 发送消息
     */
    void send(String topic, String jsonValue);

}