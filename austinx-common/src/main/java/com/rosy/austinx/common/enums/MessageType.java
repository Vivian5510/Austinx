package com.rosy.austinx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 发送的消息类型
 */
@Getter
@ToString
@AllArgsConstructor
public enum MessageType {

    NOTICE(10, "通知类消息", "notice"),
    MARKETING(20, "营销类消息", "marketing"),
    AUTH_CODE(30, "验证码消息", "auth_code"),
    ;

    /**
     * 编码值
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String description;


    /**
     * 英文标识
     */
    private final String codeEn;


    /**
     * 通过code获取enum
     */
    public static MessageType getEnumByCode(Integer code) {
        MessageType[] values = values();
        for (MessageType value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
