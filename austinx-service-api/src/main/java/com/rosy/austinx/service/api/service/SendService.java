package com.rosy.austinx.service.api.service;

import com.rosy.austinx.service.api.domain.BatchSendRequest;
import com.rosy.austinx.service.api.domain.SendRequest;
import com.rosy.austinx.service.api.domain.SendResponse;

public interface SendService {

    /**
     * 单文案发送接口
     */
    SendResponse send(SendRequest sendRequest);


    /**
     * 多文案发送接口
     */
    SendResponse batchSend(BatchSendRequest batchSendRequest);
}
