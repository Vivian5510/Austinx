package com.rosy.austinx.service.api.impl.domain;

import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.service.api.domain.MessageParam;
import com.rosy.austinx.support.pipline.ProcessModel;
import com.rosy.main.domain.entity.MessageTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 发送消息任务模型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendTaskModel implements ProcessModel {

    /**
     * 消息模板Id
     */
    private Long messageTemplateId;

    /**
     * 请求参数
     */
    private List<MessageParam> messageParamList;

    /**
     * 发送任务的信息
     */
    private List<TaskInfo> taskInfo;

    /**
     * 撤回任务的信息
     */
    private MessageTemplate messageTemplate;

}
