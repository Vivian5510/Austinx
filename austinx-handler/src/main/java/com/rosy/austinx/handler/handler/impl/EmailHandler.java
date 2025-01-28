package com.rosy.austinx.handler.handler.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.base.Throwables;
import com.rosy.austinx.common.domain.dto.model.EmailContentModel;
import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.common.enums.ChannelType;
import com.rosy.austinx.handler.handler.BaseHandler;
import com.rosy.austinx.handler.handler.Handler;
import com.rosy.austinx.support.utils.AustinFileUtils;
import com.rosy.main.domain.entity.MessageTemplate;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

/**
 * 邮件发送处理
 */
@Component
@Slf4j
public class EmailHandler extends BaseHandler implements Handler {


    @Value("${austin.business.upload.crowd.path}")
    private String dataPath;

    public EmailHandler() {
        channelCode = ChannelType.EMAIL.getCode();
    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        EmailContentModel emailContentModel = (EmailContentModel) taskInfo.getContentModel();
        MailAccount account = getAccountConfig(taskInfo.getSendAccount());
        try {
            File file = StrUtil.isNotBlank(emailContentModel.getUrl()) ? AustinFileUtils.getRemoteUrl2File(dataPath, emailContentModel.getUrl()) : null;
            String result = Objects.isNull(file) ? MailUtil.send(account, taskInfo.getReceiver(), emailContentModel.getTitle(), emailContentModel.getContent(), true) :
                    MailUtil.send(account, taskInfo.getReceiver(), emailContentModel.getTitle(), emailContentModel.getContent(), true, file);
        } catch (Exception e) {
            log.error("EmailHandler#handler fail!{},params:{}", Throwables.getStackTraceAsString(e), taskInfo);
            return false;
        }
        return true;
    }

    /**
     * 获取账号信息和配置
     */
    private MailAccount getAccountConfig(Integer sendAccount) {
        //MailAccount account = accountUtils.getAccountById(sendAccount, MailAccount.class);

        /**
         * 修改 user/from/pass
         */
        String defaultConfig = "{\"host\":\"smtp.qq.com\",\"port\":465,\"user\":\"403686131@qq.com\",\"pass\":\"123123123\",\"from\":\"403686131@qq.com\",\"starttlsEnable\":\"true\",\"auth\":true,\"sslEnable\":true}";
        MailAccount account = JSON.parseObject(defaultConfig, MailAccount.class);
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            account.setAuth(account.isAuth()).setStarttlsEnable(account.isStarttlsEnable()).setSslEnable(account.isSslEnable()).setCustomProperty("mail.smtp.ssl.socketFactory", sf);
            account.setTimeout(25000).setConnectionTimeout(25000);
        } catch (Exception e) {
            log.error("EmailHandler#getAccount fail!{}", Throwables.getStackTraceAsString(e));
        }
        return account;
    }

    @Override
    public void recall(MessageTemplate messageTemplate) {

    }
}