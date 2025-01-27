package com.rosy.austinx.handler.deduplication.service;

import com.rosy.austinx.handler.deduplication.DeduplicationParam;

public interface DeduplicationService {

    /**
     * 去重
     *
     * @param param
     */
    void deduplication(DeduplicationParam param);
}