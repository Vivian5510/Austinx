package com.rosy.austinx.service.api.impl.config;


import com.rosy.austinx.service.api.enums.BusinessCode;
import com.rosy.austinx.service.api.impl.action.AfterParamCheckAction;
import com.rosy.austinx.service.api.impl.action.AssembleAction;
import com.rosy.austinx.service.api.impl.action.PreParamCheckAction;
import com.rosy.austinx.service.api.impl.action.SendMqAction;
import com.rosy.austinx.support.pipline.ProcessController;
import com.rosy.austinx.support.pipline.ProcessTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * api层的pipeline配置类
 */
@Configuration
public class PipelineConfig {

    @Autowired
    private PreParamCheckAction preParamCheckAction;
    @Autowired
    private AssembleAction assembleAction;
    @Autowired
    private AfterParamCheckAction afterParamCheckAction;
    @Autowired
    private SendMqAction sendMqAction;

    /**
     * 普通发送执行流程
     * 1. 前置参数校验
     * 2. 组装参数
     * 3. 后置参数校验
     * 4. 发送消息至MQ
     */
    @Bean("commonSendTemplate")
    public ProcessTemplate commonSendTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList(preParamCheckAction, assembleAction,
                afterParamCheckAction, sendMqAction));
        return processTemplate;
    }


    /**
     * pipeline流程控制器
     * 后续扩展则加BusinessCode和ProcessTemplate
     */
    @Bean
    public ProcessController processController() {
        ProcessController processController = new ProcessController();
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        templateConfig.put(BusinessCode.COMMON_SEND.getCode(), commonSendTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }

}
