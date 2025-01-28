package com.rosy.austinx.support.utils;

import com.rosy.austinx.common.domain.entity.TaskInfo;
import com.rosy.austinx.common.enums.ChannelType;
import com.rosy.austinx.common.enums.MessageType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * groupId 标识着每一个消费者组
 */
public class GroupIdMappingUtils {

    /**
     * 获取所有的groupIds
     * (不同的渠道不同的消息类型拥有自己的groupId)
     */
    public static List<String> getAllGroupIds() {
        List<String> groupIds = new ArrayList<>();
        for (ChannelType channelType : ChannelType.values()) {
            for (MessageType messageType : MessageType.values()) {
                groupIds.add(channelType.getCodeEn() + "." + messageType.getCodeEn());
            }
        }
        return groupIds;
    }


    /**
     * 根据TaskInfo获取当前消息的groupId
     */
    public static String getGroupIdByTaskInfo(TaskInfo taskInfo) {
        String channelCodeEn = Objects.requireNonNull(ChannelType.getEnumByCode(taskInfo.getSendChannel())).getCodeEn();
        String msgCodeEn = Objects.requireNonNull(MessageType.getEnumByCode(taskInfo.getMsgType())).getCodeEn();
        return channelCodeEn + "." + msgCodeEn;
    }
}