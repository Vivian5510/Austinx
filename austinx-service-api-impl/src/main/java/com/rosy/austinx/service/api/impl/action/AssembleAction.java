package com.rosy.austinx.service.api.impl.action;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.rosy.austinx.common.domain.dto.model.ContentModel;
import com.rosy.austinx.common.domain.entity.BasicResultVO;
import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.common.enums.ChannelType;
import com.rosy.austinx.common.enums.RespStatusEnum;
import com.rosy.austinx.support.utils.ContentHolderUtil;
import com.rosy.austinx.support.utils.TaskInfoUtils;
import com.rosy.austinx.service.api.domain.MessageParam;
import com.rosy.austinx.service.api.enums.BusinessCode;
import com.rosy.austinx.service.api.impl.domain.SendTaskModel;
import com.rosy.austinx.support.pipline.BusinessProcess;
import com.rosy.austinx.support.pipline.ProcessContext;
import com.rosy.main.domain.entity.MessageTemplate;
import com.rosy.main.service.IMessageTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 拼装参数
 */
@Slf4j
@Service
public class AssembleAction implements BusinessProcess<SendTaskModel> {

    @Autowired
    private IMessageTemplateService messageTemplateService;

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        Long messageTemplateId = sendTaskModel.getMessageTemplateId();

        try {
            Optional<MessageTemplate> messageTemplate = Optional.ofNullable(messageTemplateService.getById(messageTemplateId));
            if (messageTemplate.isEmpty()) {
                context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.TEMPLATE_NOT_FOUND));
                return;
            }
            if (BusinessCode.COMMON_SEND.getCode().equals(context.getCode())) {
                List<TaskInfo> taskInfos = assembleTaskInfo(sendTaskModel, messageTemplate.get());
                sendTaskModel.setTaskInfo(taskInfos);
            } else if (BusinessCode.RECALL.getCode().equals(context.getCode())) {
                sendTaskModel.setMessageTemplate(messageTemplate.get());
            }
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("assemble task fail! templateId:{}, e:{}", messageTemplateId, Throwables.getStackTraceAsString(e));
        }

    }

    /**
     * 组装 TaskInfo 任务消息
     */
    private List<TaskInfo> assembleTaskInfo(SendTaskModel sendTaskModel, MessageTemplate messageTemplate) {
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();
        List<TaskInfo> taskInfoList = new ArrayList<>();

        for (MessageParam messageParam : messageParamList) {

            TaskInfo taskInfo = TaskInfo.builder()
                    .messageTemplateId(messageTemplate.getId())
                    .businessId(TaskInfoUtils.generateBusinessId(messageTemplate.getId(), Integer.valueOf(messageTemplate.getTemplateType())))
                    .receiver(new HashSet<>(Arrays.asList(messageParam.getReceiver().split(String.valueOf(StrUtil.C_COMMA)))))
                    .idType(Integer.valueOf(messageTemplate.getIdType()))
                    .sendChannel(Integer.valueOf(messageTemplate.getSendChannel()))
                    .templateType(Integer.valueOf(messageTemplate.getTemplateType()))
                    .msgType(Integer.valueOf(messageTemplate.getMsgType()))
                    .shieldType(Integer.valueOf(messageTemplate.getShieldType()))
                    .sendAccount(Integer.valueOf(messageTemplate.getSendAccount()))
                    .contentModel(getContentModelValue(messageTemplate, messageParam)).build();

            taskInfoList.add(taskInfo);
        }

        return taskInfoList;

    }


    /**
     * 获取 contentModel，替换模板msgContent中占位符信息
     */
    private static ContentModel getContentModelValue(MessageTemplate messageTemplate, MessageParam messageParam) {

        // 得到真正的ContentModel 类型
        Integer sendChannel = Integer.valueOf(messageTemplate.getSendChannel());
        Class<? extends ContentModel> contentModelClass = ChannelType.getChanelModelClassByCode(sendChannel);


        // 得到模板的 msgContent 和 入参
        Map<String, String> variables = messageParam.getVariables();
        JSONObject jsonObject = JSON.parseObject(messageTemplate.getMsgContent());


        // 通过反射 组装出 contentModel
        Field[] fields = ReflectUtil.getFields(contentModelClass);
        ContentModel contentModel = ReflectUtil.newInstance(contentModelClass);
        for (Field field : fields) {
            String originValue = jsonObject.getString(field.getName());

            if (StrUtil.isNotBlank(originValue)) {
                String resultValue = ContentHolderUtil.replacePlaceHolder(originValue, variables);
                Object resultObj = JSONUtil.isTypeJSONObject(resultValue) ? JSONUtil.toBean(resultValue, field.getType()) : resultValue;
                ReflectUtil.setFieldValue(contentModel, field, resultObj);
            }
        }

        // 如果 url 字段存在，则在url拼接对应的埋点参数
        String url = (String) ReflectUtil.getFieldValue(contentModel, "url");
        if (StrUtil.isNotBlank(url)) {
            String resultUrl = TaskInfoUtils.generateUrl(url, messageTemplate.getId(), Integer.valueOf(messageTemplate.getTemplateType()));
            ReflectUtil.setFieldValue(contentModel, "url", resultUrl);
        }
        return contentModel;
    }
}
