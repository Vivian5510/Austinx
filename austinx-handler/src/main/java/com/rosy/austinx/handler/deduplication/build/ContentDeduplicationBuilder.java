package com.rosy.austinx.handler.deduplication.build;

import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.common.enums.AnchorState;
import com.rosy.austinx.common.enums.DeduplicationType;
import com.rosy.austinx.handler.deduplication.DeduplicationParam;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ContentDeduplicationBuilder extends AbstractDeduplicationBuilder implements Builder {

    public ContentDeduplicationBuilder() {
        deduplicationType = DeduplicationType.CONTENT.getCode();
    }

    @Override
    public DeduplicationParam build(String deduplication, TaskInfo taskInfo) {
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplication, taskInfo);
        if (Objects.isNull(deduplicationParam)) {
            return null;
        }
        deduplicationParam.setAnchorState(AnchorState.CONTENT_DEDUPLICATION);
        return deduplicationParam;

    }
}