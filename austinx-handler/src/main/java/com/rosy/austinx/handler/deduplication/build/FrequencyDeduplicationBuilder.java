package com.rosy.austinx.handler.deduplication.build;

import cn.hutool.core.date.DateUtil;
import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.common.enums.AnchorState;
import com.rosy.austinx.common.enums.DeduplicationType;
import com.rosy.austinx.handler.deduplication.DeduplicationParam;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class FrequencyDeduplicationBuilder extends AbstractDeduplicationBuilder implements Builder {
    public FrequencyDeduplicationBuilder() {
        deduplicationType = DeduplicationType.FREQUENCY.getCode();
    }

    @Override
    public DeduplicationParam build(String deduplication, TaskInfo taskInfo) {
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplication, taskInfo);
        if (Objects.isNull(deduplicationParam)) {
            return null;
        }
        deduplicationParam.setDeduplicationTime((DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000);
        deduplicationParam.setAnchorState(AnchorState.RULE_DEDUPLICATION);
        return deduplicationParam;
    }
}