package com.rosy.austinx.handler.handler;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.base.Throwables;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TencentSmsScript {
    public static void send(String phone, String content, String secretId, String secretKey, String sdkAppId) {

        try {

            Credential cred = new Credential(secretId, secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);

            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = new String[]{phone};
            req.setPhoneNumberSet(phoneNumberSet1);
            req.setSmsSdkAppId(sdkAppId);
            req.setSignName("Java3y公众号");
            req.setTemplateId("1182097");
            String[] templateParamSet1 = {content};
            req.setTemplateParamSet(templateParamSet1);
            req.setSessionContext(IdUtil.fastSimpleUUID());

            SendSmsResponse response = client.SendSms(req);

            log.info(JSON.toJSONString(response));
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
        }
    }

    public static void main(String[] args) {
//        send("xxx-xxxx-xxxx", "xxxxxx", "xxx", "xxx", "xxx");
        log.info("Success to send message With TencentSms");
    }
}
