package com.rosy.austinx.handler.discard;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.rosy.austinx.common.constant.CommonConstant;
import com.rosy.austinx.common.domain.entity.AnchorInfo;
import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.common.enums.AnchorState;
import com.rosy.austinx.support.service.ConfigService;
import com.rosy.austinx.support.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 丢弃模板消息
 */
@Service
public class DiscardMessageService {
    private static final String DISCARD_MESSAGE_KEY = "discardMsgIds";
    @Autowired
    private LogUtils logUtils;

    @Autowired
    private ConfigService config;

//    @Autowired
//    private LogUtils logUtils;


    /**
     * 丢弃消息，配置在apollo
     */
    public boolean isDiscard(TaskInfo taskInfo) {
        // 配置示例:	["1","2"]
        JSONArray array = JSON.parseArray(config.getProperty(DISCARD_MESSAGE_KEY, CommonConstant.EMPTY_VALUE_JSON_ARRAY));

        if (array.contains(String.valueOf(taskInfo.getMessageTemplateId()))) {
            logUtils.print(AnchorInfo.builder().businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).state(AnchorState.DISCARD.getCode()).build());
            return true;
        }
        return false;
    }

}