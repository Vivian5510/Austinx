package com.rosy.austinx.handler.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.support.utils.GroupIdMappingUtils;
import com.rosy.austinx.handler.pending.Task;
import com.rosy.austinx.handler.pending.TaskPendingHolder;
import com.rosy.austinx.handler.service.ConsumeService;
import com.rosy.main.domain.entity.MessageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumeServiceImpl implements ConsumeService {
    private static final String LOG_BIZ_TYPE = "Receiver#consumer";
    private static final String LOG_BIZ_RECALL_TYPE = "Receiver#recall";
    @Autowired
    private ApplicationContext context;

    @Autowired
    private TaskPendingHolder taskPendingHolder;


    @Override
    public void consume2Send(List<TaskInfo> taskInfoLists) {
        String topicGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoLists.iterator()));
        for (TaskInfo taskInfo : taskInfoLists) {
            Task task = context.getBean(Task.class).setTaskInfo(taskInfo);
            taskPendingHolder.route(topicGroupId).execute(task);
        }
    }

    @Override
    public void consume2recall(MessageTemplate messageTemplate) {
    }
}