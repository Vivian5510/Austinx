package com.rosy.austinx.support.utils;

import cn.monitor4all.logRecord.bean.LogDTO;
import cn.monitor4all.logRecord.service.DataPipelineService;
import com.alibaba.fastjson2.JSON;
import com.google.common.base.Throwables;
import com.rosy.austinx.common.domain.entity.AnchorInfo;
import com.rosy.austinx.common.domain.entity.LogParam;
import com.rosy.austinx.support.mq.SendMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 所有的日志都存在
 *
 * @author 3y
 */
@Slf4j
@Component
public class LogUtils implements DataPipelineService {

    @Autowired
    private SendMqService sendMqService;

    @Value("${austin.business.log.topic.name}")
    private String topicName;

    /**
     * 方法切面的日志 @OperationLog 所产生
     */
    public boolean createLog(LogDTO logDTO) {
        log.info(JSON.toJSONString(logDTO));
        return true;
    }

    /**
     * 记录当前对象信息
     */
    public void print(LogParam logParam) {
        logParam.setTimestamp(System.currentTimeMillis());
        log.info(JSON.toJSONString(logParam));
    }

    /**
     * 记录打点信息
     */
    public void print(AnchorInfo anchorInfo) {
        anchorInfo.setLogTimestamp(System.currentTimeMillis());
        String message = JSON.toJSONString(anchorInfo);
        log.info(message);

        try {
            sendMqService.send(topicName, message);
        } catch (Exception e) {
            log.error("LogUtils#print send mq fail! e:{},params:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(anchorInfo));
        }
    }

    /**
     * 记录当前对象信息和打点信息
     */
    public void print(LogParam logParam, AnchorInfo anchorInfo) {
        print(anchorInfo);
        print(logParam);
    }
}