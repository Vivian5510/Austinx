package com.rosy.austinx.service.api.service;

import com.rosy.austinx.service.api.domain.SendRequest;
import com.rosy.austinx.service.api.domain.SendResponse;

public interface RecallService {

    /**
     * 根据模板ID撤回消息
     *
     * @param sendRequest
     * @return
     */
    SendResponse recall(SendRequest sendRequest);
}
