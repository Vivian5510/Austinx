package com.rosy.austinx.handler.deduplication.limit;

import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.handler.deduplication.DeduplicationParam;
import com.rosy.austinx.handler.deduplication.service.AbstractDeduplicationService;

import java.util.Set;

public interface LimitService {


    /**
     * 去重限制
     *
     * @param service 去重器对象
     * @param param   去重参数
     * @return 返回不符合条件的手机号码
     */
    Set<String> limitFilter(AbstractDeduplicationService service, TaskInfo taskInfo, DeduplicationParam param);

}