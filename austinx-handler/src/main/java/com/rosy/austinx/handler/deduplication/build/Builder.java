package com.rosy.austinx.handler.deduplication.build;

import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.handler.deduplication.DeduplicationParam;

public interface Builder {

    String DEDUPLICATION_CONFIG_PRE = "deduplication_";

    /**
     * 根据配置构建去重参数
     */
    DeduplicationParam build(String deduplication, TaskInfo taskInfo);
}