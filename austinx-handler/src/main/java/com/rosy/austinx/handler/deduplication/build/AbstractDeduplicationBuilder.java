package com.rosy.austinx.handler.deduplication.build;

import com.alibaba.fastjson2.JSONObject;
import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.handler.deduplication.DeduplicationHolder;
import com.rosy.austinx.handler.deduplication.DeduplicationParam;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public abstract class AbstractDeduplicationBuilder implements Builder {

    protected Integer deduplicationType;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @PostConstruct
    public void init() {
        deduplicationHolder.putBuilder(deduplicationType, this);
    }

    public DeduplicationParam getParamsFromConfig(Integer key, String duplicationConfig, TaskInfo taskInfo) {
        JSONObject object = JSONObject.parseObject(duplicationConfig);
        if (Objects.isNull(object)) {
            return null;
        }
        DeduplicationParam deduplicationParam = JSONObject.parseObject(object.getString(DEDUPLICATION_CONFIG_PRE + key), DeduplicationParam.class);
        if (Objects.isNull(deduplicationParam)) {
            return null;
        }
        deduplicationParam.setTaskInfo(taskInfo);
        return deduplicationParam;
    }
}