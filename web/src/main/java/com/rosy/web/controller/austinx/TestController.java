package com.rosy.web.controller.austinx;

import com.alibaba.fastjson.JSON;
import com.rosy.austinx.service.api.domain.MessageParam;
import com.rosy.austinx.service.api.domain.SendRequest;
import com.rosy.austinx.service.api.domain.SendResponse;
import com.rosy.austinx.service.api.enums.BusinessCode;
import com.rosy.austinx.service.api.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SendService sendService;

    @RequestMapping("/send")
    private String testSend() {
        SendRequest sendRequest1 = SendRequest.builder()
                .code(BusinessCode.COMMON_SEND.getCode())
                .messageTemplateId(1L)
                .messageParam(MessageParam.builder().receiver("13722222222").build()).build();

        SendRequest sendRequest3 = SendRequest.builder()
                .code(BusinessCode.COMMON_SEND.getCode())
                .messageTemplateId(3L)
                .messageParam(MessageParam.builder().receiver("13722222222").variables(Map.of("content", "666")).build()).build();

        SendResponse response = sendService.send(sendRequest3);
        return JSON.toJSONString(response);
    }
}
